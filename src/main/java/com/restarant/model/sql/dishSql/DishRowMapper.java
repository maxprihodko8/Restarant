package com.restarant.model.sql.dishSql;

import com.restarant.model.dish.Dish;
import com.restarant.model.sql.userSql.UserExtractorFromQueryDatabase;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DishRowMapper implements RowMapper<Dish> {
    public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
        DishExtractorFromQuery dishExtractorFromQuery = new DishExtractorFromQuery();
        return dishExtractorFromQuery.extractData(resultSet);
    }
}
