package com.mwj.personweb;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;

import java.io.File;
import java.net.InetAddress;

/** @Auther: munjie @Date: 2019/3/16 11:14 @Description: */
public class iptest {

  public static void main(String[] args) throws Exception {
    // 创建 GeoLite2 数据库
    File database = new File("C:/GeoLite2-City.mmdb");
    // 读取数据库内容
    DatabaseReader reader = new DatabaseReader.Builder(database).build();
    InetAddress ipAddress = InetAddress.getByName("183.192.11.36");

    // 获取查询结果
    CityResponse response = reader.city(ipAddress);

    // 获取国家信息
    Country country = response.getCountry();
    System.out.println(country.getIsoCode()); // 'CN'
    System.out.println(country.getName()); // 'China'
    System.out.println(country.getNames().get("zh-CN")); // '中国'

    // 获取省份
    Subdivision subdivision = response.getMostSpecificSubdivision();
    System.out.println(subdivision.getName()); // 'Guangxi Zhuangzu Zizhiqu'
    System.out.println(subdivision.getIsoCode()); // '45'
    System.out.println(subdivision.getNames().get("zh-CN")); // '广西壮族自治区'

    // 获取城市
    City city = response.getCity();
    System.out.println(city.getName()); // 'Nanning'
    Postal postal = response.getPostal();
    System.out.println(postal.getCode()); // 'null'
    System.out.println(city.getNames().get("zh-CN")); // '南宁'
    Location location = response.getLocation();
    System.out.println(location.getLatitude()); // 22.8167
    System.out.println(location.getLongitude()); // 108.3167
  }
}
