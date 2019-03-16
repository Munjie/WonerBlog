package com.mwj.personweb.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
}
