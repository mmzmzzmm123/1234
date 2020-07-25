package com.muster.web.controller.muster.weixin;

import com.muster.common.config.MusterConfig;
import com.muster.common.constant.WxConsts;
import com.muster.logic.DbLogicService;
import com.muster.logic.model.ProcedureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-11.
 *
 * @author 刘江涛.
 */
@Component
public class WeChatServiceImpl implements  WeChatService{

    private DbLogicService storedProcedure;

    /** 微信用户 */
    public final static String PROC_DU02A_USER_LOGIN = "proc_DU02A_user_login";
    public final static String PROC_DU02C_USER_CREATE = "proc_DU02C_user_create";
    @Autowired
    WeChatServiceImpl (DbLogicService storedProcedure) {
        this.storedProcedure = storedProcedure;
    }

    @Override
    public int query(final String openId) {
        return 1;
    }


    @Override
    public boolean login(final WeiXinUserInfo userInfo) {
       return this.login(userInfo, MusterConfig.getAppId(), MusterConfig.getBizId());
    }

    @Override
    public boolean login(final WeiXinUserInfo userInfo, String appId, String bizId) {
        // 创建账号
        Map<String, Object> params = new HashMap<>();
        params.put("01_acc_type", WxConsts.Account.TYPE);
        params.put("02_username", userInfo.getWxOpenid());
        params.put("03_password", WxConsts.Account.PASSWORD);
        params.put("04_userip", "0.0.0.0");
        params.put("appId", appId);
        params.put("bizId", bizId);

        int createRes = this.create(params);
        if (createRes > 0) {
            int loginRes = this.login(params);
            return loginRes > 0;
        }
        return false;
    }

    private int create( final Map<String, Object> params) {
        Map<String, Object> p = new HashMap<>(params);
        p.put("05_json", "{}");
        ProcedureResult createRes = storedProcedure.exec(PROC_DU02C_USER_CREATE, p);
        return createRes.getRes();
    }

    private int login(final Map<String, Object> params) {
        ProcedureResult loginRes = storedProcedure.exec(PROC_DU02A_USER_LOGIN, params);
        return loginRes.getRes();
    }
}
