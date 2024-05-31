package com.onethinker.common.constant;

/**
 * @author yangyouqi
 * @date 2024/5/16
 */
public interface ServicePathConstant {


    /**
     * 常规的服务路径，即/service/**
     */
    String PREFIX_SERVICE_PATH = "/service";

    /**
     * 不鉴权的的服务路径，即/public/**
     */
    String PREFIX_PUBLIC_PATH = "/public";

    /**
     * h5平台API发布
     */
    String PREFIX_WEB_API_PATH = "/web";

    /**
     * 移动端平台发布
     */
    String PREFIX_APP_API_PATH = "/app";

    /**
     * 小程序端平台发布
     */
    String PREFIX_WX_API_PATH = "/wx";

    /**
     * 管理端平台发布
     */
    String PREFIX_ADMIN_API_PATH = "/admin";
}
