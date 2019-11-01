package com.test.controller.api;

import com.test.db.service.UserService;
import com.test.model.User;
import com.test.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("api")
public class UserControllerApi {

    @Autowired
    UserService userService;

    @RequestMapping("getUser")
    @ResponseBody
    public Response getUser(HttpServletRequest request, User user) {
        String name = request.getParameter("token");
        String psw = request.getParameter("psw");
        System.out.println(name);
        System.out.println(psw);
        System.out.println(user.name);
        System.out.println(request.getParameter("name"));
        User u = new User();
        u.setName("name");
        u.setPassword("1234");
        Response<User> response = new Response();
        response.setCode(1);
        response.setData(u);
        return response;
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public Response getAllUser(HttpServletRequest request) {
        List<User> allUsers = userService.getAllUsers();
        Response<List<User>> response = new Response();
        response.setCode(1);
        response.setData(allUsers);
        return response;
    }

    @RequestMapping("updateUser")
    @ResponseBody
    public Response updateUser(HttpServletRequest request, User user) {
        String name = request.getParameter("name");
        String psw = request.getParameter("psw");
        System.out.println(name);
        System.out.println(psw);
        int n = userService.updateUser(user);
        Response response = new Response();
        response.setCode(1);
        response.setMsg("success");
        return response;
    }

}
