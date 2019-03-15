package com.mwj.personweb.model;

import java.io.Serializable;
import java.util.Date;

/** @Author: 母哥 @Date: 2019-02-28 14:51 @Version 1.0 */
public class SysUser implements Serializable {

  static final long serialVersionUID = 1L;

  private int id;

  private String name;

  private String password;

  private String email;

  private String imgUrl;

  private String username;

  private String phone;

  private String qq;

  private String wechat;

  private String birth;

  private Date logintime;

  private Date outtime;

  private long onlinetimes;

  private int lev;

  private long levstatus;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public Date getLogintime() {
    return logintime;
  }

  public void setLogintime(Date logintime) {
    this.logintime = logintime;
  }

  public Date getOuttime() {
    return outtime;
  }

  public void setOuttime(Date outtime) {
    this.outtime = outtime;
  }

  public long getOnlinetimes() {
    return onlinetimes;
  }

  public void setOnlinetimes(long onlinetimes) {
    this.onlinetimes = onlinetimes;
  }

  public int getLev() {
    return lev;
  }

  public void setLev(int lev) {
    this.lev = lev;
  }

  public long getLevstatus() {
    return levstatus;
  }

  public void setLevstatus(long levstatus) {
    this.levstatus = levstatus;
  }
}
