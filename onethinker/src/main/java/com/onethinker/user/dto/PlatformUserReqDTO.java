package com.onethinker.user.dto;

import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
@Data
public class PlatformUserReqDTO {

    /**
     * 微信平台
     */
    public static final String SOURCE_TYPE_WX = "1";
    /**
     * 账户密码登录
     */
    public static final String SOURCE_TYPE_ACCOUNT = "2";

    /**
     * 手机验证码
     */
    public static final String PHONE_CODE = "1";

    /**
     * 邮箱验证码
     */
    public static final String EMAIL_CODE = "2";

    /**
     * 平台用户来源 1：微信平台登录 2：账户密码登录
     */
    private String sourceType;

    private String userName;
    private String password;

    private String code;
    private String openId;
    private String phone;

    private String avatarUrl;

    private String nickName;

    private String email;

    private String serviceType;

    private String dataId;

    /**
     * 参数有效性校验
     */
    public void existsParams() {
        String regex = "\\d{11}";
        Assert.isTrue(!userName.matches(regex),"用户名不能为11位数字！");
        Assert.isTrue(!userName.contains("@"),"用户名不能包含@！");
        if (StringUtils.hasText(phone) && StringUtils.hasText(email)) {
            throw new RuntimeException("手机号与邮箱只能选择其中一个！");
        }
        if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(email)) {
            throw new RuntimeException("手机号与邮箱不能同时为空");
        }
    }
}
