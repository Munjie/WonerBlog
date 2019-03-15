package com.mwj.personweb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/** @Author: 母哥 @Date: 2019-02-27 11:46 @Version 1.0 */
public class JsonUtil {

  /**
   * 1.通过JSON.parse把json转换为Map
   *
   * @param jsonStr
   */
  public Map getJsonToMap(String jsonStr) {
    Map maps = (Map) JSON.parse(jsonStr);
    //    for (Object map : maps.entrySet()) {
    //      System.out.println(
    //          "key为：" + ((Map.Entry) map).getKey() + ", 值为：" + ((Map.Entry) map).getValue());
    //    }

    return maps;
  }

  /**
   * 2.通过JSON.parseObject把json转换为Map
   *
   * @param jsonStr
   * @return
   */
  public Map getJsonToMapByClass(String jsonStr) {
    jsonStr = "{\"userName\":\"lmx\",\"pwd\":\"123456\",\"address\":\"sz\",\"Id\":\"1\"}";
    Map mapTypes = JSON.parseObject(jsonStr, Map.class);
    for (Object obj : mapTypes.keySet()) {
      System.out.println("key为：" + obj + ", 值为：" + mapTypes.get(obj));
    }

    return mapTypes;
    /*  输出:  key为：address, 值为：sz
             key为：Id, 值为：1
             key为：userName, 值为：lmx
             key为：pwd, 值为：123456
    */
  }

  /**
   * 3.通过JSON.parseObject把json转换为Object
   *
   * @param jsonStr
   * @return
   */
  public Map getJsonToObject(String jsonStr) {
    Map mapTypes = JSON.parseObject(jsonStr);
    for (Object obj : mapTypes.keySet()) {
      System.out.println("key为：" + obj + ", 值为：" + mapTypes.get(obj));
    }

    return mapTypes;

    /* 输出: key为：address, 值为：sz
             key为：Id, 值为：1
             key为：userName, 值为：lmx
             key为：pwd, 值为：123456
    */
  }

  /**
   * 4.通过JSONObject.parse把json转换为Map
   *
   * @param jsonStr
   */
  public Map getJSONObjectToMap(String jsonStr) {
    jsonStr = "{\"userName\":\"lmx\",\"pwd\":\"123456\",\"address\":\"sz\",\"Id\":\"1\"}";
    Map json = (Map) JSONObject.parse(jsonStr);
    //    for (Object map : json.entrySet()) {
    //      System.out.println(
    //          "key为：" + ((Map.Entry) map).getKey() + ", 值为：" + ((Map.Entry) map).getValue());
    //    }
    return json;
    /* 输出: key为：address, 值为：sz
           key为：Id, 值为：1
           key为：userName, 值为：lmx
           key为：pwd, 值为：123456
    */
  }

  /**
   * 5.通过JSONObject.parseObject把json转换为JSONObject,并遍历单Object输出
   *
   * @param jsonStr
   */
  public JSONObject getJSONObjectToObject(String jsonStr) {
    JSONObject jsonObject = JSONObject.parseObject(jsonStr);
    //    for (Object map : jsonObject.entrySet()) {
    //      System.out.println(
    //          "key为：" + ((Map.Entry) map).getKey() + ", 值为：" + ((Map.Entry) map).getValue());
    //    }
    return jsonObject;
    /* 输出: key为：address, 值为：sz
            key为：Id, 值为：1
            key为：userName, 值为：lmx
            key为：pwd, 值为：123456
    */
  }

  /**
   * 6.通过JSONObject.parseObject把json转换为Map
   *
   * @param jsonStr
   */
  public Map getJSONObjectToMapClass(String jsonStr) {
    jsonStr = "{\"userName\":\"lmx\",\"pwd\":\"123456\",\"address\":\"sz\",\"Id\":\"1\"}";
    Map mapObj = JSONObject.parseObject(jsonStr, Map.class);

    for (Object map : mapObj.entrySet()) {
      System.out.println(
          "key为：" + ((Map.Entry) map).getKey() + ", 值为：" + ((Map.Entry) map).getValue());
    }
    return mapObj;
    /* 输出: key为：address, 值为：sz
           key为：Id, 值为：1
           key为：userName, 值为：lmx
           key为：pwd, 值为：123456
    */
  }

  ////////////// List集合和JSON相互转换工具类//////////////////////////
  /**
   * 7.通过JSON.toJSONString把List<T>转换为json
   *
   * @param obj
   * @return
   */
  public static <T> String getListToJson(List<T> obj) {
    String jsonStr = JSON.toJSONString(obj);
    return jsonStr;
    // String输出: ["aa","bb","cc"]
    // userModel输出:
    // [{"cId":"100","pwd":"123456","uName":"lmx"},{"cId":"200","pwd":"123456","uName":"jqy"}]
  }

  /**
   * 8.通过JSONArray.parseArray把json转换为List<T>
   *
   * @param jsonStr
   * @param model
   * @return
   */
  public static <T> List<T> getStringToList(String jsonStr, Class<T> model) {
    List<T> object = (List<T>) JSONArray.parseArray(jsonStr, model);
    return object;
    // String输出: "aa","bb","cc"
    // userModel输出: lmx,jqy
  }

  ////////////// 对象和JSON相互转换工具类/////////////////////////

  /**
   * 9.通过JSONObject.toJSONString把对象转换为String
   *
   * @param model
   * @return
   */
  public static <T> String getObjectToJson(T model) {
    String StrObject = null;
    try {

      StrObject = JSON.toJSONString(model);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return StrObject;
  }

  /**
   * 10.通过JSONObject.parseObject把String转换为对象
   *
   * @param jsonStr
   * @param model
   * @return
   */
  public static <T> T getStringToObject(String jsonStr, Class<T> model) {
    return JSONObject.parseObject(jsonStr, model);

    // User输出:  {"cId":"100","pwd":"123456","uName":"lmx"}
    // Map输出:   {"uName":"llmmxx","pwd":"123","cId":"300"}

  }
}
