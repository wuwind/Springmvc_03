package com.test.test;

import com.test.db.service.UserService;
import com.test.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        user.userId = "admin2";
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
        user.userId = "admin2";
        user.name = "admin2";
        user.password = "123";
        userService.addUser(user);
    }


}