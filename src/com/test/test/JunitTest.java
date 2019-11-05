package com.test.test;

import com.test.db.dao.UserDao;
import com.test.db.service.UserService;
import com.test.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class JunitTest extends BaseJunitTest {

    @Autowired
    UserService userService;


    @Autowired
    UserDao userDao;

    @Test
    public void delUserById(){
        int i = userService.delUserById("admin");
        System.out.println("删除i："+i);
    }


    @Test
    public void getUserById(){
        User admin = userDao.getUserById("41");
        System.out.println(admin);
    }
    @Test
    public void getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        for (User allUser : allUsers) {
            System.out.println(allUser);
        }
    }

    @Test
    public void addUser(){
        User user = new User();
        user.name = "admin2";
        user.password = "1234";
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
         userService.addUser(user);
        int i = userService.addUser(user);
        System.out.println(i);
    }



    @Test
    public void getAllMenu(){
//        List<Menu> allMenu = menuService.getAllMenu();
//        for (Menu menu : allMenu) {
//            System.out.println(menu.name);
//        }
    }


    @Test
    public void delAllUser(){
//        userService.deleteAll();
//        userService.delUserById(42);

//        d.add(46);
//        userService.delUserById(d);
        User u = new User();
        u.setId(37);
        List<User> d = new ArrayList<>();
        d.add(u);
//        d.add(48);
        userDao.delete(d);
    }

    @Test
    public void updateUser(){
        List<User> d = new ArrayList<>();
        User user = new User();
        user.id = 39;
        user.name = "admin2";
        user.password = "123456";
        d.add(user);
        int i = userDao.update(d);
        System.out.println("修改："+i);
    }

}