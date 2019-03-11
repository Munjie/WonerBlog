package com.mwj.personweb.service.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mwj.personweb.dao.ITagsDao;
import com.mwj.personweb.model.Tags;
import com.mwj.personweb.service.ITagsService;
import com.mwj.personweb.service.redis.RedisServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @Auther: munjie @Date: 2019/3/9 19:33 @Description: */
@Service
public class TagsServiceImpl implements ITagsService {

  private static Logger logger = LoggerFactory.getLogger(TagsServiceImpl.class);

  @Autowired private ITagsDao tagsDao;

  @Autowired private RedisServer redisServer;

  @Override
  public void addTags(String[] tags, int tagSize) {
    for (String tag : tags) {
      if (tagsDao.findIsExitByTagName(tag) == 0) {
        Tags t = new Tags(tag, tagSize);
        tagsDao.insertTag(t);
      }
    }
    if (redisServer.hasKey("tags")) {
      redisServer.remove("tags");
    }
  }

  @Override
  public JSONObject findTagsCloud() {
    List<Tags> tags = tagsDao.findTagsCloud();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", 200);
    jsonObject.put("result", JSONArray.fromObject(tags));
    jsonObject.put("tagsNum", tags.size());
    return jsonObject;
  }

  @Override
  public int countTagsNum() {
    return tagsDao.countTagsNum();
  }

  @Override
  public int getTagsSizeByTagName(String tagName) {
    return tagsDao.getTagsSizeByTagName(tagName);
  }

  @Override
  public List<Tags> allTags() {

    List<Tags> tagsCloud = tagsDao.findTagsCloud();
    return tagsCloud;
  }

  @Override
  public JSONArray findAllTags(String rows, String pageNo) {

    int pageNum = Integer.parseInt(pageNo);
    int pageSize = Integer.parseInt(rows);

    List<Tags> allTags = tagsDao.findAllTags();
    PageHelper.startPage(pageNum, pageSize);

    PageInfo<Tags> pageInfo = new PageInfo<>(allTags);
    List<Map<String, Object>> newTags = new ArrayList<>();
    Map<String, Object> map;

    for (Tags tags : allTags) {
      map = new HashMap<>();
      map.put("id", tags.getId());
      map.put("tagName", tags.getTagName());
      map.put("tagSize", tags.getTagSize());
      newTags.add(map);
    }
    JSONArray jsonArray = JSONArray.fromObject(newTags);
    Map<String, Object> thisPageInfo = new HashMap<>();
    thisPageInfo.put("pageNum", pageInfo.getPageNum());
    thisPageInfo.put("pageSize", pageInfo.getPageSize());
    thisPageInfo.put("total", pageInfo.getTotal());
    thisPageInfo.put("pages", pageInfo.getPages());
    thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
    thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());

    jsonArray.add(thisPageInfo);
    return jsonArray;
  }

  @Override
  public JSONObject deleteTags(int id) {
    JSONObject jsonObject = new JSONObject();
    try {
      tagsDao.deleteTags(id);
      jsonObject.put("status", 200);
      if (redisServer.hasKey("tags")) {
        redisServer.remove("tags");
      }
    } catch (Exception e) {
      logger.error("删除标签失败", e);
      jsonObject.put("status", 400);
    }

    return jsonObject;
  }

  @Override
  public JSONObject bachDeleteTags(List list) {
    JSONObject jsonObject = new JSONObject();
    try {
      tagsDao.batchDelete(list);
      jsonObject.put("status", 200);
      if (redisServer.hasKey("tags")) {
        redisServer.remove("tags");
      }
    } catch (Exception e) {
      logger.error("删除标签失败", e);
      jsonObject.put("status", 400);
    }

    return jsonObject;
  }
}
