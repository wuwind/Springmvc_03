package com.test.db.dao.impl;

import com.test.db.dao.BaseDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public abstract class BaseDaoImpl<T> extends JdbcDaoSupport implements BaseDao<T> {

    private String tbNmae;

    public BaseDaoImpl() {
        tbNmae = getTClass().getSimpleName().toLowerCase();
    }

    /**
     * 获取数据源
     *
     * @param dataSource 来源于 bean.xml配置文件的dataSource
     */
    @Resource
    public final void setJdbcDaoDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    private Class<T> getTClass() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) genericSuperclass;
        Type[] types = type.getActualTypeArguments();
        if (types.length > 0)
            return (Class<T>) types[0];
        throw new IllegalArgumentException("add <T> for super class please");
    }


    /**
     * 增加
     *
     * @param t
     * @return
     */
    @Override
    public Object add(T t) {
        List<T> u = new ArrayList<>();
        u.add(t);
        List<Object> keys = add(u);
        return keys.get(0);
    }

    @Override
    public List<Object> add(List<T> datas) {
        if (null == datas || datas.isEmpty())
            return null;
        try {
            T t = datas.get(0);
            Field[] declaredFields = t.getClass().getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ").append(tbNmae).append("(");
            String idName = null;
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                sb.append(name);
                sb.append(",");
                if ("id".equals(name)) {
                    idName = "id";
                } else if (null == idName && name.contains("Id")) {
                    idName = name;
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") values");
            for (T data : datas) {
                addVaules(sb, declaredFields, data);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
//            System.out.println(sb.toString());
            KeyHolder key = new GeneratedKeyHolder();
            // 往数据库插入数据并且返回主键值
            String finalIdName = idName;
            this.getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    // 做数据库持久化   插入数据
                    return con.prepareStatement(sb.toString(), new String[]{finalIdName});
                }
            }, key);
            List<Map<String, Object>> keyList = key.getKeyList();
            List<Object> keys = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : keyList) {
                Object o = stringObjectMap.values().iterator().next();
//                System.out.println(":" + stringObjectMap);
                keys.add(o);
            }
            return keys;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addVaules(StringBuilder sb, Field[] declaredFields, Object o) throws IllegalAccessException {
        sb.append("(");
        for (int i = 0; i < declaredFields.length; i++) {
            Object v = declaredFields[i].get(o);
            if (v instanceof String) {
                sb.append("\"");
                sb.append(v);
                sb.append("\"");
            } else {
                sb.append(v);
            }
            sb.append(i == declaredFields.length - 1 ? ")" : ",");
        }
    }

    @Override
    public T queryById(Object id) {
        return null;
    }

    @Override
    public List<T> queryList(String sql) {
        return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(getTClass()));
    }

    @Override
    public List<T> getAll() {
        String sql = "select * from " + tbNmae;
        return queryList(sql);
    }


    @Override
    public int deleteById(Object id) {
        String sql = "delete from " + tbNmae + " where id=" + id;
        getJdbcTemplate().execute(sql);
        return 1;
    }

    @Override
    public int deleteById(List<Object> id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE from user WHERE id in (");
        for (Object o : id) {
            sb.append(o.toString()).append(",");
        }
        sb.replace(sb.length()-1,sb.length(),")");
        String sql = sb.toString();
        getJdbcTemplate().execute(sql);
        return 1;
    }

    @Override
    public int delete(T t) {
        return 0;
    }

    @Override
    public int delete(List<T> datas) {
        return 0;
    }

    @Override
    public int delete(String sql) {
        return 0;
    }

    @Override
    public int deleteAll() {
        String sql = "delete  from " + tbNmae;
        getJdbcTemplate().execute(sql);
        return 1;
    }

    @Override
    public int update(T data) {
        return 0;
    }

    @Override
    public int update(List<T> datas) {
        return 0;
    }

    @Override
    public int update(String sql) {
        return 0;
    }

}
