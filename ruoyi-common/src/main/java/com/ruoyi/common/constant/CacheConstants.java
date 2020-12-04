/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: CacheConstants
 * Author:   wh
 * Date:     2020/11/5 20:17
 * Description: 缓存常量信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ruoyi.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 * <缓存常量信息>
 *
 * @program: ruoyi
 * @ClassName: CacheConstants
 * @author: wanghui
 * @date: 2020/11/5 20:17
 * @Version: 1.0.0
 * @Copyright: 2020 Inc. All rights reserved. 注意: 本内容仅限于内部传阅,禁止外泄以及用于其他的商业目
 */
public class CacheConstants {

    /**缓存类型枚举内部类*/
    public enum CacheType {
        /**系统缓存*/
        ehcache("ehcache"),
        /**Redis缓存*/
        redis("redis");

        /**缓存类型名称*/
        private String name;

        CacheType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class CacheKey {

        public static Map<String,String> map = new HashMap<String,String>(){
            {
                put(Constants.SYS_CONFIG_CACHE, Constants.SYS_CONFIG_KEY);
                put(Constants.SYS_DICT_CACHE, Constants.SYS_DICT_KEY);
                put(Constants.LOGIN_TOKEN_CACHE, Constants.LOGIN_TOKEN_KEY);
                put(Constants.REPEAT_SUBMIT_CACHE, Constants.REPEAT_SUBMIT_KEY);
                put(Constants.CAPTCHA_CODE_CACHE, Constants.CAPTCHA_CODE_KEY);
            }
        };
    }
}
