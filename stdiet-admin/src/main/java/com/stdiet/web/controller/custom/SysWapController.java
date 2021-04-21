package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.domain.SysWxAdLog;
import com.stdiet.custom.service.ISysOrderPauseService;
import com.stdiet.custom.service.ISysRecipesService;
import com.stdiet.custom.service.ISysWapServices;
import com.stdiet.custom.service.ISysWxSaleAccountService;
import com.stdiet.custom.utils.HttpRequestUtils;
import com.stdiet.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

}
