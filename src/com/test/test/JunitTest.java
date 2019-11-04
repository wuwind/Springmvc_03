package com.test.test;

import com.test.db.service.MenuService;
import com.test.db.service.UserService;
import com.test.model.Menu;
import com.test.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class JunitTest extends BaseJunitTest {

    @Autowired
    UserService userService;

    @Test
    public void delUserById(){
        int i = userService.delUserById("admin");
        System.out.println("删除i："+i);
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.name = "admin2";
        user.password = "123456";
        int i = userService.updateUser(user);
        System.out.println("修改："+i);
    }

    @Test
    public void getUserById(){
        User admin = userService.getUserById("admin");
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

    @Autowired
    MenuService menuService;

    @Test
    public void getAllMenu(){
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu) {
            System.out.println(menu.name);
        }
    }

    @Test
    public void delAllUser(){
//        userService.deleteAll();
//        userService.delUserById(35);
        List<Object> d = new ArrayList<>();
        d.add(43);
        d.add(45);
        d.add(46);
        userService.delUserById(d);
    }

}