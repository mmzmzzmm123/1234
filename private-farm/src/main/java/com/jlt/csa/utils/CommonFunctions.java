package com.jlt.csa.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包含公共工具方法的类
 */
public class CommonFunctions {

    /**
     * 解析编号格式，将非数字部分和整数部分拆分，包含在Map中返回
     * @param number 格式为 [最后一位为非数字的字符，非必须][多位数字]，如A8000,B6F8000,8000
     * @return  非数字部分放在key为prefix的字符串中(可选)，
     *          整数部分放在key为number的整数对象中，
     *          整数位数放在key为width的整数对象中，
     *          格式不正确返回空Map对象
     */
    public static Map<String, Object> parseCode(String number) {
        Map<String, Object> map = new HashMap<>();
        Pattern p = Pattern.compile("^(.*?)(\\d+)$");
        Matcher m = p.matcher(number);
        while(m.find()) {
            if(m.groupCount() == 2) {
                map.put("prefix", m.group(1));
                map.put("number", Integer.parseInt(m.group(2)));
                map.put("width", m.group(2).length());
            }
        }

        return map;
    }
}
