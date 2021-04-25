package com.ruoyi.common.core.controller;

/**
 * @author : yelifeng
 * @date : 2020/10/20 0020 17:51 xuc5566@gmail.com
 */

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * controller父类
 *
 * @author yanghaipeng
 * @date 2019/05/13
 */
public class AbstractController {

    /**
     * 封装http 输入数据
     *
     * @param params
     *            参数列表
     * @param formClass
     *            需要转化的对象类型
     * @return
     */
    protected <T> T parseRequestParamsForm(Map<String, Object> params, Class<T> formClass) {
        String jsonString = JSONObject.toJSONString(params);
        T form = JSONObject.parseObject(jsonString, formClass);

        return form;
    }

}
