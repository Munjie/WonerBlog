package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/16 00:10 @Description: */
public class Message {

  private int id;
  private int articleid;
  private String name;
  private String msg;
  private String create;
  private String status;
  private String comname;
  private String comimg;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getArticleid() {
    return articleid;
  }

  public void setArticleid(int articleid) {
    this.articleid = articleid;
  }

  public String getCreate() {
    return create;
  }

  public void setCreate(String create) {
    this.create = create;
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

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getComimg() {
    return comimg;
  }

  public void setComimg(String comimg) {
    this.comimg = comimg;
  }
}
