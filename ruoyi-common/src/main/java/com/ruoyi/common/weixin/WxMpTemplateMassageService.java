package com.ruoyi.common.weixin;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/10/31 16:27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMpTemplateMassageService {

    private final WxMpService wxOfficialAccountService;

    /**
     * 发送公众号模板消息
     */
    public void wxMpSendTemplateMessage(WxMpTemplateMessage templateMessage) {
        log.info("发送公众号模板消息：开始，参数：{}", JSON.toJSONString(templateMessage));
        try {
            String result = wxOfficialAccountService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("发送公众号模板消息：完成，返回数据：{}", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("发送公众号模板消息：异常，异常消息：{}", e.getMessage());
        }
        log.info("发送公众号模板消息：完成");
    }

    /**
     * map数据转对应的消息模板数据集合
     *
     * @param dataMap 数据map集合
     * @return 结果
     */
    public static List<WxMpTemplateData> MapToData(Map<String, String> dataMap) {
        List<WxMpTemplateData> voList = new ArrayList<>();
        dataMap.forEach((key, val) -> {
            WxMpTemplateData temp = new WxMpTemplateData();
            temp.setName(key);
            temp.setValue(val);
            voList.add(temp);
        });
        return voList;
    }


}
