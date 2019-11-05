package com.test.db.dao.impl;

import com.test.db.dao.BaseDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public abstract class BaseDaoImpl<T> extends JdbcDaoSupport implements BaseDao<T> {

    private String tbNmae;
    private Field[] declaredFields;
    private String idName = null;
    private Class<T> tClass;
    private Field idField = null;

    public BaseDaoImpl() {
        tClass = getTClass();
        tbNmae = tClass.getSimpleName().toLowerCase();
        declaredFields = tClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            if ("id".equals(name)) {
                setIdFiele(name, declaredField);
                break;
            } else if (null == idName && name.contains("Id")) {
                setIdFiele(name, declaredField);
            }
        }
    }

    private void setIdFiele(String name, Field declaredField) {
        idName = name;
        idField = declaredField;
        idField.setAccessible(true);
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
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ").append(tbNmae).append("(");
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                sb.append(name);
                sb.append(",");
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
            this.getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    // 做数据库持久化   插入数据
                    return con.prepareStatement(sb.toString(), new String[]{idName});
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
            getValue(sb, v);
            sb.append(i == declaredFields.length - 1 ? ")" : ",");
        }
    }

    @Override
    public T queryById(Object id) {
        String sql = "select * from " + tbNmae + " where " + idName + " = " + id;
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(tClass));
    }

    @Override
    public List<T> queryList(String sql) {
        return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(tClass));
    }

    @Override
    public List<T> getAll() {
        String sql = "select * from " + tbNmae;
        return queryList(sql);
    }


    @Override
    public int deleteById(Object id) {
        String sql = "delete from " + tbNmae + " where " + idName + "=" + id;
        return delete(sql);
    }

    @Override
    public int deleteById(List<Object> id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE from user WHERE id in (");
        for (Object o : id) {
            sb.append(o.toString()).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");
        String sql = sb.toString();
        return delete(sql);
    }

    @Override
    public int delete(T t) {
        Object id = null;
        try {
            id = idField.get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return deleteById(id);
    }

    @Override
    public int delete(List<T> datas) {
        if (null == datas || datas.isEmpty())
            return 0;
        List<Object> ids = new ArrayList<>();
        try {
            for (T data : datas) {
                Object o = idField.get(data);
                ids.add(o);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return deleteById(ids);
    }

    @Override
    public int delete(String sql) {
        return getJdbcTemplate().update(sql);
    }

    @Override
    public int deleteAll() {
        String sql = "delete  from " + tbNmae;
        return delete(sql);
    }

    @Override
    public int update(T data) {
//        UPDATE user set name='123' , password = '465' where id=39
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update ").append(tbNmae).append(" set ");
            Object id = null;
            for (Field declaredField : declaredFields) {
                Object o = declaredField.get(data);
                if (declaredField == idField) {
                    id = o;
                    continue;
                }
                String name = declaredField.getName();
                sb.append(name).append("=");
                getValue(sb, o);
                sb.append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), " where ");
            sb.append(idName).append("=").append(id);
            String sql = sb.toString();
            return update(sql);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void getValue(StringBuilder sb, Object o) {
        if (o instanceof String) {
            sb.append("\"");
            sb.append(o);
            sb.append("\"");
        } else {
            sb.append(o);
        }
    }

    @Override
    public int update(List<T> datas) {
        int n = 0;
        for (T data : datas) {
            n += update(data);
        }
        return n;
    }

    @Override
    public int update(String sql) {
        return getJdbcTemplate().update(sql);
    }

}
