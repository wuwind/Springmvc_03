package com.test.db.dao.impl;


import com.test.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setName(rs.getString("USER_NAME"));
        user.setPassword(rs.getString("USER_PSW"));
        return user;
    }
}
