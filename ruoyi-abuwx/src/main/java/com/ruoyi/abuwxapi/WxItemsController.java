package com.ruoyi.abuwxapi;

import com.ruoyi.abuwx.domain.AbucoderItems;
import com.ruoyi.abuwx.service.IAbucoderItemsService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author：AbuCoder QQ:932696181
 * 微信小程序获取推荐小程序接口
 * Gitee:https://gitee.com/rahman
 */
@RestController
@RequestMapping("/wxapi")
public class WxItemsController {

    @Autowired
    IAbucoderItemsService iAbucoderItemsService;
    @GetMapping("/loadItems")
    public AjaxResult loadItems(AbucoderItems abucoderItems){
        List<AbucoderItems> itemslist = iAbucoderItemsService.selectAbucoderItemsList(abucoderItems);
        return AjaxResult.success(itemslist);
    }
}
