package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.PasswordConfig;
import com.ruoyi.system.mapper.PasswordConfigMapper;
import com.ruoyi.system.service.ISysPasswordService;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 密码强度设置 服务类
 * </p>
 *
 * @author boger
 * @since 2022-05-15
 */

@Service
public class SysPasswordServiceImpl implements ISysPasswordService {

    private PasswordConfig passwordConfig;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordConfigMapper passwordConfigMapper;
    /**
     * 自动装配时，初始化密码强度设置参数到缓存
     */
    @PostConstruct
    public void init()
    {
        resetConfigCache();
    }

    /**
     * 设置passwordConfig
     * @param passwordConfig
     */
    @Override
    public void setPasswordConfig(PasswordConfig passwordConfig)
    {
        this.passwordConfig = passwordConfig;
    }
    public void validatePasswordRule(String password){
        validatePasswordRule(password, passwordConfig);
    }

    /**
     * 根据指定的密码强度策略验证密码是否满足策略要求，不满足策略要求抛出异常
     * @param password 待验证密码
     * @param passwordConfig 密码强度配置
     */
    public void validatePasswordRule(String password, PasswordConfig passwordConfig){

        List<Rule> passwordRules = new ArrayList<>();
        //密码组成规则校验
        passwordRules.add(new LengthRule(passwordConfig.getMinimumLength(), passwordConfig.getMaximumLength()));//密码长度范围
        if (isPositiveInteger(passwordConfig.getMinimumUppercaseLetters())) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.UpperCase, passwordConfig.getMinimumUppercaseLetters()));//包含的大写字母个数
        }
        if (isPositiveInteger(passwordConfig.getMinimumLowercaseLetters())) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.LowerCase, passwordConfig.getMinimumLowercaseLetters()));//包含的小写字母个数
        }
        if (isPositiveInteger(passwordConfig.getMinimumDigits())) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.Digit, passwordConfig.getMinimumDigits()));//包含的数字个数
        }
        if (isPositiveInteger(passwordConfig.getMinimumSpecialCharacters())) {
            passwordRules.add(new CharacterRule(EnglishCharacterData.Special, passwordConfig.getMinimumSpecialCharacters()));//包含的特殊字符个数
        }
        PasswordValidator validator = new PasswordValidator(passwordRules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult result = validator.validate(passwordData);//校验密码组成规则
        if (!result.isValid()) {
            String message = passwordConfig.getPasswordPolicyDesc();
            throw new ServiceException(message);
        }
    }

    /**
     * 获取密码强度配置
     * @return 密码策略配置对象
     */
    @Override
    public PasswordConfig selectConfig() {
        PasswordConfig config;
        config = redisCache.getCacheObject("sys:password");
        if (config!=null)
        {
            return config;
        }
        PasswordConfig passwordConfig = passwordConfigMapper.selectPasswordConfigById(1L);
        if (passwordConfig!=null)
        {
            this.passwordConfig = passwordConfig;
            redisCache.setCacheObject("sys:password", passwordConfig);
        }
        return passwordConfig;
    }

    /**
     * 保存密码强度配置策略
     * @param passwordConfig 密码强度配置信息
     * @return 保存结果
     */
    @Override
    public int saveConfig(PasswordConfig passwordConfig) {
        passwordConfig.setUpdateTime(DateUtils.getNowDate());
        int result= passwordConfigMapper.updatePasswordConfig(passwordConfig);
        if(result>0){
            this.passwordConfig = passwordConfig;
            redisCache.setCacheObject("sys:password", passwordConfig);
        }
        return result;
    }

    /**
     * 将保存的密码强度策略保存到缓存中
     */
    @Override
    public void CachePasswordConfig()
    {
        PasswordConfig passwordConfig = passwordConfigMapper.selectPasswordConfigById(1L);
        if (passwordConfig!=null)
        {
            this.passwordConfig = passwordConfig;
            redisCache.setCacheObject("sys:password", passwordConfig);
        }

    }

    /**
     * 清理密码强度策略缓存
     */
    @Override
    public void clearConfigCache() {
        redisCache.deleteObject("sys:password");
    }

    /**
     * 重置密码策略缓存内容
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        CachePasswordConfig();
    }


    /**
     * 判断是否为正整数
     * @param val 待判定整数
     * @return 正整数返回true,否则返回false
     */
    private boolean isPositiveInteger(Integer val) {
        return val != null && val > 0;
    }

}
