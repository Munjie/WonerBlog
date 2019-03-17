package com.mwj.personweb.service.serviceimpl;

import com.mwj.personweb.dao.IVisitorDao;
import com.mwj.personweb.service.IVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Auther: munjie @Date: 2019/3/17 10:27 @Description: */
@Service
public class VistServiceImpl implements IVisitService {

  @Autowired private IVisitorDao visitorDao;

  private String visitor;

  @Override
  public void updateTotal() {
    visitorDao.updateHistory();
    visitorDao.updateToday();
  }

  @Override
  public void updateOther() {

    visitorDao.updateOther();
  }

  @Override
  public long getToday() {
    return visitorDao.getToday();
  }

  @Override
  public long getHisTory() {
    return visitorDao.getHistory();
  }

  @Override
  public long getOther() {
    return visitorDao.getOther();
  }

  @Override
  public int updateTodayZero() {
    return visitorDao.updateTodayZero();
  }

  @Override
  public int updatePc() {
    return visitorDao.updatePc();
  }

  @Override
  public int updateMobile() {
    return visitorDao.updateMobile();
  }

  @Override
  public long getPc() {
    return visitorDao.getPc();
  }

  @Override
  public long getMobile() {
    return visitorDao.getMobile();
  }
}
