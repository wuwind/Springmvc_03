package com.test.db.service;

import com.test.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    int addUser(User user);

    User findUser(User user);

    User findUserByName(String name);

    int delUserById(Object id);

    int delUserById(List<Object> ids);

    int updateUser(User user);

    User getUserById(String id);

    User findByUser(User user);

    void deleteAll();

}
