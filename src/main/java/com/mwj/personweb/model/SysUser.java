package com.mwj.personweb.model;

import java.io.Serializable;

/**
 * @Author: 母哥 @Date: 2019-02-28 14:51 @Version 1.0
 */
public class SysUser implements Serializable {

    static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
