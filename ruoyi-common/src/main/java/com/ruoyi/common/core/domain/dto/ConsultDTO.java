package com.ruoyi.common.core.domain.dto;
import com.ruoyi.common.enums.LoginType;
import lombok.Data;



@Data
public class ConsultDTO  {

     /**
     * 用户id
     */
    private Long consultId;
    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;


    /**
     * 手机号码
     */
    private String phone;
   

    /**
     * 登录方式，微信等
     */
    private LoginType loginType;

    



}