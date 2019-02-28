package com.mwj.personweb.service;

import com.mwj.personweb.model.SysLog;

import java.util.List;

/**
 * @Author: 母哥
 * @Date: 2019-02-14 9:40
 * @Version 1.0
 */
public interface ISysLogService {


    void addLog(SysLog sysLog);


    int getLogCount();

    int getViewCount();

    List<SysLog> listAllLog();




}
