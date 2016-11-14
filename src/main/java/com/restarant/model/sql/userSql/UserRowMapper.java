package com.restarant.model.sql.userSql;

import com.restarant.model.user.UserImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper <UserImpl> {
    public UserImpl mapRow(ResultSet resultSet, int i) throws SQLException {
        UserExtractorFromQueryDatabase userExtractorFromQueryDatabase = new UserExtractorFromQueryDatabase();
        return userExtractorFromQueryDatabase.extractData(resultSet);
    }
}
