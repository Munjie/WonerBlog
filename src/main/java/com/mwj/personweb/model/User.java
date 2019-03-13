package com.mwj.personweb.model;

import io.swagger.annotations.ApiModelProperty;

/** @Auther: munjie @Date: 2019/3/13 00:24 @Description: */
public class User {
  @ApiModelProperty(hidden = true)
  private String id;

  @ApiModelProperty(value = "用户名")
  private String username;

  @ApiModelProperty(value = "密码")
  private String password;

  @ApiModelProperty(value = "邮箱")
  private String email;

  @ApiModelProperty(hidden = true)
  private Integer age;

  @ApiModelProperty(hidden = true)
  private Boolean enabled;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.age = age;
    this.enabled = enabled;
  }

  public User(String username, Integer age) {
    this.username = username;
    this.age = age;
  }
}
