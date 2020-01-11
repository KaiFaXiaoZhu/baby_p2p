package com.baby.common;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 */
public class IPUtil {

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null)
            return "";
        String ip = request.getHeader("X-Requested-For");
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if("0:0:0:0:0:0:0:1".equals(ip)){
            return "127.0.0.1";
        }
        return ip;
    }


    public static boolean isLocalHost(String ip){
        return "127.0.0.1".equals(ip) || "localhost".equals(ip);
    }
}
