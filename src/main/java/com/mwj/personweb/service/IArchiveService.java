package com.mwj.personweb.service;

import net.sf.json.JSONObject;

/**
 * @Author: 母哥 @Date: 2019-03-08 15:44 @Version 1.0
 */
public interface IArchiveService {

    /**
     * 获得归档信息
     *
     * @return
     */
    JSONObject findArchiveNameAndArticleNum();

    /**
     * 添加归档日期
     *
     * @param archiveName
     */
    void addArchiveName(String archiveName);
}
