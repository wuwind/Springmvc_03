package com.test.db.dao.impl;

import com.test.model.Menu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuRowMapper implements RowMapper<Menu> {

    @Override
    public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int pid = resultSet.getInt("pid");
        Menu menu = new Menu();
        menu.id = id;
        menu.name = name;
        menu.pid = pid;
        return menu;
    }
}
