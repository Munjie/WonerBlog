package com.mwj.personweb.utils;

import net.sf.json.JSONObject;

/** @Auther: munjie @Date: 2019/3/12 19:22 @Description: */
public class ReturnMsgUtil {

  public static JSONObject bulidRetunMsg(String status, String msg) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("msg", msg);
    return jsonObject;
  }
}
