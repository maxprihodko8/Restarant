package com.restarant.model.sql;

import com.restarant.model.user.UserImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractorFromQueryDatabase implements ResultSetExtractor <UserImpl> {
    public UserImpl extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        {
            UserImpl user = new UserImpl();

            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setAdmin(false);

            return user;
        }
    }
}
