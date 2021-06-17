package com.stdiet.web.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.domain.SysWxAdLog;
import com.stdiet.custom.service.*;
import com.stdiet.custom.utils.CookieUtils;
import com.stdiet.custom.utils.HttpRequestUtils;
import com.stdiet.system.service.ISysDictTypeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wap")
public class SysWapController extends BaseController {
    @Autowired
    ISysWapServices iSysWapServices;

    @Autowired
    ISysRecipesService iSysRecipesService;

    @Autowired
    ISysDictTypeService iSysDictTypeService;

    @Autowired
    ISysOrderPauseService iSysOrderPauseService;

    @Autowired
    ISysWxSaleAccountService iSysWxSaleAccountService;

    @Autowired
    ISysSmsConfirmServie iSysSmsConfirmServie;


    /**
     * 客户食谱详情
     *
     * @param outId
     * @return
     */
    @GetMapping(value = "/recipes/plans/{outId}")
    public AjaxResult detail(@PathVariable String outId) {
        return AjaxResult.success(iSysWapServices.getRecipesPlanListInfo(outId));
    }

    @GetMapping(value = "/recipes/plan/pause/{outId}")
    public AjaxResult planPauses(@PathVariable String outId) {
        SysOrderPause sysOrderPause = new SysOrderPause();
        sysOrderPause.setOutId(outId);
        List<SysOrderPause> list = iSysOrderPauseService.selectSysOrderPauseList(sysOrderPause);
        for (SysOrderPause pause : list) {
            pause.setCusId(null);
        }
        return AjaxResult.success(list);
    }

    /**
     * 获取用户信息
     *
     * @param outId
     * @return
     */
    @GetMapping(value = "/healthyInfo/{outId}")
    public AjaxResult healthy(@PathVariable String outId) {
        return AjaxResult.success(iSysWapServices.getHealthyDataByOutId(outId));
    }

    /**
     * 获取某天食谱菜品
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/recipes/menu/{id}")
    public AjaxResult dayilyMenu(@PathVariable Long id) {
        return AjaxResult.success(iSysRecipesService.selectDishesByMenuId(id));
    }

    /**
     * 系统字典
     *
     * @param dictType
     * @return
     */
    @GetMapping(value = "/dict/{dictType}")
    public AjaxResult sysDict(@PathVariable String dictType) {
        return AjaxResult.success(iSysDictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 获取完整食谱
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/recipes/{id}")
    public AjaxResult recipesDetail(@PathVariable Long id) {
        return AjaxResult.success(iSysRecipesService.selectSysRecipesByRecipesId(id));
    }

//    @GetMapping(value = "/qrcode")
//    public void qrcodeRediredt(String group, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.sendRedirect("https://weibo.com/u/1913360251");
//    }

    @GetMapping(value = "/wxid")
    public AjaxResult getWxId(@RequestParam String cid, @RequestParam String uid, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        SysWxAdLog sysWxAdLog = new SysWxAdLog();
        sysWxAdLog.setUserId(uid);
        sysWxAdLog.setIp(HttpRequestUtils.getIpAddr(request));
        sysWxAdLog.setChannelId(cid);
        sysWxAdLog.setUserAgent(userAgent);
        sysWxAdLog.setType(0);
        sysWxAdLog.setDate(DateUtils.getNowDate());

        Map<String, String> result = new HashMap<>();
        String wxId = iSysWxSaleAccountService.getWxAdId(sysWxAdLog);
        result.put("id", wxId);

        return AjaxResult.success(result);
    }

    @GetMapping(value = "/wxid/st")
    public AjaxResult logActived(@RequestParam String cid, @RequestParam String uid, @RequestParam String wxid, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {
        SysWxAdLog sysWxAdLog = new SysWxAdLog();
        sysWxAdLog.setUserId(uid);
        sysWxAdLog.setIp(HttpRequestUtils.getIpAddr(request));
        sysWxAdLog.setWxId(wxid);
        sysWxAdLog.setChannelId(cid);
        sysWxAdLog.setUserAgent(userAgent);
        sysWxAdLog.setType(1);
        sysWxAdLog.setDate(DateUtils.getNowDate());

        return toAjax(iSysWxSaleAccountService.logWxAd(sysWxAdLog));
    }

    @GetMapping(value = "/getCookie")
    public AjaxResult getCookie(@RequestParam String phone, HttpServletResponse response) {
        String tokenStr = phone + "_" + new Date().getTime() + "_" + RandomStringUtils.random(8);
        Cookie cookie = new Cookie("token", AesUtils.encrypt(tokenStr));
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return AjaxResult.success();
    }

    @GetMapping(value = "/checkCookie")
    public AjaxResult checkCookie(HttpServletRequest request) {
        JSONObject resultObj = CookieUtils.checkCookieValida(request, "token");
        if (resultObj.getInteger("code") == 200) {
            return AjaxResult.success(resultObj.getString("msg"));
        } else {
            return AjaxResult.error(resultObj.getInteger("code"), resultObj.getString("msg"));
        }
    }

    @GetMapping(value = "/getCode")
    public AjaxResult getCode(@RequestParam String phone) {

        if (!StringUtils.isMobileNO(phone)) {
            return AjaxResult.error(50001, "非法手机号");
        }

        int code = iSysSmsConfirmServie.sendSmsCode(phone);
        if (code == 1) {
            return AjaxResult.error(50001, "非法手机号");
        } else if (code == 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }

    }

    @GetMapping(value = "/checkCode")
    public AjaxResult checkCode(@RequestParam String phone, @RequestParam String code, HttpServletResponse response, HttpServletRequest request) {

        if (StringUtils.isEmpty(code)) {
            return AjaxResult.error(50002, "验证码不能为空");
        }


        int checkCode = iSysSmsConfirmServie.checkSmsCode(phone, code);
        if (checkCode == 0) {
            String tokenStr = phone + "_" + new Date().getTime() + "_" + RandomStringUtils.randomAlphanumeric(8);
            Cookie cookie = new Cookie("token", AesUtils.encrypt(tokenStr));
            cookie.setMaxAge(24 * 60 * 60);
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
            cookie.setDomain(request.getServerName());
            cookie.setPath("/");
            response.addCookie(cookie);
            return new AjaxResult(20000, "登录成功");
        } else if (checkCode == 1) {
            return AjaxResult.error(50003, "验证码失效");
        } else if (checkCode == 2) {
            return AjaxResult.error(50004, "验证码错误");
        } else {
            return AjaxResult.error();
        }
    }

}
