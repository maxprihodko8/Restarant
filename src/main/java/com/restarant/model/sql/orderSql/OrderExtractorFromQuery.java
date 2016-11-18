package com.restarant.model.sql.orderSql;

import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderExtractorFromQuery implements ResultSetExtractor<SimpleOrder> {
    public SimpleOrder extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        SimpleOrder order = new SimpleOrder();

        order.setId(resultSet.getInt(1));
        order.setName(resultSet.getString(2));
        order.setCount(resultSet.getInt(3));

        return order;
    }
}
