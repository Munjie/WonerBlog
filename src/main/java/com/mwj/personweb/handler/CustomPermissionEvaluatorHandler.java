package com.mwj.personweb.handler;

import com.mwj.personweb.model.SysPermission;
import com.mwj.personweb.service.ISysPermissionService;
import com.mwj.personweb.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: 母哥 @Date: 2019-03-01 16:13 @Version 1.0
 */
@Component
public class CustomPermissionEvaluatorHandler implements PermissionEvaluator {

    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private ISysRoleService roleService;

    @Override
    public boolean hasPermission(
            Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获得loadUserByUsername()方法的结果
        User user = (User) authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        // 遍历用户所有角色
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleService.selectByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);

            // 遍历permissionList
            for (SysPermission sysPermission : permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                // 如果访问的Url和权限用户符合的话，返回true
                if (targetUrl.equals(sysPermission.getUrl()) && permissions.contains(targetPermission)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
