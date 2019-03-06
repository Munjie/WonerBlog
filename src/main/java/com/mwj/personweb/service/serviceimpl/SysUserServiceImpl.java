package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IUserDao;
import com.mwj.personweb.model.SysUser;
import com.mwj.personweb.service.ISysUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: 母哥 @Date: 2019-03-01 10:54 @Version 1.0
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public SysUser findById(Integer id) {
        return userDao.findById(id);
    }

    public SysUser findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public Boolean isExitUser(String name) {

        SysUser user = userDao.findByName(name);
        return user != null;
    }

    @Override
    public JSONObject insertUser(SysUser sysUser) {
        JSONObject jsonObject = new JSONObject();
        if (sysUser != null) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setImgUrl("http://cdn.biubiucat.com/u=62256820,3946498744&fm=26&gp=0.jpg");
            int i = userDao.insertSysUser(sysUser);

            if (i > 0) {

                jsonObject.put("status", "200");
            } else {

                jsonObject.put("status", "400");
            }
        } else {

            jsonObject.put("status", "400");
        }

        return jsonObject;
  }
}
