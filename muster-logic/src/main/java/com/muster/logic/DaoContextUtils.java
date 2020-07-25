package com.muster.logic;


import com.muster.common.config.MusterConfig;
import com.muster.logic.BO.DaoContext;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-05.
 *
 * @author 刘江涛.
 */
public class DaoContextUtils {

    public static DaoContext createContext() {
        String hid = MDC.get("hid");
        int userId = Integer.valueOf(MDC.get("userId"));
        int token = Integer.valueOf(MDC.get("token"));
        String appId = MusterConfig.getAppId();
        String bizId = MusterConfig.getBizId();
        return new DaoContext(appId, bizId, hid, userId, token);
    }

    public static DaoContext createContext(final Map<String, Object> params) {
        String hid = MDC.get("hid");
        int userId = Integer.valueOf(MDC.get("userId"));
        int token = Integer.valueOf(MDC.get("token"));
        String appId = String.valueOf(params.getOrDefault("appId", MusterConfig.getAppId()));
        String bizId = String.valueOf(params.getOrDefault("bizId", MusterConfig.getBizId()));
        params.remove("appId");
        params.remove("bizId");
        return new DaoContext(appId, bizId, hid, userId, token);
    }
}
