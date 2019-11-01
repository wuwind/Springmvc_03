package com.test.db.dao;

import com.test.model.User;

import java.util.List;

public interface UserDao {

    int addUser(User user);

    int delUserById(String id);

    int updateUser(User user);

    User getUserById(String id);

    List<User> getAllUsers();

    User findByUser(User user);

    User findUserByName(String name);
}
