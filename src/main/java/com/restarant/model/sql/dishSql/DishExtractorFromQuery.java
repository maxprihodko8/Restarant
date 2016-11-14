package com.restarant.model.sql.dishSql;

import com.restarant.model.dish.Dish;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DishExtractorFromQuery implements ResultSetExtractor<Dish> {
    public Dish extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        {
            Dish dish = new Dish();

            dish.setId(resultSet.getInt(1));
            dish.setName(resultSet.getString(2));
            dish.setPrice(resultSet.getInt(3));
            dish.setType(resultSet.getString(4));

            return dish;
        }
    }
}