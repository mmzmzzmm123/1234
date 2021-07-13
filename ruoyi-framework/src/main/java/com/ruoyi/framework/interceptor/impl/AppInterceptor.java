package com.ruoyi.framework.interceptor.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.mzt.MztSample;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * APP接口拦截器，验证闽政通token，用户id的合法性
 *
 * @author ruoyi
 */
@Component
public class AppInterceptor extends HandlerInterceptorAdapter
{

    private static final String HEADER_MZT_USER_TOKEN="MZT_USER_TOKEN";
    private static final String HEADER_MZT_USER_ID="MZT_USER_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        String token = request.getHeader(HEADER_MZT_USER_TOKEN);
        String userId = request.getHeader(HEADER_MZT_USER_ID);
        if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(userId))
        {
            //TODO:闽政通鉴权
//            String url = "https://mztapp.fujian.gov.cn:8304/"
//                    + "dataset/AppSerController/invokeservice.do";
//            Map<String,Object> paramsMap = new HashMap<String,Object>();
//            paramsMap.put("INVOKESERVICE_CODE","103");
//            paramsMap.put("INVOKECALLER_CODE","授权码");
//            paramsMap.put("TRUST_TICKET","34343433dddd");
//            String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
//            Map<String,Object> clientParam = new HashMap<String,Object>();
//            clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
//            String result = MztSample.sendPostParams(url, clientParam);
            return super.preHandle(request, response, handler);
        }

        AjaxResult ajaxResult = AjaxResult.error("实名身份认证失败，请确认是否登录正常");
        ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));
        return false;
    }
}
