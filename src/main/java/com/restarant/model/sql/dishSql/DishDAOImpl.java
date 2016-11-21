package com.restarant.model.sql.dishSql;

import com.restarant.model.dish.Dish;
import com.restarant.model.sql.CreateTablesQueries;
import com.restarant.model.sql.TablesForSqlNames;
import com.restarant.model.sql.userSql.UserRowMapper;
import com.restarant.model.user.UserImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishDAOImpl implements DishDAO {

    private JdbcTemplate jdbcTemplate;
    String dishTable = TablesForSqlNames.dishTable;

    DishDAOImpl (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private void init() throws SQLException{
        jdbcTemplate.execute(CreateTablesQueries.createDishTable);
    }

    public void saveOrUpdate(String name, Dish dish) {
        try {
            Dish userBySQL = getByName(name);
            String userUpdateQuery = "UPDATE" + dishTable +
                    "SET name=?, price=?, type=?;";
            jdbcTemplate.update(userUpdateQuery, dish.getName(), dish.getPrice(), dish.getType());
        } catch (Exception e){
            String userAddQuery = "INSERT INTO" + dishTable +
                    "(name, price, type) VALUES " + " (?,?,?);";
            jdbcTemplate.update(userAddQuery, dish.getName(), dish.getPrice(),
                    dish.getType());
        }
    }

    public void saveOrUpdate(Dish dish) {
        try {
            Dish dishBySQL = get(dish.id);
            String userUpdateQuery = "UPDATE" + dishTable +
                    "SET name=?, price=?, type=? where id=?;";
            jdbcTemplate.update(userUpdateQuery, dish.getName(), dish.getPrice(), dish.getType(), dish.getId());
        } catch (Exception e){
            String userAddQuery = "INSERT INTO" + dishTable +
                    "(name, price, type) VALUES " + " (?,?,?);";
            jdbcTemplate.update(userAddQuery, dish.getName(), dish.getPrice(),
                    dish.getType());
        }
    }

    public void delete(int dishId) {
        String deleteUserQuery = "DELETE FROM" + dishTable + "where id=?;";
        jdbcTemplate.update(deleteUserQuery, dishId);
    }

    public void delete(String userName) {
        String deleteUserQuery = "DELETE FROM" + dishTable + "where name=?;";
        jdbcTemplate.update(deleteUserQuery, userName);
    }

    public Dish get(int dishId) throws NameNotFoundException {
        String getDishByIdQuery = "SELECT * FROM" + dishTable + "where id = " + dishId + ";";
        /*Dish dish = getOneDish(getDishByIdQuery);
        if(dish != null){
            return dish;
        } else {
            throw new NameNotFoundException();
        }*/
        List <Dish> dish = jdbcTemplate.query(getDishByIdQuery, new DishRowMapper());
        return getFirstFromList(dish);

    }

    public Dish getByName(String name) {
        String getByNameDishQuery = "SELECT * FROM" + dishTable + "where name LIKE " + "\"" + name + "\"" + ";";
        /*Dish dish = getOneDish(getByNameDishQuery);
        if (dish != null) {
            return dish;
        } else
            throw new NameNotFoundException();
            */
        List <Dish> dish = jdbcTemplate.query(getByNameDishQuery, new DishRowMapper());
        return getFirstFromList(dish);
    }

    public List<Dish> list() {
        String getAllUsersQuery = "SELECT * FROM " + dishTable + ";";
        List <Dish> dish = new ArrayList<Dish>();
        dish = jdbcTemplate.query(getAllUsersQuery, new DishRowMapper());
        return dish;
    }

    private Dish getOneDish(String query){
        Dish dish = jdbcTemplate.queryForObject(query, new Object[]{1}, new RowMapper<Dish>() {
            public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setPrice(resultSet.getInt("price"));
                dish.setType(resultSet.getString("type"));
                return dish;
            }
        });
        return dish;
    }

    private Dish getFirstFromList(List<Dish> dishList) {
        try{
            return dishList.get(0);
        } catch (IndexOutOfBoundsException e){
            return new Dish("standartDish", 0, "none");
        }
    }

}
