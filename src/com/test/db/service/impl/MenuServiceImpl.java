package com.test.db.service.impl;

import com.test.db.dao.MenuDao;
import com.test.db.service.MenuService;
import com.test.model.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    MenuDao menuDao;

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> allMenu = menuDao.getAllMenu();
        List<Menu> result = new ArrayList<>();
        filterByPid(result, allMenu, 0);
        return result;
    }

    private void filterByPid(List<Menu> result, List<Menu> allMenu, int pid) {
        for (Menu menu : allMenu) {
            if (menu.pid == pid) {
                result.add(menu);
                menu.children = new ArrayList<>();
                filterByPid(menu.children, allMenu, menu.id);
            }
        }
    }
}
