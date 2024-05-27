package com.onethinker.web.controller.onethinker;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.onethinker.domain.SubscribeMsgCtrl;
import com.onethinker.onethinker.service.ISubscribeMsgCtrlService;
import com.onethinker.wechat.service.IMinWechatService;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.utils.DateUtils;
import io.jsonwebtoken.lang.Assert;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 微信小程序能力接入
 * @author : yangyouqi
 * @date : 2024/1/15 11:26
 *
 */
@RestController
@RequestMapping("/onethinker")
public class MinWechatController extends BaseController {
    @Autowired
    private IMinWechatService wechatService;
    @Autowired
    private ISubscribeMsgCtrlService subscribeMsgCtrlService;

    /**
     * 测试发布订阅消息
     */
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/sendSubscribeMsgTest")
    public AjaxResult myBatisTest(@RequestParam String templateId) {
        SubscribeMsgCtrl subscribeMsgCtrl = subscribeMsgCtrlService.findEntryByTemplateId(templateId);
        Assert.isTrue(ObjectUtils.isNotEmpty(subscribeMsgCtrl), "模版信息不存在");
        WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();
        subscribeMessage.setToUser("oA-3y5HRX4ENAF9yJhTdua7lxH9U");
        subscribeMessage.setTemplateId(subscribeMsgCtrl.getTemplateId());
        JSONObject dataFrom = JSONObject.parse(subscribeMsgCtrl.getData());
        List<WxMaSubscribeMessage.MsgData> data = Lists.newArrayList();
        for (String key : dataFrom.keySet()) {
            WxMaSubscribeMessage.MsgData msgData = new WxMaSubscribeMessage.MsgData();
            // 特殊规则处理
            if (key.contains("time")) {
                // 日期时间
                msgData.setName(key);
                msgData.setValue(DateUtils.getTime());
            } else {
                msgData.setName(key);
                msgData.setValue(dataFrom.getString(key));
            }
            data.add(msgData);
        }
        subscribeMessage.setData(data);
        subscribeMessage.setPage(subscribeMsgCtrl.getPage());
        subscribeMessage.setMiniprogramState(subscribeMsgCtrl.getMiniprogramState());
        try {
            wechatService.sendSubscribeMsg(subscribeMessage);
        } catch (WxErrorException e) {
            System.out.println(e.getError());
            return error(e.getMessage());
        }
        return success();
    }
}
