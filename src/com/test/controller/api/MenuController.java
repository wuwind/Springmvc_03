package com.test.controller.api;

import com.test.db.service.MenuService;
import com.test.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    public List<Menu> getAllMenu(){
        return menuService.getAllMenu();
    }
}
