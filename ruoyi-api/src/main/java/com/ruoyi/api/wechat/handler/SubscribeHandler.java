package com.ruoyi.api.wechat.handler;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.enums.SysYesNoEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.weixin.config.WxConfig;
import com.ruoyi.common.weixin.service.WechatMsgReplyService;
import com.ruoyi.user.domain.UserOfficialAccount;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LAM
 * @date 2023/10/15 23:00
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeHandler implements WxMpMessageHandler {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final WechatMsgReplyService wechatMsgReplyService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager){
        log.info("微信公众号关注事件：开始，参数：{}，{}", wxMessage, context);
        // 判断列表中是否存在该用户
        UserOfficialAccount select = new UserOfficialAccount();
        select.setOpenId(wxMessage.getFromUser());
        List<UserOfficialAccount> userOfficialAccounts = userOfficialAccountMapper.selectUserOfficialAccountList(select);
        if (ObjectUtil.isNotEmpty(userOfficialAccounts)){
            log.info("微信公众号关注事件：描述，该用户曾经关注过公众号，现直接修改订阅状态即可");
            userOfficialAccountMapper.updateIfSubscribeByOpenId(SysYesNoEnums.YES.getCode(), wxMessage.getFromUser());
        }else{
            log.info("微信公众号关注事件：描述，新用户，直接保存该用户信息");
            UserOfficialAccount insert = new UserOfficialAccount();
            try {
                WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
                BeanUtils.copyBeanProp(insert, wxMpUser);
                if (ObjectUtil.isNotNull(wxMpUser.getGroupId())){
                    insert.setGroupId(wxMpUser.getGroupId().longValue());
                }
                if (ObjectUtil.isNotNull(wxMpUser.getSubscribeTime())){
                    insert.setSubscribeTime(new Date(wxMpUser.getSubscribeTime()));
                }
            }catch (Exception e){
                log.warn("微信公众号关注事件：异常，获取用户基本信息失败");
                throw new ServiceException(e.getMessage());
            }
            userOfficialAccountMapper.insertUserOfficialAccount(insert);
        }
        wechatMsgReplyService.replyText(wxMessage.getFromUser(), "\uD83C\uDF33 欢迎来到[ 热爱树洞 ] \uD83C\uDF33\n" +
                "\n" +
                "陪伴，是生活中最真挚的告白。在这片温馨的树洞社交之中，我们倾心呵护，提供多重关怀服务：\n" +
                "\n" +
                "\uD83D\uDDE8️ 真人陪聊，与您分享美好时光\n" +
                "\uD83C\uDF89 整蛊朋友，共创欢笑回忆\n" +
                "\uD83D\uDC94 情感咨询，诉说内心的故事\n" +
                "⏰ 叫醒哄睡，温柔的晨晚夜\n" +
                "\uD83D\uDE41 失恋安慰，伴您走过难关\n" +
                "❓ 树洞解答，寻找生活的答案\n" +
                "\uD83D\uDCDA 学习监督，一起进步成长\n" +
                "\uD83C\uDF81 盲盒抽取，惊喜无限\n" +
                "\uD83D\uDE4B\u200D♂️\uD83D\uDE4B\u200D♀️ 小哥哥小姐姐在线营业，等候您的选择\n" +
                "\n" +
                "让我们一起探索树洞的奇妙之处，感受爱与温情。点击关注，享受独一无二的陪伴之旅！✨\uD83C\uDF3F\"");
        log.info("微信公众号关注事件：完成");
        return null;
    }
}
