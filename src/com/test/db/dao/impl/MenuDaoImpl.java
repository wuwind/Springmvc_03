package com.test.db.dao.impl;

import com.test.db.dao.MenuDao;
import com.test.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menus = queryList("select * from menu");
        return menus;
    }
}
