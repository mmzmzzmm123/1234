package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;

import java.io.Serializable;

/**
 * 密码强度配置
 * @author boger
 * @since 2022-05-14
 */
public class PasswordConfig extends BaseEntity
{
    private static final long serialVersionUID = 234L;
    private Integer id;
    /**
     * 最多尝试次数，默认5次
     */
    private Integer maxFailedLoginAttempts = 5;
    /**
     * 锁定后通知邮箱
     */
    private String userLockoutNotificationEmail;



    /**
     * 锁定后解锁时间间隔，默认10分钟
     */
    private Integer userLockPeriod= 10;

    /**
     * 密码过期时间,默认90天
     */
    private Integer passwordExpirationPeriodDays = 90;

    /**
     * 密码最小长度,默认8个字符
     */
    private Integer minimumLength = 8;

    /**
     * 密码最大长度，默认20个字符
     */
    private Integer maximumLength = 20;


    /**
     * 最少大写字符数
     */
    private Integer minimumUppercaseLetters = 1;
    /**
     * 最少小写字符数
     */
    private Integer minimumLowercaseLetters = 1;
    /**
     * 最少数字字符数
     */
    private Integer minimumDigits = 1;
    /**
     * 最少特殊字符数
     */
    private Integer minimumSpecialCharacters = 1;


    /**
     * 检验提示
     */
    private String passwordPolicyDesc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxFailedLoginAttempts() {
        return maxFailedLoginAttempts;
    }

    public void setMaxFailedLoginAttempts(Integer maxFailedLoginAttempts) {
        this.maxFailedLoginAttempts = maxFailedLoginAttempts;
    }

    public String getUserLockoutNotificationEmail() {
        return userLockoutNotificationEmail;
    }

    public void setUserLockoutNotificationEmail(String userLockoutNotificationEmail) {
        this.userLockoutNotificationEmail = userLockoutNotificationEmail;
    }

    public Integer getUserLockPeriod() {
        return userLockPeriod;
    }

    public void setUserLockPeriod(Integer userLockPeriod) {
        this.userLockPeriod = userLockPeriod;
    }

    public Integer getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(Integer minimumLength) {
        this.minimumLength = minimumLength;
    }

    public Integer getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Integer getMinimumUppercaseLetters() {
        return minimumUppercaseLetters;
    }

    public void setMinimumUppercaseLetters(Integer minimumUppercaseLetters) {
        this.minimumUppercaseLetters = minimumUppercaseLetters;
    }

    public Integer getMinimumLowercaseLetters() {
        return minimumLowercaseLetters;
    }

    public void setMinimumLowercaseLetters(Integer minimumLowercaseLetters) {
        this.minimumLowercaseLetters = minimumLowercaseLetters;
    }

    public Integer getMinimumDigits() {
        return minimumDigits;
    }

    public void setMinimumDigits(Integer minimumDigits) {
        this.minimumDigits = minimumDigits;
    }

    public Integer getMinimumSpecialCharacters() {
        return minimumSpecialCharacters;
    }

    public void setMinimumSpecialCharacters(Integer minimumSpecialCharacters) {
        this.minimumSpecialCharacters = minimumSpecialCharacters;
    }

    public Integer getPasswordExpirationPeriodDays() {
        return passwordExpirationPeriodDays;
    }

    public void setPasswordExpirationPeriodDays(Integer passwordExpirationPeriodDays) {
        this.passwordExpirationPeriodDays = passwordExpirationPeriodDays;
    }

    public String getPasswordPolicyDesc() {
        String lengthRule;
        String characterRule = "";
        if(isPositiveInteger(this.getMinimumUppercaseLetters())){
            characterRule += StringUtils.format("{}个大写字母，",this.getMinimumUppercaseLetters());
        }
        if(isPositiveInteger(this.getMinimumLowercaseLetters())){
            characterRule += StringUtils.format("{}个小写字母，",this.getMinimumLowercaseLetters());
        }
        if(isPositiveInteger(this.getMinimumDigits())){
            characterRule += StringUtils.format("{}个数字，",this.getMinimumDigits());
        }
        if(isPositiveInteger(this.getMinimumSpecialCharacters())){
            characterRule += StringUtils.format("{}个特殊字符",this.getMinimumSpecialCharacters());
        }
        if(this.getMinimumLength().equals(this.getMaximumLength())){
            lengthRule = StringUtils.format("密码长度必须是{}位数",this.getMinimumLength());
        }else {
            lengthRule = StringUtils.format("密码长度最少{}位，最多{}位",this.getMinimumLength(),this.getMaximumLength());
        }
        if(StringUtils.isNotEmpty(characterRule)){
            this.setPasswordPolicyDesc(lengthRule+"，至少包含："+characterRule);
        }else {
            this.setPasswordPolicyDesc(lengthRule);
        }
        return this.passwordPolicyDesc;
    }

    public void setPasswordPolicyDesc(String passwordPolicyDesc) {
        this.passwordPolicyDesc = passwordPolicyDesc;
    }

    private boolean isPositiveInteger(Integer val) {
        return val != null && val > 0;
    }
}
