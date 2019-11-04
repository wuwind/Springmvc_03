package com.test.db.service.impl;

import com.test.db.dao.MenuDao;
import com.test.db.service.MenuService;
import com.test.model.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    MenuDao menuDao;

    @Override
    public List<Menu> getAllMenu() {
        return menuDao.getAllMenu();
    }
}
