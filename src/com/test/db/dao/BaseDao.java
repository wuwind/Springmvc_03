package com.test.db.dao;

import java.util.List;

public interface BaseDao<T> {

    T queryById(Object id);

    List<T> queryList(String sql);

    List<T> getAll();

    Object add(T t);

    List<Object> add(List<T> datas);

    int deleteById(Object id);

    int deleteById(List<Object> id);

    int delete(T data);

    int delete(List<T> datas);

    int delete(String sql);

    int deleteAll();

    int update(T data);

    int update(List<T> datas);

    int update(String sql);
}
