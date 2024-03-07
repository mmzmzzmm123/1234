package com.ruoyi.common.utils;

import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

/**
 * @author : yangyouqi
 * @date : 2023/10/24 0024 13:47
 */
public class PhoneUtils {

    public static Boolean isMobiPhoneNum(String phone) {
        if (ObjectUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(phone).matches();
    }

}
