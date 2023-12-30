package com.ruoyi.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证url格式
 */
public class UrlValidator {
    public static boolean validateUrl(String url) {
        String regex = "^(http|https):\\/\\/.+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        return matcher.matches();
    }
}
