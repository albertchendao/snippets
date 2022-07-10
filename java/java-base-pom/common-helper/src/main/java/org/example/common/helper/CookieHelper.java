package org.example.common.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Cookie option
 * User: qhn
 * Date: 14-9-30
 * Time: am 10:55
 */
public class CookieHelper {
    /**
     * 根据cookie名称解析获取cookie中保存的value以数组的方式返回
     *
     * @param name    名称
     * @param request 请求
     */
    public static String[] getCookieValueArray(String name, HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            for (Cookie c : cookies) {
                if (StringUtils.isEmpty(c.getName()) || !StringUtils.equals(name, c.getName().trim())) {
                    continue;
                }
                String cookieValue = c.getValue();
                if (StringUtils.isEmpty(cookieValue)) {
                    continue;
                }
                String status = cookieValue.substring(cookieValue.length() - 1);
                cookieValue = cookieValue.substring(0, cookieValue.length() - 1) + "," + status;
                String[] cooks = StringUtils.split(cookieValue, ",");
                return cooks;
            }
        }
        return null;
    }

    /**
     * 根据cookie名称解析获取保存的value
     *
     * @param name    名称
     * @param request 请求
     */
    public static String getCookieValue(String name, HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            for (Cookie c : cookies) {
                if (StringUtils.isEmpty(c.getName()) || !StringUtils.equals(name, c.getName().trim())) {
                    continue;
                }
                String cookieValue = c.getValue();
                if (StringUtils.isEmpty(cookieValue)) {
                    continue;
                }
                cookieValue = cookieValue.substring(0, cookieValue.length() - 1);
                return cookieValue;
            }
        }
        return null;
    }

    /**
     * 根据名称添加cookie或修改对应名称cookie的value
     *
     * @param name     名称
     * @param valueStr 值
     * @param expiry   有效时间（单位小时）
     * @param status   状态 1：开启0：关闭
     * @param response 响应
     */
    public static void setCookie(String name, String valueStr, int expiry, String status, HttpServletResponse response) {
        if (!StringUtils.isEmpty(valueStr)) {
            if (!"1".equals(status)) {
                status = "0";
            }
            valueStr = valueStr + status;
            //添加cookie
            Cookie cookie = new Cookie(name, valueStr);
            cookie.setMaxAge(60 * 60 * expiry);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }


    /**
     * 追加cookie
     *
     * @param name
     * @param valueStr
     * @param expiry
     * @param status
     * @param response
     * @param type     1，追加valueStr，-1删除vlaueStr
     */
    public static void appendCookie(String name, String valueStr, int expiry, String status, HttpServletRequest request, HttpServletResponse response, int type) {
        String[] cookValArr = getCookieValueArray(name, request);
        if (type != 1) {
            type = -1;
        }
        if (cookValArr == null) {
            setCookie(name, valueStr, expiry, status, response);
        } else {
            String cookVals = "";
            boolean hasExist = false;
            for (String cook : cookValArr) {
                if (cook.equals(valueStr)) {
                    hasExist = true;
                    if (type == -1) {
                        continue;
                    }
                }
                cookVals += cook + ",";
            }
            if (!hasExist && type == 1) {
                cookVals += valueStr + ",";
            }
            if (cookVals.endsWith(",")) {
                cookVals = cookVals.substring(0, cookVals.length() - 1);
            }
            setCookie(name, cookVals, expiry, status, response);
        }

    }

    /**
     * 根据cookie名称修改cookie状态
     *
     * @param name     名称
     * @param status   状态 1：开启0：关闭
     * @param request  请求
     * @param response 响应
     */
    public static void updateCookieStatus(String name, int expiry, String status, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(name)) {
            //cookie状态默认只有
            if (!"1".equals(status)) {
                status = "0";
            }
            String valueStr = getCookieValue(name, request);
            setCookie(name, valueStr, expiry, status, response);
        }
    }

    /**
     * 根据cookie名称删除cookie
     *
     * @param name     名称
     * @param response 响应
     */
    public static void deleteCookie(String name, HttpServletResponse response) {
        if (!StringUtils.isEmpty(name)) {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    /**
     * 解析获取访问者IP
     *
     * @param request 请求
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotEmpty(ip) && StringUtils.contains(ip, ",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 获取前段JS设置cookie的 value
     *
     * @param name    名称
     * @param request 请求
     */
    public static String getJsCookieValue(String name, HttpServletRequest request) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            for (Cookie c : cookies) {
                if (StringUtils.isEmpty(c.getName()) || !StringUtils.equals(name, c.getName().trim())) {
                    continue;
                }
                String cookieValue = c.getValue();
                if (StringUtils.isEmpty(cookieValue)) {
                    continue;
                }
                return unescape(cookieValue);
            }
        }
        return null;
    }

    /**
     * 对应javascript的escape()函数, 加码后的串可直接使用javascript的unescape()进行解码
     */
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 对应javascript的unescape()函数, 可对javascript的escape()进行解码
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

}


