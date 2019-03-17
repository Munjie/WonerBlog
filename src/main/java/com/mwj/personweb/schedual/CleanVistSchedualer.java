package com.mwj.personweb.schedual;

import com.mwj.personweb.service.IVisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** @Auther: munjie @Date: 2019/3/17 13:11 @Description: */
@Service
public class CleanVistSchedualer {

  private Logger logger = LoggerFactory.getLogger(CleanVistSchedualer.class);

  @Autowired private IVisitService iVisitService;

  @Scheduled(cron = "0 0 1 * * ?")
  public void process() {

    int i = iVisitService.updateTodayZero();
    if (i != 0) {

      logger.info("更新今日归零成功");
    }
  }
}
