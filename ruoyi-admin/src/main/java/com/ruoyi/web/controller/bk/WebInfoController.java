package com.ruoyi.web.controller.bk;

import com.alibaba.fastjson2.JSONObject;
import com.onethinker.bk.domain.WebInfo;
import com.onethinker.bk.service.ISortService;
import com.onethinker.bk.service.IWebInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站信息Controller
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@RestController
@RequestMapping("/bk/webInfo")
public class WebInfoController extends BaseController {
    @Autowired
    private IWebInfoService webInfoService;
    @Autowired
    private ISortService sortService;

    /**
     * 查询网站信息
     */
    @GetMapping("/getWebInfo")
    public AjaxResult getWebInfo() {
        WebInfo webInfo = webInfoService.getWebInfo();
        webInfo.setRandomAvatar(null);
        webInfo.setRandomCover(null);
        webInfo.setRandomName(null);
        webInfo.setWaifuJson(null);
        return AjaxResult.success(webInfo);
    }

    /**
     * 获取分类标签信息
     */
    @GetMapping(value = "/getSortInfo")
    public AjaxResult getSortInfo() {
        return AjaxResult.success(sortService.getSortInfo());
    }


    /**
     * 获取看板娘消息
     */
    @GetMapping("/getWaifuJson")
    public AjaxResult getWaifuJson() {
        return AjaxResult.success(JSONObject.parseObject(webInfoService.getWebInfo().getWaifuJson()));
    }
}
