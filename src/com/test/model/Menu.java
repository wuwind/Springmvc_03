package com.test.model;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class Menu extends BeanPropertyRowMapper {
    public int id;
    public String name;
    public int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


}
