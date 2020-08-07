package com.ruoyi.project.tool.address.utils;

import com.ruoyi.project.tool.address.AddressResult;
import com.ruoyi.project.tool.address.parse.ParseContext;
import com.ruoyi.project.tool.address.parse.XParse;

/**
 * 地址清洗工具类
 *
 * @author lihe
 */
public class AddressUtil {
    /**
     * 清洗
     *
     * @param text
     * @return
     */
    public static AddressResult clear(String text) {
        ParseContext context = new ParseContext(text);
        XParse xParse = new XParse(context);
        xParse.parse();
        AddressResult result = new AddressResult(context);
        return result;
    }
}
