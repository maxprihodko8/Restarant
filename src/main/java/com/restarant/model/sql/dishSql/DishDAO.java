package com.restarant.model.sql.dishSql;

import com.restarant.model.dish.Dish;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface DishDAO {
    void saveOrUpdate(String name, Dish dish);
    void delete(int userId);
    void delete(String userName);
    Dish get(int userId) throws NameNotFoundException;
    Dish getByName(String name) throws NameNotFoundException;
    List<Dish> list();
}
