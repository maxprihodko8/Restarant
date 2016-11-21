package com.restarant.model.sql.groupSql;

import com.restarant.model.group.SimpleUserGroup;
import com.restarant.model.group.UserGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupOrderRowMapper implements RowMapper {
    @Override
    public SimpleUserGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        GroupOrderDAOExtractorFromQuery groupOrderDAOExtractorFromQuery = new GroupOrderDAOExtractorFromQuery();
        return groupOrderDAOExtractorFromQuery.extractData(resultSet);
    }
}
