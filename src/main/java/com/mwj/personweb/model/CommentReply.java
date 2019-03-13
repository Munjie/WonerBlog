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
  private String replyImg;

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

  public String getReplyImg() {
    return replyImg;
  }

  public void setReplyImg(String replyImg) {
    this.replyImg = replyImg;
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
        + ", replyImg='"
        + replyImg
        + '\''
        + '}';
  }
}
