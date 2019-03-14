package com.mwj.personweb.model;

/** @Auther: munjie @Date: 2019/3/13 23:44 @Description: */
public class CommentReply {

  private int id;
  private int coid;
  private int authorId;
  private int replyId;
  private String comment;
  private int likes;
  private String created;
  private String authorImg;
  private String replyName;
  private String authorName;
  private String ip;
  private String agent;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCoid() {
    return coid;
  }

  public void setCoid(int coid) {
    this.coid = coid;
  }

  public int getAuthorId() {
    return authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public int getReplyId() {
    return replyId;
  }

  public void setReplyId(int replyId) {
    this.replyId = replyId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getReplyName() {
    return replyName;
  }

  public void setReplyName(String replyName) {
    this.replyName = replyName;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getAuthorImg() {
    return authorImg;
  }

  public void setAuthorImg(String authorImg) {
    this.authorImg = authorImg;
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

  @Override
  public String toString() {
    return "CommentReply{"
        + "id="
        + id
        + ", coid="
        + coid
        + ", authorId="
        + authorId
        + ", replyId="
        + replyId
        + ", comment='"
        + comment
        + '\''
        + ", likes="
        + likes
        + ", created='"
        + created
        + '\''
        + ", authorImg='"
        + authorImg
        + '\''
        + ", replyName='"
        + replyName
        + '\''
        + ", authorName='"
        + authorName
        + '\''
        + ", ip='"
        + ip
        + '\''
        + ", agent='"
        + agent
        + '\''
        + '}';
  }
}
