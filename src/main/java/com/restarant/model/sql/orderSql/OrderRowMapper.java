package com.restarant.model.sql.orderSql;


import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRowMapper implements RowMapper {

    public SimpleOrder mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderExtractorFromQuery orderExtractorFromQuery = new OrderExtractorFromQuery();
        return orderExtractorFromQuery.extractData(resultSet);
    }
}
