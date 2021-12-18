package vip.carpool.api.gateway.controller.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vip.carpool.api.gateway.common.api.ApiCode;
import vip.carpool.api.gateway.common.api.ApiResult;
import vip.carpool.api.gateway.common.exception.BadRequestException;
import vip.carpool.api.gateway.common.utils.ThrowableUtil;
import vip.carpool.api.gateway.dto.SubscribeMessageDTO;
import vip.carpool.api.gateway.vo.TemplateData;
import vip.carpool.api.gateway.vo.TemplateMsgVO;
import vip.carpool.api.gateway.vo.WxMsg;
import vip.carpool.api.gateway.vo.WxMssVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
@Api(value = "微信小程序回复用户消息模块", tags = "微信小程序回复用户消息模块", description = "微信小程序回复用户消息模块")
public class WeChatMessageController {


    @Resource
    private WxMaService wxMiniService;


    @PostMapping("/sendMessage")
    public void sendMessage(String openId, String message) throws WxErrorException {
        WxMaKefuMessage wxMaKefuMessage = new WxMaKefuMessage();
        wxMaKefuMessage.setMsgType("text");
        wxMaKefuMessage.setToUser(openId);
        WxMaKefuMessage.KfText text = new WxMaKefuMessage.KfText(message);
        wxMaKefuMessage.setText(text);
        wxMiniService.getMsgService().sendKefuMsg(wxMaKefuMessage);
        log.info("成功回复消息:" + message);
    }

    /*public static void main(String[] args) {
        List<WxMsg> list = new ArrayList<>();
        WxMsg wxMsg = new WxMsg();
        wxMsg.setName("keyword1");
        wxMsg.setValue("易鑫车贷");
        list.add(wxMsg);
        wxMsg = new WxMsg();
        wxMsg.setName("keyword2");
        wxMsg.setValue("尊敬的客户：您在农业银行的贷款申请已受理，具体贷款结果请咨询合作机构！");
        list.add(wxMsg);
        String msg = JSON.toJSONString(list);
        JSONObject object = new JSONObject();
        object.put("msgs", msg);
        System.out.println(object.toJSONString());

    }*/

    @PostMapping("/sendSubscribeMessage")
    public ApiResult sendSubscribeMessage(@Validated @RequestBody TemplateMsgVO templateMsgVO) {
        try {
            WxMssVo subscribeMessage = new WxMssVo();
            //跳转小程序页面路径
            subscribeMessage.setPage(templateMsgVO.getUrl());
            //模板消息id
            subscribeMessage.setTemplate_id(templateMsgVO.getTemplateId());
            //给谁推送 用户的openid （可以调用根据code换openid接口)
            subscribeMessage.setTouser(templateMsgVO.getOpenId());
            //==========================================创建一个参数集合========================================================
            Gson gson = new Gson();
            String msgs = templateMsgVO.getMsgs();
            Map<String, TemplateData> wxMaTemplateDataMap = new HashMap<>();
            List<WxMsg> msgList = gson.fromJson(msgs, new TypeToken<List<WxMsg>>() {
            }.getType());
            if (msgs != null && msgList.size() > 0) {
                for (WxMsg wxMsg : msgList) {
                    wxMaTemplateDataMap.put(wxMsg.getName(), new TemplateData(wxMsg.getValue()));
                }
            }
            subscribeMessage.setData(wxMaTemplateDataMap);
            String str = JSON.toJSONString(subscribeMessage);
            log.info("给客户推送消息：{}", str);
            //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
            String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + wxMiniService.getAccessToken();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, subscribeMessage, String.class);
            SubscribeMessageDTO subscribeMessageDTO = JSON.parseObject(responseEntity.getBody(), SubscribeMessageDTO.class);
            if(null != subscribeMessageDTO.getErrcode() && subscribeMessageDTO.getErrcode().equals(0)){
                return ApiResult.ok(responseEntity.getBody());
            }
            return ApiResult.fail(ApiCode.FAIL, responseEntity.getBody());
        } catch (WxErrorException e) {
            log.error("消息推送失败：{}", ThrowableUtil.getStackTrace(e));
            throw new BadRequestException("消息推送失败：" + e.getMessage());
        }
    }

}
