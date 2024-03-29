package com.test.test;

import com.test.db.dao.MenuDao;
import com.test.db.dao.UserDao;
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


    @Autowired
    UserDao userDao;
    @Autowired
    MenuDao menuDao;


    @Test
    public void delUserById() {
        int i = userService.delUserById("admin");
        System.out.println("删除i：" + i);
    }


    @Test
    public void getUserById() {
        User admin = userDao.getUserById("41");
        System.out.println(admin);
    }

    @Test
    public void getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        for (User allUser : allUsers) {
            System.out.println(allUser);
        }
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setName("admin2");
        user.setPassword("1234");
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
    public void getAllMenu() {
//        List<Menu> allMenu = menuService.getAllMenu();
//        for (Menu menu : allMenu) {
//            System.out.println(menu.name);
//        }
    }

    @Test
    public void addMenu() {
        Menu m = new Menu();
        m.name = "人事管理";
        m.pid = 1;
        menuDao.add(m);
    }

    @Test
    public void delAllUser() {
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
    public void updateUser() {
        List<User> d = new ArrayList<>();
        User user = new User();
        user.setId(39);
        user.setName("admin2");
        user.setPassword("123456");
        d.add(user);
        int i = userDao.update(d);
        System.out.println("修改：" + i);
    }

}