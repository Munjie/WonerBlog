package com.mwj.personweb.service;

import com.mwj.personweb.model.Tags;
import net.sf.json.JSONObject;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:32 @Description: */
public interface ITagsService {

  /**
   * 加入标签
   *
   * @param tags 一群标签
   * @param tagSize 标签大小
   */
  void addTags(String[] tags, int tagSize);

  /**
   * 获得标签云
   *
   * @return
   */
  JSONObject findTagsCloud();

  /**
   * 获得标签云数量
   *
   * @return
   */
  int countTagsNum();

  /**
   * 通过标签名获得标签大小
   *
   * @param tagName
   * @return
   */
  int getTagsSizeByTagName(String tagName);
  /**
   * @description //TODO
   * @param:
   * @return:
   * @date: 2019/3/9 22:10
   */
  List<Tags> allTags();
}
