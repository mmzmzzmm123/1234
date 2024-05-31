package com.onethinker.activity.service.impl;

import com.onethinker.activity.service.IMailService;
import com.onethinker.activity.utils.MailUtil;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author yyq
 */
@Service
public class IMailServiceImpl implements IMailService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public void sendMailCode(String place, int code) {
        List<String> mail = new ArrayList<>();
        mail.add(place);
        String text = getCodeMail(code);

        Integer count = redisCache.getCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0));
        if (count == null || count < Integer.parseInt(sysConfigService.selectConfigByKey(SysConfigKeyEnum.CODE_MAIL_COUNT))) {
            mailUtil.sendMailMessage(mail, "您有一封来自扬万科技的回执！", text);
            if (count == null) {
                redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0), 1, 1, TimeUnit.DAYS);
            } else {
                redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0), count++, 1, TimeUnit.DAYS);
            }
        } else {
            throw new RuntimeException("验证码发送次数过多，请明天再试！");
        }
    }

    private String getCodeMail(int i) {
        String webName = "OneThinker";

        // 1. 网站名称 2. 邮件类型 3. 发件人 4. 发件内容 5. originalText 6. 网站名称
        return String.format(
                mailUtil.getMailText(),
                webName,
                String.format(MailUtil.IM_MAIL, webName),
                webName,
                String.format(sysConfigService.selectConfigByKey(SysConfigKeyEnum.CODE_MAIL_FORMAT), i),
                "",
                webName);
    }
}
