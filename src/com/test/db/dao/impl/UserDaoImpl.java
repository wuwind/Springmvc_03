package com.test.db.dao.impl;

import com.test.db.dao.UserDao;
import com.test.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.lang.System.out;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


    @Override
    public List<User> getAllUsers() {
//        List<User> userList = queryList("select * from user ");
        List<User> userList = getAll();
        return userList;
    }

    @Override
    public int addUser(User user) {
        System.out.println(user);
        Long add = (Long) add(user);
        return add.intValue();
    }

//    @Override
//    public int delUserById(String id) {
//        String SQL = "DELETE FROM LK_WS_USER WHERE USER_ID=?";
//        return getJdbcTemplate().update(SQL, id);
//    }

    @Override
    public User getUserById(String id) {
//        String SQL = "SELECT * FROM LK_WS_USER WHERE USER_ID=?";
//        User user = queryForObject(SQL, new UserRowMapper(), id);
        return queryById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findByUser(User user) {
        out.println(user);
        String sql = "select * from user where name=? and password=? LIMIT 1 ";
        return queryForObject(sql, new UserRowMapper(), user.getName(), user.getPassword());
    }

    @Override
    public User findUserByName(String name) {
        String sql = "select * from user where name=? limit 1";
        return queryForObject(sql, new UserRowMapper(), name);
    }

    private <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        T t;
        try {
            t = getJdbcTemplate().queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            t = null;
        }
        return t;
    }
}
