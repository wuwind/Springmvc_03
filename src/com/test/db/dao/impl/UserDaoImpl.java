package com.test.db.dao.impl;

import com.test.db.dao.UserDao;
import com.test.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.out;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    /**
     * 获取数据源
     *
     * @param dataSource 来源于 bean.xml配置文件的dataSource
     */
    @Resource
    public final void setJdbcDaoDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        //新建list集合接受所有的用户信息
        List<User> userList;
        // 做数据库持久化  并且用 userList 接受查询出来数据
        userList = this.getJdbcTemplate().query("select * from user ", new RowMapper() {
            //  类似循环  所有的数据在ResultSet rs 对象中取，  也可以根据行数取int rowNum
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                //新建user对象   从数据库取到的数据保存user的属性中
                User user = new User();
                //  rs.getString(2)   getString是要取的数据的类型   2是属性在行的第几列
                user.setName(rs.getString(1));
                user.setPassword(rs.getString(2));
                //  返回user对象    SpringJDBC会自动将user   添加到  userList 中
                return user;
            }
        });
        return userList;
    }

    @Override
    public int addUser(User user) {
        System.out.println(user);

//        int update=getJdbcTemplate().update("INSERT INTO employee_table(employee_name,email,gender,salary) VALUES(?,?,?,?)", employee.getEmployeeName(),employee.getEmail(),employee.getGender(),employee.getSalary());
//        if(update>0)

        // 接受插入数据是返回的主键值
        KeyHolder key = new GeneratedKeyHolder();
        // 往数据库插入数据并且返回主键值
        this.getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // 做数据库持久化   插入数据
                PreparedStatement prepareStatement = con.prepareStatement("insert into LK_WS_USER (USER_ID, USER_NAME,USER_PSW) values (?,?,?)", new String[]{"USER_ID"});
                //给占位符赋值  数字表示第几个占位符
                prepareStatement.setString(1, user.getName());
                prepareStatement.setString(2, user.getName());
                prepareStatement.setString(3, user.getPassword());
                return prepareStatement;
            }
        }, key);
        //返回主键   因为KeyHolder key是一个对象  所以需要从中取出 key 并转为int类型
        System.out.println(key.getKeys().get("USER_ID"));
        return 1;
    }

    @Override
    public int delUserById(String id) {
        String SQL = "DELETE FROM LK_WS_USER WHERE USER_ID=?";
        return getJdbcTemplate().update(SQL, id);
    }

    @Override
    public int updateUser(User user) {
        String SQL = "UPDATE LK_WS_USER SET USER_NAME=?, USER_PSW=? WHERE USER_ID=?";
        return getJdbcTemplate().update(SQL, user.getName(), user.getPassword(), user.getUserId());
    }

    @Override
    public User getUserById(String id) {
        String SQL = "SELECT * FROM LK_WS_USER WHERE USER_ID=?";
        User user = queryForObject(SQL, new UserRowMapper(), id);
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findByUser(User user) {
        out.println(user);
        String sql = "select * from user where name=? and password=? LIMIT 1 ";
        return queryForObject(sql, new UserRowMapper(), user.getName(), user.getPassword());
    }

    @Override
    public User findUserByName(String name) {
        String sql = "select * from user where name=? limit 1";
        return queryForObject(sql, new UserRowMapper(), name);
    }

    private <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        T t;
        try {
            t = getJdbcTemplate().queryForObject(sql, rowMapper, args);
        } catch (EmptyResultDataAccessException e) {
            t = null;
        }
        return t;
    }
}
