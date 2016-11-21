package com.restarant.model.sql.groupSql;

import com.restarant.model.group.SimpleUserGroup;
import com.restarant.model.group.UserGroup;
import com.restarant.model.order.Order;
import com.restarant.model.sql.CreateTablesQueries;
import com.restarant.model.sql.orderSql.OrderDAOImpl;
import com.restarant.model.user.UserImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import javax.naming.NameNotFoundException;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupOrderDAOImpl implements GroupOrderDAO {

    private JdbcTemplate jdbcTemplate;
    private OrderDAOImpl orderDAOImpl;

    GroupOrderDAOImpl(JdbcTemplate jdbcTemplate, OrderDAOImpl orderDAOImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDAOImpl = orderDAOImpl;
    }

    private void init() {
        this.jdbcTemplate.execute(CreateTablesQueries.createGroupsTable);
        this.jdbcTemplate.execute(CreateTablesQueries.createOrdersOfGroupTable);
        this.jdbcTemplate.execute(CreateTablesQueries.createUsersOfGroupTable);
        this.jdbcTemplate.execute(CreateTablesQueries.createRequestsOfUsersToGroup);
    }

    public void save(String user, Order order, Integer userGroup) {
        UserGroup ug = get(userGroup);
        if (ug != null) {
            String query = "INSERT INTO ordersOfGroup (groupId, orderId) VALUES (?, ?);";
            orderDAOImpl.saveOrUpdate(user, order);
            jdbcTemplate.update(query, new Object[]{userGroup, order.getId()});
        }
    }

    public void addGroup(UserGroup userGroup){
        String groupsQuery = "INSERT INTO groups (id, name, creator) VALUES (?, ?, ?);";
        String query = "INSERT INTO usersOfGroup VALUES (?, ?)";
        if(!exists(userGroup.getName())) {
            jdbcTemplate.update(groupsQuery, new Object[]{userGroup.getId(), userGroup.getName(), userGroup.getCreator()});
            jdbcTemplate.update(query, new Object[]{userGroup.getId(), userGroup.getCreator()});
        }
    }

    public void addUserToGroup(int userGroup, String user){
        String query = "INSERT INTO usersOfGroup VALUES (?, ?);";
        String deleteUserFromReq = "DELETE FROM groupRequests WHERE groupId = ? and userName LIKE ?;";
        jdbcTemplate.update(query, new Object[]{userGroup, user});
        jdbcTemplate.update(deleteUserFromReq, new Object[]{userGroup, user});
    }

    public void rejectAdddingToGroup(int groupId, String user){
        String query = "DELETE FROM groupRequests WHERE groupId = " + groupId + " AND userName LIKE \"" + user + "\";";
        jdbcTemplate.execute(query);
    }

    public void deleteGroup(int groupId) {
        String query = "DELETE FROM userOrders where orderId IN " +
                "(SELECT orderId FROM ordersOfGroup Where groupId = " + groupId + ");";
        String deleteGroupQuery = "DELETE FROM groups where id = " + groupId + ";";
        String deleteUsersInList = "DELETE FROM usersOfGroup where id = " + groupId;
        jdbcTemplate.execute(query);
        jdbcTemplate.execute(deleteGroupQuery);
        jdbcTemplate.execute(deleteUsersInList);
    }

    public void addReqForEnterGroup(UserImpl user, String groupName){
        int groupId = getUserGroupId(groupName);
        String query = "INSERT INTO groupRequests (groupId, userName) VALUES (?, ?);";
        jdbcTemplate.update(query, new Object[]{groupId, user.getName()});
    }


    public void deleteOrder(int orderId) {
        String query = "DELETE FROM userOrders where orderId = " + orderId + ";";
        jdbcTemplate.execute(query);
    }

    public void leftUserFromGroup(UserImpl user, int groupId) {
        String query = "UPDATE userOrders SET name = " + groupId + " WHERE name LIKE \""  + user.getName() + "\"" +
               " AND userOrders.orderId IN (SELECT ordersOfGroup.orderId from ordersOfGroup);";
        jdbcTemplate.execute(query);
        String query1 = "DELETE FROM usersOfGroup WHERE id = " + groupId;
        jdbcTemplate.execute(query1);
    }


    public UserGroup get(int groupId) {
        String groupInfo = "SELECT g.name, g.creator FROM groups g\n" +
                "  WHERE g.id = " + groupId + ";";
        String usersAndOrders = "SELECT og.orderId, uo.name FROM ordersOfGroup og\n" +
                "  JOIN userOrders uo ON og.orderId = uo.orderId\n" +
                "  WHERE groupId = " + groupId + ";";
        String getRequestsForEnterGroupQuery = "SELECT userName FROM groupRequests\n" +
                "JOIN groups on groupRequests.groupId = groups.id\n" +
                "WHERE groupId = " + groupId + ";";
        List<String> usersInRequests  = jdbcTemplate.query(getRequestsForEnterGroupQuery, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        });
        UserGroup ug = getUserGroupInfo(groupInfo);
        List<SimpleUserGroup> groups = jdbcTemplate.query(usersAndOrders, new GroupOrderRowMapper());
        UserGroup userGroup = generateUserGroupFromUnsortedQuery(groups);
        userGroup.setId(groupId);
        userGroup.setName(ug.getName());
        userGroup.setCreator(ug.getCreator());
        for(Integer i : userGroup.getOrdersId()){
            try {
                userGroup.addOrderToUserOrders(orderDAOImpl.getOrderOfGroup(i));
            } catch (NameNotFoundException e){
                System.out.println("no such an element");
            } catch (ArrayIndexOutOfBoundsException e1){
                System.out.println("index out of bound in getOrderById method!!!");
            }
        }
        for(String s : usersInRequests){
            userGroup.addUserToRequests(s);
        }

        return userGroup;
    }

    public List<UserGroup> getByUser(UserImpl user) {
        String query = "SELECT DISTINCT g.id FROM groups g\n" +
                "  LEFT JOIN ordersOfGroup og ON g.id = og.groupId\n" +
                "  LEFT JOIN userOrders uo ON og.orderId = uo.orderId\n" +
                "  JOIN usersOfGroup uof ON g.id = uof.id\n" +
                "  WHERE uof.name LIKE \"" + user.getName() + "\";";
        ArrayList<UserGroup> resultUserGroup = new ArrayList<>();

        List<Integer> groupIds = jdbcTemplate.query(query, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        for (Integer i : groupIds) {
            resultUserGroup.add(get(i));
        }

        return resultUserGroup;
    }

    public List<UserGroup> list() {
        String getAllGroups = "SELECT id FROM groups;";
        List<UserGroup> groups = new ArrayList<>();
        List<Integer> groupsIds = jdbcTemplate.query(getAllGroups, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        for (Integer i : groupsIds) {
            groups.add(get(i));
        }

        return groups;
    }

    public List<String> getGroupNamesList(UserImpl user){
        String query = "SELECT groups.name FROM groups\n" +
                " WHERE groups.id NOT IN (SELECT id FROM usersOfGroup WHERE usersOfGroup.name LIKE \"afaffa\")\n" +
                "       AND groups.creator NOT LIKE \"" + user.getName() + "\" AND groups.id NOT IN\n" +
                "      (SELECT groupId FROM groupRequests WHERE groupRequests.userName LIKE \"" + user.getName() +  "\");";
        List<String> groupNames = jdbcTemplate.query(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString(1);
            }
        });
        return groupNames;
    }

    private UserGroup generateUserGroupFromUnsortedQuery(List<SimpleUserGroup> simpleGroup) {
        UserGroup userGroup = new UserGroup();
        ArrayList<String> check = new ArrayList();
        for (SimpleUserGroup s : simpleGroup) {
            userGroup.addOrderId(s.getOrderId());
            boolean found = false;
            for (String ch : check) {
                if (ch.equals(s.getUserName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                userGroup.addUser(s.getUserName());
            }
        }
        return userGroup;
    }

    private UserGroup getUserGroupInfo(String query) {
        List<UserGroup> userList = jdbcTemplate.query(query, new RowMapper<UserGroup>() {
            @Override
            public UserGroup mapRow(ResultSet resultSet, int i) throws SQLException {
                UserGroup ug = new UserGroup();
                ug.setName(resultSet.getString(1));
                ug.setCreator(resultSet.getString(2));
                return ug;
            }
        });
        return userList.get(0);
    }

    private boolean exists(String name){
        String query = "SELECT TRUE FROM groups WHERE name LIKE \"" + name + "\";";
        boolean result = jdbcTemplate.query(query, new ResultSetExtractor<Boolean>() {
            public Boolean extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                boolean result = resultSet.next();
                return result;
            }
        });
        return result;
    }

    private Integer getUserGroupId(String groupName){
        String getGroupId = "SELECT id FROM groups\n" +
                "WHERE name LIKE \"" + groupName + "\";";
        List<Integer> groupIds = jdbcTemplate.query(getGroupId, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        return groupIds.get(0);
    }
}
