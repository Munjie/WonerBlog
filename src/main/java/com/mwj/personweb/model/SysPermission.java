package com.mwj.personweb.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 15:59 @Version 1.0
 */
public class SysPermission implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;
    // 比于数据库多了一个 permissions 属性，该字段将 permission 按逗号分割为了 list。
    private List permissions;

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
