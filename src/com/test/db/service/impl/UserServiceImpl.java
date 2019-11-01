package com.test.db.service.impl;

import com.test.db.dao.UserDao;
import com.test.db.service.UserService;
import com.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public int addUser(User user) {
        try {
            return userDao.addUser(user);
        } catch (Exception e) {
            // 抛出异常 用于报错回滚
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public User findUser(User user) {
        return userDao.findByUser(user);
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public int delUserById(String id) {
        return userDao.delUserById(id);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    public User findByUser(User user) {
        return userDao.findByUser(user);
    }


}
