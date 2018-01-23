package com.aspire.hdc.common.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpInfoUtil
{
  private static Logger LOGGER = LoggerFactory.getLogger(IpInfoUtil.class);
  
  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("x-forwarded-for");
    if ((isEffective(ip)) && (ip.indexOf(",") > -1))
    {
      String[] array = ip.split(",");
      for (String element : array) {
        if (isEffective(element))
        {
          ip = element;
          LOGGER.info("当前x-forwarded-for Ip:{}", ip);
          break;
        }
      }
    }
    if (!isEffective(ip))
    {
      ip = request.getHeader("X-Real-IP");
      LOGGER.info("当前X-Real-IP:{}", ip);
    }
    if (!isEffective(ip))
    {
      ip = request.getHeader("Proxy-Client-IP");
      LOGGER.info("当前Proxy-Client-IP:{}", ip);
    }
    if (!isEffective(ip))
    {
      ip = request.getHeader("WL-Proxy-Client-IP");
      LOGGER.info("当前WL-Proxy-Client-IP:{}", ip);
    }
    if (!isEffective(ip))
    {
      ip = request.getRemoteAddr();
      LOGGER.info("当前request.getRemoteAddr():{}", ip);
    }
    if (!isEffective(ip))
    {
      LOGGER.error("get romote Ip error port is null");
      
      ip = "";
    }
    return ip;
  }
  
  public static int getRemotePort(HttpServletRequest request)
  {
    return request.getRemotePort();
  }
  
  private static boolean isEffective(String remoteAddr)
  {
    boolean isEffective = false;
    if ((StringUtils.isNotBlank(remoteAddr)) && (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
      isEffective = true;
    }
    return isEffective;
  }
}
