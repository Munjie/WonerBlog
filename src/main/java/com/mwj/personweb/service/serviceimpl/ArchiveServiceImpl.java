package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IArchiveDao;
import com.mwj.personweb.service.IArchiveService;
import com.mwj.personweb.service.IArticleService;
import com.mwj.personweb.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** @Author: 母哥 @Date: 2019-03-08 15:46 @Version 1.0 */
@Service
public class ArchiveServiceImpl implements IArchiveService {

  @Autowired private IArchiveDao archiveDao;
  @Autowired private IArticleService articleService;

  @Override
  public JSONObject findArchiveNameAndArticleNum() {
    List<String> archives = archiveDao.findArchives();
    JSONArray archivesJsonArray = new JSONArray();
    JSONObject archiveJson;
    for (String archiveName : archives) {
      archiveJson = new JSONObject();
      archiveJson.put("archiveName", archiveName);
      archiveName = TimeUtil.timeYearToWhippletree(archiveName);
      archiveJson.put(
          "archiveArticleNum", articleService.countArticleArchiveByArchive(archiveName));
      archivesJsonArray.add(archiveJson);
    }
    JSONObject returnJson = new JSONObject();
    returnJson.put("status", 200);
    returnJson.put("result", archivesJsonArray);
    return returnJson;
  }

  @Override
  public void addArchiveName(String archiveName) {
    int archiveNameIsExit = archiveDao.findArchiveNameByArchiveName(archiveName);
    if (archiveNameIsExit == 0) {
      archiveDao.addArchiveName(archiveName);
    }
  }
}
