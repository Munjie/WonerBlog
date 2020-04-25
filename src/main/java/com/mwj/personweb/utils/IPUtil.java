package com.mwj.personweb.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {

  /**
   * @description //获取IP信息
   * @param:
   * @return:
   * @date: 2019/3/9 15:52
   */
  public static String getIpAddrByRequest(HttpServletRequest request) {
    String Xip = request.getHeader("X-Real-IP");
    String XFor = request.getHeader("X-Forwarded-For");
    if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
      // 多次反向代理后会有多个ip值，第一个ip才是真实ip
      int index = XFor.indexOf(",");
      if (index != -1) {
        return XFor.substring(0, index);
      } else {
        return XFor;
      }
    }
    XFor = Xip;
    if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
      return XFor;
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("HTTP_CLIENT_IP");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
      XFor = request.getRemoteAddr();
    }
    return XFor;
  }

  /**
   * @return 本机IPSocketException
   * @throws SocketException
   */
  public static String getRealIp() throws SocketException {
    String localip = null; // 本地IP，如果没有配置外网IP则返回它
    String netip = null; // 外网IP

    Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
    InetAddress ip = null;
    boolean finded = false; // 是否找到外网IP
    while (netInterfaces.hasMoreElements() && !finded) {
      NetworkInterface ni = netInterfaces.nextElement();
      Enumeration<InetAddress> address = ni.getInetAddresses();
      while (address.hasMoreElements()) {
        ip = address.nextElement();
        if (!ip.isSiteLocalAddress()
            && !ip.isLoopbackAddress()
            && !ip.getHostAddress().contains(":")) { // 外网IP
          netip = ip.getHostAddress();
          finded = true;
          break;
        } else if (ip.isSiteLocalAddress()
            && !ip.isLoopbackAddress()
            && !ip.getHostAddress().contains(":")) { // 内网IP
          localip = ip.getHostAddress();
        }
      }
    }

    if (netip != null && !"".equals(netip)) {
      return netip;
    } else {
      return localip;
    }
  }

  public static String getAgentByRequest(HttpServletRequest request) {

    // 获取浏览器信息
    Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
    // 获取浏览器版本号
    Version version = browser.getVersion(request.getHeader("User-Agent"));
    return browser.getName() + "/" + version.getVersion();
  }

  public static String getLocationByIP(String ip) {
    String locations = null;

    try {

      // 创建 GeoLite2 数据库
      File database = new File("/usr/local/GeoLite2-City.mmdb");
      // File database = new File("C:/GeoLite2-City.mmdb");
      // 读取数据库内容
      DatabaseReader reader = new DatabaseReader.Builder(database).build();
      InetAddress ipAddress = InetAddress.getByName(ip);

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
      locations = city.getNames().get("zh-CN");
      return locations;

    } catch (Exception e) {

      e.printStackTrace();
    }
    return locations;
  }
}
