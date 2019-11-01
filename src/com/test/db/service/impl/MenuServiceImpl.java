package com.test.db.service.impl;

import com.test.db.dao.MenuDao;
import com.test.db.service.MenuService;
import com.test.db.service.UserService;
import com.test.model.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    MenuDao menuDao;

    @Override
    public List<Menu> getAllMenu() {
        return menuDao.getAllMenu();
    }
}
