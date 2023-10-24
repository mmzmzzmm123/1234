package com.ruoyi.onethinker.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.domain.PlatformUserDetail;
import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.mapper.PlatformUserDetailMapper;
import com.ruoyi.onethinker.service.IPlatformUserDetailService;
import com.ruoyi.system.service.ISysConfigService;

/**
 * 平台用户详情信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
public class PlatformUserDetailServiceImpl implements IPlatformUserDetailService {
    @Autowired
    private PlatformUserDetailMapper platformUserDetailMapper;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private RedisCache redisCache;
    /**
     * 查询平台用户详情信息
     *
     * @param id 平台用户详情信息主键
     * @return 平台用户详情信息
     */
    @Override
    public PlatformUserDetail selectPlatformUserDetailById(Long id) {
        return platformUserDetailMapper.selectPlatformUserDetailById(id);
    }

    /**
     * 查询平台用户详情信息列表
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 平台用户详情信息
     */
    @Override
    public List<PlatformUserDetail> selectPlatformUserDetailList(PlatformUserDetail platformUserDetail) {
        return platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
    }

    /**
     * 新增平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    @Override
    public int insertPlatformUserDetail(PlatformUserDetail platformUserDetail) {
        platformUserDetail.setCreateTime(DateUtils.getNowDate());
        return platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
    }

    /**
     * 修改平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    @Override
    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail) {
        platformUserDetail.setUpdateTime(DateUtils.getNowDate());
        return platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
    }

    /**
     * 批量删除平台用户详情信息
     *
     * @param ids 需要删除的平台用户详情信息主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserDetailByIds(Long[] ids) {
        return platformUserDetailMapper.deletePlatformUserDetailByIds(ids);
    }

    /**
     * 删除平台用户详情信息信息
     *
     * @param id 平台用户详情信息主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserDetailById(Long id) {
        return platformUserDetailMapper.deletePlatformUserDetailById(id);
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByAccount(PlatformUser platformUser) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setAvatarUrl(platformUser.getAvatarUrl());
        platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT);
        platformUserDetail.setPhone(platformUser.getPhone());
        platformUserDetail.setUsername(platformUser.getPhone());
        platformUserDetail.setNickName(platformUser.getNickName());
        platformUserDetail.setPassword(sysConfigService.selectConfigByKey(PlatformUserDetail.DEFAULT_PASSWORD));
        platformUserDetail.setDataId(platformUser.getPhone());
        platformUserDetail.setWeight(System.currentTimeMillis());
        platformUserDetail.setCreateTime(new Date());
        platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        return platformUserDetail;
    }

    @Override
    public PlatformUserDetail saveEntryUserDetailByWx(PlatformUser platformUser, PlatformUserReqDTO reqDTO) {
        String redisKey = RedisKeyConstants.QUERY_USER_DETAIL_DATA_ID_KEY + reqDTO.getOpenId();

        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(reqDTO.getOpenId());
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            // 保存
            platformUserDetail.setAvatarUrl(ObjectUtils.isEmpty(reqDTO.getAvatarUrl()) ? platformUser.getAvatarUrl() : reqDTO.getAvatarUrl());
            platformUserDetail.setNickName(ObjectUtils.isEmpty(reqDTO.getNickName()) ? platformUser.getNickName() : reqDTO.getNickName());
            platformUserDetail.setEnabled(PlatformUserDetail.STATE_TYPE_ENABLED);
            platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
            platformUserDetail.setPhone(platformUser.getPhone());
            platformUserDetail.setDataId(reqDTO.getOpenId());
            platformUserDetail.setWeight(System.currentTimeMillis());
            platformUserDetail.setCreateTime(new Date());
            platformUserDetailMapper.insertPlatformUserDetail(platformUserDetail);
        } else {
            // 更新手机号
            platformUserDetail = platformUserDetails.get(0);
            if (!ObjectUtils.isEmpty(platformUserDetail.getPhone()) && !platformUserDetail.getPhone().equals(platformUser.getPhone())) {
                throw new RuntimeException("该微信号已绑定手机号");
            }
            platformUserDetail.setPhone(platformUser.getPhone());
            platformUserDetail.setUpdateTime(new Date());
            platformUserDetailMapper.updatePlatformUserDetail(platformUserDetail);
        }
        redisCache.setCacheObject(redisKey,platformUserDetail,1,TimeUnit.DAYS);
        return platformUserDetail;
    }

    @Override
    public void existsWxRegister(String openId, String phone) {
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setPhone(phone);
        platformUserDetail.setType(PlatformUserReqDTO.SOURCE_TYPE_WX);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            return;
        }
        platformUserDetail = platformUserDetails.get(0);
        if (!platformUserDetail.getDataId().equals(openId)) {
            throw new RuntimeException("手机号已被其他微信绑定");
        }

    }

    @Override
    public PlatformUserDetail selectPlatformUserDetailByDataId(String dataId) {
        String redisKey = RedisKeyConstants.QUERY_USER_DETAIL_DATA_ID_KEY + dataId;

        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        PlatformUserDetail platformUserDetail = new PlatformUserDetail();
        platformUserDetail.setDataId(dataId);
        List<PlatformUserDetail> platformUserDetails = platformUserDetailMapper.selectPlatformUserDetailList(platformUserDetail);
        if (ObjectUtils.isEmpty(platformUserDetails) || platformUserDetails.isEmpty()) {
            return null;
        }
        redisCache.setCacheObject(redisKey,platformUserDetails.get(0),1, TimeUnit.DAYS);
        return platformUserDetails.get(0);
    }

    @Override
    public PlatformUserDetail queryLoginUserInfo() {
        String dataId = SecurityUtils.getLoginUser().getDataId();
        PlatformUserDetail platformUserDetail = selectPlatformUserDetailByDataId(dataId);
        return platformUserDetail;
    }
}
