package com.mwj.personweb.controller;

import com.mwj.personweb.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** @Auther: munjie @Date: 2019/3/12 23:46 @Description: */
@RestController
public class SwgController {

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String hello() {
    return "hello";
  }

  ArrayList<User> users = new ArrayList<>();

  @ApiOperation(value = "获取用户列表", notes = "获取所有用户信息")
  @RequestMapping(
      value = {""},
      method = RequestMethod.GET)
  public List<User> listUser() {
    users.add(new User("java", 100));
    users.add(new User("python", 99));
    return users;
  }

  @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public User postUser(User user) {

    return user;
  }

  @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
  @RequestMapping(value = "getUser/{id}", method = RequestMethod.GET)
  public User getUser(@PathVariable(value = "id") String id) {

    return new User(id, "mwj", "6666");
  }
}
