package com.mwj.personweb.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

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
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
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
