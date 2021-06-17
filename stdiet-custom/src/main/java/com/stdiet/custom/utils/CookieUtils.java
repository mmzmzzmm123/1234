package com.stdiet.custom.utils;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

public class CookieUtils {

    public static String getCookieValue(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (StringUtils.isNull(cookies)) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static JSONObject checkCookieValida(HttpServletRequest req, String cookieName) {
        JSONObject resultObj = new JSONObject();

        String token = getCookieValue(req, "token");
        if (StringUtils.isEmpty(token)) {
            resultObj.put("code", 50102);
            resultObj.put("msg", "请重新登录");
            return resultObj;
        }

        String tokenStr = AesUtils.decrypt(token);
        String[] vals = tokenStr.split("_");
        String phone = vals[0];
        Long fromDate = Long.parseLong(vals[1]);
        String randomCode = vals[2];

        resultObj.put("code", 200);
        resultObj.put("phone", phone);


        if (new Date().getTime() - fromDate > 24 * 60 * 60 * 1000) {
            resultObj.put("code", 50102);
            resultObj.put("msg", "请重新登录");
            return resultObj;
        }

        return resultObj;
    }
}
