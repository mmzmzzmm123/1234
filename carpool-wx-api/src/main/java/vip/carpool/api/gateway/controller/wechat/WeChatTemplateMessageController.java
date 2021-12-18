package vip.carpool.api.gateway.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.carpool.api.gateway.common.api.ApiResult;
import vip.carpool.api.gateway.common.exception.BadRequestException;
import vip.carpool.api.gateway.common.utils.ThrowableUtil;
import vip.carpool.api.gateway.vo.TemplateMsgVO;
import vip.carpool.api.gateway.vo.WxMsg;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: huangguojie
 * @Date: 2020/7/19 14:54
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
@Api(value = "推送模版消息", tags = "推送模版消息", description = "推送模版消息")
public class WeChatTemplateMessageController {

    @Resource
    private WxMpService wxService;

    /**
     * 推送模版消息
     *
     * @param templateMsgVO TemplateMsgVO
     * @return ApiResult
     */
    @PostMapping(value = "/sendTemplateMsg")
    @ApiOperation(value = "推送模版消息", notes = "推送模版消息")
    public ApiResult sendTemplateMsg(@Validated @RequestBody TemplateMsgVO templateMsgVO) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(templateMsgVO.getOpenId())
                .templateId(templateMsgVO.getTemplateId())
                .url(templateMsgVO.getUrl())
                .build();
        try {
            Gson gson = new Gson();
            String msgs = templateMsgVO.getMsgs();
            List<WxMsg> msgList = gson.fromJson(msgs, new TypeToken<List<WxMsg>>(){}.getType());
            if(msgs != null && msgList.size() > 0) {
                for(WxMsg wxMsg: msgList) {
                    WxMpTemplateData templateData = new WxMpTemplateData(wxMsg.getName(), wxMsg.getValue(), wxMsg.getColor());
                    templateMessage.addData(templateData);
                }
            }
            log.info("给客户推送消息：{}", templateMessage.toJson());
            String msgId = this.wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("给客户推送消息结果：{}", msgId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msgId", msgId);
            return ApiResult.ok(jsonObject);
        } catch (WxErrorException e) {
            log.error("消息推送失败：{}", ThrowableUtil.getStackTrace(e));
            throw new BadRequestException("消息推送失败：" + e.getMessage());
        }
    }
}
