package com.restarant.model.sql;

import com.restarant.model.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    //@Autowired
    private JdbcTemplate jdbcTemplate;

    UserDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private void init() throws SQLException{
            jdbcTemplate.execute(CreateTablesQueries.createUsersTable);
    }
    
    public void saveOrUpdate(UserImpl user) {
        try {
            UserImpl userBySQL = getByName(user.getName());
            String userUpdateQuery = "UPDATE" + TablesForSqlNames.usersTableName +
                    "SET name=?, password=?, admin=?;";
            jdbcTemplate.update(userUpdateQuery, user.getName(), user.getPassword(), user.isAdmin());
        } catch (Exception e){
            String userAddQuery = "INSERT INTO" + TablesForSqlNames.usersTableName +
                    "(name, password, admin) VALUES " + " (?,?,?);";
            jdbcTemplate.update(userAddQuery, user.getName(), user.getPassword(),
                    user.isAdmin());
        }
        /*if(userBySQL.getName()!= "" && userBySQL.getPassword() != ""){
            String userUpdateQuery = "UPDATE" + TablesForSqlNames.usersTableName +
                    "SET name=?, password=?, gender=?, admin=?;";
            getJdbcTemplate().update(userUpdateQuery, user.getName(), user.getPassword(), user.isGender(),user.isAdmin());
        } else {
            String userAddQuery = "INSERT INTO" + TablesForSqlNames.usersTableName +
                    "(name, password, gender, admin) VALUES " + " (?,?,?,?);";
            getJdbcTemplate().update(userAddQuery, new Object[] {user.getName(), user.getPassword(), user.isGender(), user.isAdmin()});
            // userAddQuery, new Object[] {user.getName(), user.getPassword(), user.isGender()});
            }*/

        // userAddQuery, new Object[] {user.getName(), user.getPassword(), user.isGender()});
    }

    public void delete(int userId) {
        String deleteUserQuery = "DELETE FROM" + TablesForSqlNames.usersTableName + "where id=?;";
        jdbcTemplate.update(deleteUserQuery, userId);
    }

    public void delete(String userName) {
        String deleteUserQuery = "DELETE FROM" + TablesForSqlNames.usersTableName + "where name=?;";
        jdbcTemplate.update(deleteUserQuery, userName);
    }

    public UserImpl get(int userId) {
        String getUserByIdQuery = "SELECT * FROM" + TablesForSqlNames.usersTableName + "where id = " + userId + ";";
        List <UserImpl> userbyName = jdbcTemplate.query(getUserByIdQuery, new UserRowMapper());
        //return  getJdbcTemplate().queryForObject(getByNameUser, new Object[] {name},
        //        new BeanPropertyRowMapper<UserImpl>(UserImpl.class));
        return getFirstFromList(userbyName);
    }

    public UserImpl getByName(String name) {
        String getByNameUserQuery = "SELECT * FROM" + TablesForSqlNames.usersTableName + "where name = " +" \"" + name + "\"" + ";";
        List <UserImpl> userbyName = jdbcTemplate.query(getByNameUserQuery, new UserRowMapper());
        //return  getJdbcTemplate().queryForObject(getByNameUser, new Object[] {name},
        //        new BeanPropertyRowMapper<UserImpl>(UserImpl.class));
        return getFirstFromList(userbyName);
    }

    public List<UserImpl> list() {

        String getAllUsersQuery = "SELECT * FROM " + TablesForSqlNames.usersTableName + ";";
        List users = new ArrayList<UserImpl>();
        users = jdbcTemplate.query(getAllUsersQuery, new UserRowMapper());
        return users;
        
        //getJdbcTemplate().setDataSource(dataSource);
        //jdbcTemplate.setDataSource(dataSource);
        /*//users = getJdbcTemplate().queryForList(getAllUsersQuery);
        * //users =  jdbcTemplate.query(getAllUsersQuery, new UserRowMapper());
        //users = jdbcTemplate.queryForList(getAllUsersQuery);//jdbcTemplate.query(getAllUsersQuery, new RowMapper());*/
    }

    private UserImpl getFirstFromList(List<UserImpl> userList) throws UsernameNotFoundException{
        try{
            return userList.get(0);
        } catch (IndexOutOfBoundsException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private void printUserListToConsole(){
        List <UserImpl>usersList = list();
        for(int i=0; i < usersList.size(); i++){
            System.out.println(usersList.get(i).getName());
        }
        System.out.println(usersList.size());
    }
}
