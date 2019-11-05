package com.test.controller.api;

import com.test.db.service.MenuService;
import com.test.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("all")
    public List<Menu> getAllMenu(){
        List<Menu> allMenu = menuService.getAllMenu();
//        Response<List<Menu>> response = new Response<>();
//        response.setCode(1);
//        response.setData(allMenu);
        return allMenu;
    }
}
