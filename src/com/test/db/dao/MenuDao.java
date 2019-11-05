package com.test.db.dao;

import com.test.model.Menu;

import java.util.List;

public interface MenuDao extends BaseDao<Menu>{

    List<Menu> getAllMenu();


}
