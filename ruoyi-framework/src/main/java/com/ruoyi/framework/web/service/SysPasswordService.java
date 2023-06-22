package com.ruoyi.framework.web.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import org.passay.*;
import java.util.List;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@Component
public class SysPasswordService
{
    @Autowired
    private RedisCache redisCache;

    //密码最大错误次数
    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    //密码锁定时间
    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    //密码最小长度
    @Value(value = "${user.password.minLength}")
    private int minLength;

    //密码最大长度
    @Value(value = "${user.password.maxLength}")
    private int maxLength;

    //最少大写字符数
    @Value(value = "${user.password.minUppercaseLetters}")
    private int minUppercaseLetters;

    //最少小写字符数
    @Value(value = "${user.password.minLowercaseLetters}")
    private int minLowercaseLetters;

    //最少数字字符数
    @Value(value = "${user.password.minDigits}")
    private int minDigits;

    // 最少特殊字符数
    @Value(value = "${user.password.minSpecialChars}")
    private int minSpecialChars;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user)
    {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount, lockTime)));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.count", retryCount)));
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisCache.hasKey(getCacheKey(loginName)))
        {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }

    /**
     * 使用配置的密码规则校验密码是否合规
     * @param password 待验证密码
     */
    public void validatePasswordRule(String password)
    {
        List<Rule> passwordRules = new ArrayList<>();
        //密码规则校验
        passwordRules.add(new LengthRule(minLength, maxLength));//密码长度范围
        if (isPositiveInteger(minUppercaseLetters)) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.UpperCase, minUppercaseLetters));//包含的大写字母个数
        }
        if (isPositiveInteger(minLowercaseLetters)) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.LowerCase, minLowercaseLetters));//包含的小写字母个数
        }
        if (isPositiveInteger(minDigits)) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.Digit, minDigits));//包含的数字个数
        }
        if (isPositiveInteger(minSpecialChars)) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.Special, minSpecialChars));//包含的特殊字符个数
        }
        PasswordValidator validator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult result = validator.validate(passwordData);//校验密码是否满足规则要求
        if (!result.isValid()) {
            String message = this.getPasswordPolicyDesc();
            throw new ServiceException(message);
        }
    }

    /**
     * 生成密码规则描述
     * @return 密码规则描述
     */
    public String getPasswordPolicyDesc() {
        String lengthRule;
        String characterRule = "";
        //检验提示
        String passwordPolicyDesc;
        if(isPositiveInteger(minUppercaseLetters)){
            characterRule += StringUtils.format("{}个大写字母，",minUppercaseLetters);
        }
        if(isPositiveInteger(minLowercaseLetters)){
            characterRule += StringUtils.format("{}个小写字母，",minLowercaseLetters);
        }
        if(isPositiveInteger(minDigits)){
            characterRule += StringUtils.format("{}个数字，",minDigits);
        }
        if(isPositiveInteger(minSpecialChars)){
            characterRule += StringUtils.format("{}个特殊字符",minSpecialChars);
        }
        if(minLength == maxLength){
            lengthRule = StringUtils.format("密码长度必须是{}位数",minLength);
        }else {
            lengthRule = StringUtils.format("密码长度最少{}位，最多{}位",minLength,maxLength);
        }
        if(StringUtils.isNotEmpty(characterRule)){
            passwordPolicyDesc=lengthRule+"，至少包含："+characterRule;
        }else {
            passwordPolicyDesc=lengthRule;
        }
        if(passwordPolicyDesc.charAt(passwordPolicyDesc.length()-1)=='，'){
            StringBuilder stringBuilder = new StringBuilder(passwordPolicyDesc);
            stringBuilder.replace(passwordPolicyDesc.length() - 1, passwordPolicyDesc.length(), "。");
            passwordPolicyDesc=stringBuilder.toString();
        }
        return passwordPolicyDesc;
    }

    /**
     * 判断参数是否为非0数字
     * @param val 参数值
     * @return 正整数返回true,否则返回false
     */
    private boolean isPositiveInteger(Integer val) {
        return val != null && val > 0;
    }
}
