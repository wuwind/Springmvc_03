package com.test.db.dao;

import com.test.model.User;

import java.util.List;

public interface UserDao {

    int addUser(User user);

    int deleteById(Object id);

    int deleteById(List<Object> id);

    int deleteAll();

    int delete(User user);

    int delete(List<User> user);

    int update(User user);

    int update(List<User> user);

    User getUserById(String id);

    List<User> getAllUsers();

    User findByUser(User user);

    User findUserByName(String name);
}
