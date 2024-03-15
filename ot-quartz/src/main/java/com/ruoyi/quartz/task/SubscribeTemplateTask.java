package com.ruoyi.quartz.task;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.onethinker.onethinker.domain.SubscribeMsgCtrl;
import com.onethinker.onethinker.service.ISubscribeMsgCtrlService;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.onethinker.weatherinfo.service.IWeatherInfoService;
import com.onethinker.wechat.service.IMinWechatService;
import com.ruoyi.common.utils.DateUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yangyouqi
 * @date : 2024/3/15 11:46
 * 模版消息定时任务
 */
@Component("SubscribeTemplateTask")
@EnableScheduling
@Log4j2
public class SubscribeTemplateTask {

    @Resource
    private ISubscribeMsgCtrlService subscribeMsgCtrlService;

    @Resource
    private IWeatherInfoService weatherInfoService;

    @Resource
    private IPlatformUserDetailService platformUserDetailService;

    @Resource
    private IMinWechatService wechatService;

    /**
     * 发送天气预报订阅消息
     */
    public void sendSubscribeTemplateByWeatherInfo() {

        // 发送模版消息,这里后续新增一张用户订阅表，然后获取可发送的用户进行处理
        List<PlatformUserDetail> platformUserDetails =  platformUserDetailService.queryAllUserByType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        if (platformUserDetails.isEmpty()) {
            return;
        }
        // 获取当前城市信息（可以通过用户设定，目前先暂时用广州的值）
        String cityKey = "440100";
        // 返回天气结果
        String content = weatherInfoService.selectNewWeatherInfoByCity(cityKey);
        String templateId = "QVISoOJAxIH-eb3Ky-xFbmH5WcC5I1kcopworAsBfm4";
        SubscribeMsgCtrl subscribeMsgCtrl = subscribeMsgCtrlService.findEntryByTemplateId(templateId);
        if (ObjectUtils.isEmpty(subscribeMsgCtrl)) {
            return;
        }
        for (PlatformUserDetail platformUserDetail : platformUserDetails) {
            WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();
            subscribeMessage.setToUser(platformUserDetail.getDataId());
            subscribeMessage.setTemplateId(templateId);
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
                    msgData.setValue(dataFrom.getString(key).replace("{{content}}",content));
                }
                data.add(msgData);
            }
            subscribeMessage.setData(data);
            subscribeMessage.setPage(subscribeMsgCtrl.getPage());
            subscribeMessage.setMiniprogramState(subscribeMsgCtrl.getMiniprogramState());
            try {
                wechatService.sendSubscribeMsg(subscribeMessage);
            } catch (WxErrorException e) {
                log.error(e.getError());
            }
        }
    }

}
