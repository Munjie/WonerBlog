package com.mwj.personweb.model;

import java.io.Serializable;

/**
 * @Author: 母哥 @Date: 2019-02-28 14:52 @Version 1.0
 */
public class SysRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
