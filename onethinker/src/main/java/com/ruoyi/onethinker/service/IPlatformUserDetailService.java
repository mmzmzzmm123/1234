package com.ruoyi.onethinker.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.domain.PlatformUserDetail;
import com.ruoyi.onethinker.dto.PlatformUserReqDTO;

/**
 * 平台用户详情信息Service接口
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public interface IPlatformUserDetailService {

    /**
     * 修改平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail);
    /**
     * 保存用户信息，平台来自账户密码输入
     * @param platformUser
     * @return
     */
    PlatformUserDetail saveEntryUserDetailByAccount(PlatformUser platformUser);

    /**
     * 保存用户详情信息，平台来自微信登录补全手机号
     * @param platformUser
     * @param reqDTO
     */
    PlatformUserDetail saveEntryUserDetailByWx(PlatformUser platformUser, PlatformUserReqDTO reqDTO);

    /**
     * 校验手机号是否被其他微信账号绑定
     * @param openId
     * @param phone
     */
    void existsWxRegister(String openId, String phone);

    /**
     * 获取用户信息
     * @param dataId
     * @return
     */
    PlatformUserDetail selectPlatformUserDetailByDataId(String dataId);

    /**
     * 获取当前登录用户信息
     * @return
     */
    PlatformUserDetail queryLoginUserInfo();

    /**
     * 获取用户信息
     * @param phone
     * @return
     */
    PlatformUser queryUserByPhone(String phone);

    /**
     * 用户多名用户信息
     * @param puUserIds
     * @return
     */
    Map<Long, String> selectUserPhoneByUserIds(List<Long> puUserIds);
}
