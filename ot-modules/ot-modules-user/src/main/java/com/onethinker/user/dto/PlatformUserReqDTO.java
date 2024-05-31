package com.onethinker.user.dto;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.util.StringUtil;
import com.onethinker.common.constant.SystemConst;
import com.onethinker.common.enums.CodeTypeEnum;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.common.utils.SecurityUtils;
import lombok.Data;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
@Data
public class PlatformUserReqDTO {

    /**
     * 平台用户来源 1：微信平台登录 2：账户密码登录
     */
    private PlatformUserTypeEnum sourceType;

    private String password;

    private String code;
    private String openId;

    private String phone;

    private String avatarUrl;

    private String nickName;

    private String email;

    private String serviceType;

    private String dataId;

    private String uuid;

    /**
     * 参数有效性校验
     */
    public void existsParams() {
        String regex = "\\d{11}";
        Assert.isTrue(StringUtil.isNotEmpty(dataId),"账号不能为空");
        Assert.isTrue(!dataId.matches(regex), "账号不能为11位数字");
        Assert.isTrue(!dataId.contains("@"), "账号不能包含@");
        //密码规则：
        //1.密码长度必须在8到20个字符之间。
        //2.必须包含至少一个大写字母。
        //3.必须包含至少一个小写字母。
        //4.必须包含至少一个数字。
        String upperCasePattern = ".*[A-Z].*";
        String lowerCasePattern = ".*[a-z].*";
        String digitPattern = ".*[0-9].*";
        Assert.isTrue(StringUtil.isNotEmpty(password),"密码不能为空");
        Assert.isTrue(password.length() >= 8 && password.length() <= 20,"长度在8到20个字符之间");
        Assert.isTrue(password.matches(upperCasePattern), "包含至少一个大写字母");
        Assert.isTrue(password.matches(lowerCasePattern), "必须包含至少一个小写字母");
        Assert.isTrue(password.matches(digitPattern), "必须包含至少一个数字");
    }

    /**
     * 校验绑定手机号相关参数信息
     *
     */
    public void existsParamsByBindPhoneOrEmail(CodeTypeEnum codeTypeEnum) {
        Assert.isTrue(StringUtil.isNotEmpty(code),"验证码不能为空");
        Assert.isTrue(StringUtil.isNotEmpty(uuid),"验证码信息不能为空");
        Assert.isTrue(Objects.nonNull(codeTypeEnum),"更新内容不能为空");
        if (CodeTypeEnum.PHONE.equals(codeTypeEnum) && StringUtil.isEmpty(phone)) {
            throw new RuntimeException("绑定信息不存在");
        } else  if (CodeTypeEnum.MAIL.equals(codeTypeEnum) && StringUtil.isEmpty(email)) {
            throw new RuntimeException("绑定信息不存在");
        }

        String dataId = SecurityUtils.getLoginUser().getDataId();
        if (Objects.isNull(this.dataId)) {
            this.dataId = dataId;
        } else if (!Objects.equals(dataId,this.dataId)) {
            throw new RuntimeException("用户信息非法，请重新登录");
        }
    }

    public String getPassword() {
        return SecureUtil.aes(SystemConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(password);
//        return password;
    }


}
