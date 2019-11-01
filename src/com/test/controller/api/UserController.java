package com.test.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @RequestMapping("{page}")
    public String main(@PathVariable String page) {
        return page;
    }

    @RequestMapping("login")
    public String login(HttpSession session, String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        if (username.equals("1") && password.equals("1")) {
            session.setAttribute("user", username);
            return "redirect:/main";
        } else {
            return "redirect:/login.jsp";
        }
    }
}
