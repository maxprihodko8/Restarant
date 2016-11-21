package com.restarant.model.sql.orderSql;

import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.sql.CreateTablesQueries;
import com.restarant.model.sql.userSql.UserDAO;
import com.restarant.model.sql.userSql.UserDAOImpl;
import com.restarant.model.user.UserImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderDAOImpl implements OrderDAO {
    private JdbcTemplate jdbcTemplate;
    private UserDAOImpl userDAO;

    OrderDAOImpl(JdbcTemplate jdbcTemplate, UserDAOImpl userDAO){
        this.jdbcTemplate = jdbcTemplate;
        this.userDAO = userDAO;
    }

    public void init(){
        jdbcTemplate.execute(CreateTablesQueries.createUserOrdersTable);
        jdbcTemplate.execute(CreateTablesQueries.createOrdersTable);
    }

    public void saveOrUpdate(String userName, Order order){
        saveOrUpdate(userDAO.getByName(userName), order);
    }

    public void saveOrUpdate(UserImpl user, Order order) {
        if(exists(user)) {
            try {
                List<Order> orders = getByNameAndId(user.getName(), order.getId());
                if (orders.size() > 0) {
                    Order userOrder = orders.get(0);
                    if (!userOrder.equals(order)) {
                        String updateQuery = "UPDATE orders SET orders.dishName=?, orders.count=? WHERE orderId=?;";
                        for (Map.Entry<String, Integer> mapVals : order.getMapOfDishes().entrySet()) {
                            jdbcTemplate.update(updateQuery, mapVals.getKey(), mapVals.getValue(), order.getId());
                        }
                    }
                } else {
                    String newUserId = "INSERT INTO userOrders (name, orderId) VALUES (\"" + user.getName() + "\", " + order.getId() + ");";
                    String insertQuery = "INSERT INTO orders (orderId, dishName, count) VALUES (?,?,?);";
                    jdbcTemplate.execute(newUserId);
                    for (Map.Entry<String, Integer> mapVals : order.getMapOfDishes().entrySet()) {
                        jdbcTemplate.update(insertQuery, order.getId(), mapVals.getKey(), mapVals.getValue());
                    }
                }
            } catch (NameNotFoundException e) {
            }
        } else {
            String createUserQuery = "INSERT INTO userOrders (name, orderId) VALUES (\"" + user.getName() + "\", " + order.getId() + ");";
            String insertQuery = "INSERT INTO orders (orderId, dishName, count) VALUES (?,?,?);";
            jdbcTemplate.execute(createUserQuery);
            for (Map.Entry<String, Integer> mapVals : order.getMapOfDishes().entrySet()) {
                jdbcTemplate.update(insertQuery, order.getId(), mapVals.getKey(), mapVals.getValue());
            }
        }
    }

    public void delete(Order order) {
        String query = "DELETE FROM userOrders WHERE userOrders.orderId=" + order.getId() +
                " AND userOrders.orderId NOT IN (SELECT oOG.orderId FROM ordersOfGroup oOG);";
        jdbcTemplate.execute(query);
    }

    public void delete(int orderId) {
        String query = "DELETE FROM userOrders WHERE userOrders.orderId= " + orderId +
                " AND userOrders.orderId NOT IN (SELECT oOG.orderId FROM ordersOfGroup oOG);";
        jdbcTemplate.execute(query);
    }

    public void deleteAllOrdersOfUser(UserImpl user) {
        String query = "DELETE FROM userOrders WHERE userOrders.name LIKE \"" + user.getName() + "\";";
        jdbcTemplate.execute(query);
    }

    public List<Order> get(int orderId) throws NameNotFoundException {
        String query = "SELECT o.orderId, o.dishName, o.count FROM orders o\n" +
                "JOIN userOrders uo ON o.orderId = uo.orderId\n" +
                "WHERE uo.orderId =  " + orderId +
                " AND o.orderId NOT IN (SELECT oOG.orderId FROM ordersOfGroup oOG);";
        List<SimpleOrder> orders = jdbcTemplate.query(query, new OrderRowMapper());
        //return new Order();
        return generateOrderListFromUnsortedOrderList(orders);
    }

    public Order getOrderOfGroup(int orderId) throws NameNotFoundException {
        String query = "SELECT o.orderId, o.dishName, o.count FROM orders o\n" +
                "JOIN userOrders uo ON o.orderId = uo.orderId\n" +
                "WHERE uo.orderId =  " + orderId + ";";
        List<SimpleOrder> orders = jdbcTemplate.query(query, new OrderRowMapper());
        //return new Order();
        return generateOrderListFromUnsortedOrderList(orders).get(0);
    }


    public List<Order> getByNameAndId(String name, int orderId) throws NameNotFoundException {
        String query = "SELECT o.orderId, o.dishName, o.count from orders o\n" +
                "Join userOrders uo on o.orderId = uo.orderId\n" +
                "WHERE uo.name LIKE \"" + name + "\" AND uo.orderId = " + orderId + " AND " +
                "o.orderId NOT IN (SELECT oOG.orderId FROM ordersOfGroup oOG);";
        List<SimpleOrder> orders = jdbcTemplate.query(query, new OrderRowMapper());
        return generateOrderListFromUnsortedOrderList(orders);
    }

    public List<Order> getByUser(UserImpl user) throws NameNotFoundException {
        String query = "SELECT o.orderId, o.dishName, o.count FROM orders o\n" +
                "JOIN userOrders uo ON o.orderId = uo.orderId\n" +
                "WHERE uo.name LIKE \""+ user.getName() + "\" AND o.orderId NOT IN " +
                "(SELECT oOG.orderId FROM ordersOfGroup oOG);";
        List<SimpleOrder> orders = jdbcTemplate.query(query, new OrderRowMapper());
        return generateOrderListFromUnsortedOrderList(orders);
    }

    public List<Order> list() {
        String query = "SELECT o.orderId, o.dishName, o.count FROM orders o\n" +
                "JOIN userOrders uo ON o.orderId = uo.orderId\n" +
                "WHERE uo.orderId NOT IN (SELECT ooG.orderId FROM ordersOfGroup ooG);";
        List<SimpleOrder> orders = jdbcTemplate.query(query, new OrderRowMapper());
        try {
            return generateOrderListFromUnsortedOrderList(orders);
        } catch (NameNotFoundException e){
            return new ArrayList<Order>();
        }
    }

    private List<Order> generateOrderListFromUnsortedOrderList(List<SimpleOrder> simpleOrders) throws NameNotFoundException {
        if (simpleOrders != null){
            List<Order> resultList = new ArrayList<Order>();
            for (SimpleOrder so : simpleOrders){
                boolean found = false;
                for (Order o : resultList){
                    if (o.getId().equals(so.getId())){
                        found = true;
                    }
                }
                if(!found){
                    Order newOrder = new Order();
                    for(SimpleOrder s : simpleOrders){
                        if(so.getId().equals(s.getId())){
                            newOrder.addDish(s.getName(), s.getCount());
                        }
                    }
                    newOrder.setId(so.getId());
                    resultList.add(newOrder);
                }
            }
            return resultList;
        } else {
            throw new NameNotFoundException();
        }
    }

    private boolean exists(UserImpl user){
        String query = "SELECT TRUE FROM userOrders uo WHERE uo.name LIKE \"" + user.getName() + "\"\n" +
                "AND uo.orderId NOT IN (SELECT ooG.orderId FROM ordersOfGroup ooG);";
        boolean result = jdbcTemplate.query(query, new ResultSetExtractor<Boolean>() {
            public Boolean extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                boolean result = resultSet.next();
                return result;
            }
        });
        return result;
    }
}
