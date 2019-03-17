package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/17 10:30 @Description: */
public class Visitor {

  private int id;

  private long visttotle;

  private String vistpage;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getVisttotle() {
    return visttotle;
  }

  public void setVisttotle(long visttotle) {
    this.visttotle = visttotle;
  }

  public String getVistpage() {
    return vistpage;
  }

  public void setVistpage(String vistpage) {
    this.vistpage = vistpage;
  }

  @Override
  public String toString() {
    return "Visitor{"
        + "id="
        + id
        + ", visttotle="
        + visttotle
        + ", vistpage='"
        + vistpage
        + '\''
        + '}';
  }
}
