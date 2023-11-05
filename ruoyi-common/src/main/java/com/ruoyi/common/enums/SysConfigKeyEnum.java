package com.ruoyi.common.enums;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2023/10/25 0025 10:48
 */
public enum SysConfigKeyEnum {

    /**
     * 主框架页-默认皮肤样式名称
     */
    SYS_INDEX_SKINNAME("sys.index.skinName", "主框架页-默认皮肤样式名称"),
    /**
     * 用户管理-账号初始密码
     */
    SYS_USER_INITPASSWORD("sys.user.initPassword", "用户管理-账号初始密码"),
    /**
     * 主框架页-侧边栏主题
     */
    SYS_INDEX_SIDETHEME("sys.index.sideTheme", "主框架页-侧边栏主题"),
    /**
     * 账号自助-验证码开关
     */
    SYS_ACCOUNT_CAPTCHAENABLED("sys.account.captchaEnabled", "账号自助-验证码开关"),
    /**
     * 账号自助-是否开启用户注册功能
     */
    SYS_ACCOUNT_REGISTERUSER("sys.account.registerUser", "账号自助-是否开启用户注册功能"),
    /**
     * 用户登录-黑名单列表
     */
    SYS_LOGIN_BLACKIPLIST("sys.login.blackIPList", "用户登录-黑名单列表"),
    /**
     * 用户注册-默认头像
     */
    DEFAULT_AVATAR_URL("default_avatar_url", "用户注册-默认头像"),
    /**
     * 用户注册-默认昵称
     */
    DEFAULT_NICK_NAME("default_nick_name", "用户注册-默认昵称"),
    /**
     * 用户注册-默认密码
     */
    DEFAULT_PASSWORD("default_password", "用户注册-默认密码"),
    /**
     * 平台用户账号
     */
    PU_USER_NAME("pu_user_name", "平台用户账号"),
    /**
     * 平台用户密码
     */
    PU_USER_PASSWORD("pu_user_password", "平台用户密码"),
    /**
     * 小程序APPID
     */
    APPID("appId", "小程序APPID"),
    /**
     * 小程序APP_SECRET
     */
    APPSECRET("appsecret", "小程序APP_SECRET"),
    /**
     * 默认图片地址
     */
    DETAIL_FILE_PATH("detail_file_path", "默认图片地址"),
    /**
     * 创建二维码私钥
     */
    QR_CODE_RSA_PRIVATE_KEY("qr_code_rsa_private_key","创建二维码私钥"),
    /**
     * 创建二维码公钥
     */
    QR_CODE_RSA_PUBLIC_KEY("qr_code_rsa_public_key","创建二维码公钥"),

    /**
     * 服务器文件前缀
     */
    SERVICE_FILE_PUBLIC_URL("service_file_public_url", "服务器文件前缀");
    String code;
    String msg;

    SysConfigKeyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SysConfigKeyEnum getSysConfigKeyEnumByCode(String code) {
        for (SysConfigKeyEnum value : SysConfigKeyEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
