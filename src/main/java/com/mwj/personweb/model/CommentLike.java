package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/13 16:07 @Description: */
public class CommentLike {
  private int id; // 点赞id
  private Integer cid; // 文章id
  private Integer coid; // 评论id
  private Integer authorId; // 点赞用户id
  private String ip; // 点赞用户ip
  private String agent; // 点赞用户客户端

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getCid() {
    return cid;
  }

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public Integer getCoid() {
    return coid;
  }

  public void setCoid(Integer coid) {
    this.coid = coid;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }
}
