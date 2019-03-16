package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/16 00:10 @Description: */
public class Message {

  private int id;
  private String sysuser;
  private int articleid;
  private String msg;
  private String creattime;
  private String status;
  private String comname;
  private String hdimg;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSysuser() {
    return sysuser;
  }

  public void setSysuser(String sysuser) {
    this.sysuser = sysuser;
  }

  public int getArticleid() {
    return articleid;
  }

  public void setArticleid(int articleid) {
    this.articleid = articleid;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getCreattime() {
    return creattime;
  }

  public void setCreattime(String creattime) {
    this.creattime = creattime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getComname() {
    return comname;
  }

  public void setComname(String comname) {
    this.comname = comname;
  }

  public String getHdimg() {
    return hdimg;
  }

  public void setHdimg(String hdimg) {
    this.hdimg = hdimg;
  }

  @Override
  public String toString() {
    return "Message{"
        + "id="
        + id
        + ", name='"
        + sysuser
        + '\''
        + ", articleid="
        + articleid
        + ", msg='"
        + msg
        + '\''
        + ", create='"
        + creattime
        + '\''
        + ", status='"
        + status
        + '\''
        + ", comname='"
        + comname
        + '\''
        + ", hdimg='"
        + hdimg
        + '\''
        + '}';
  }
}
