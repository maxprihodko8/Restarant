package com.restarant.model.sql.groupSql;

import com.restarant.model.group.SimpleUserGroup;
import com.restarant.model.group.UserGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupOrderDAOExtractorFromQuery implements ResultSetExtractor {
    @Override
    public SimpleUserGroup extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        SimpleUserGroup userGroup = new SimpleUserGroup();
        userGroup.setOrderId(resultSet.getInt(1));
        userGroup.setUserName(resultSet.getString(2));
        return userGroup;
    }
}
