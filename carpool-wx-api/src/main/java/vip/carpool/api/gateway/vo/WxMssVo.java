package vip.carpool.api.gateway.vo;

import lombok.Data;

import java.util.Map;

/**
 * @Author: huangguojie
 * @Date: 2020/12/13 11:49
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//订阅消息模版id
    private String page = "pages/index/index";//默认跳到小程序首页
    private Map<String, TemplateData> data;//推送文字
}
