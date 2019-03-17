package com.mwj.personweb.service;

/** @Auther: munjie @Date: 2019/3/17 10:26 @Description: */
public interface IVisitService {

  void updateTotal();

  void updateOther();

  long getToday();

  long getHisTory();

  long getOther();
}
