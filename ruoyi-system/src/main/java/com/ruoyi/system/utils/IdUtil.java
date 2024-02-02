package com.ruoyi.system.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import org.springframework.util.DigestUtils;

import java.util.Iterator;
import java.util.List;


public class IdUtil {
    public static String sumId(Object... ids) {
        return sumId(CollectionUtil.toList(ids));
    }

    public static String sumId(List<Object> fields) {
        Assert.notEmpty(fields);
        StringBuffer sb = new StringBuffer();
        Iterator var2 = fields.iterator();

        while(var2.hasNext()) {
            Object f = var2.next();
            sb.append(f == null ? "" : f.toString().trim());
        }

        return md5(sb.toString());
    }

    public static String md5(String str) {
        return str == null ? null : DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
    }
}
