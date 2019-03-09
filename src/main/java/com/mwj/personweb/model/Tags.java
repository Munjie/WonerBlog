package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/9 19:35 @Description: */
public class Tags {

  private int id;

  /** 标签名 */
  private String tagName;

  /** 标签大小 */
  private int tagSize;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public int getTagSize() {
    return tagSize;
  }

  public void setTagSize(int tagSize) {
    this.tagSize = tagSize;
  }

  public Tags(String tagName, int tagSize) {
    this.tagName = tagName;
    this.tagSize = tagSize;
  }
}
