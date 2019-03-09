package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.ITagsDao;
import com.mwj.personweb.model.Tags;
import com.mwj.personweb.service.ITagsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Auther: munjie @Date: 2019/3/9 19:33 @Description: */
@Service
public class TagsServiceImpl implements ITagsService {

  @Autowired private ITagsDao tagMapper;

  @Override
  public void addTags(String[] tags, int tagSize) {
    for (String tag : tags) {
      if (tagMapper.findIsExitByTagName(tag) == 0) {
        Tags t = new Tags(tag, tagSize);
        tagMapper.insertTag(t);
      }
    }
  }

  @Override
  public JSONObject findTagsCloud() {
    List<Tags> tags = tagMapper.findTagsCloud();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", 200);
    jsonObject.put("result", JSONArray.fromObject(tags));
    jsonObject.put("tagsNum", tags.size());
    return jsonObject;
  }

  @Override
  public int countTagsNum() {
    return tagMapper.countTagsNum();
  }

  @Override
  public int getTagsSizeByTagName(String tagName) {
    return tagMapper.getTagsSizeByTagName(tagName);
  }
}
