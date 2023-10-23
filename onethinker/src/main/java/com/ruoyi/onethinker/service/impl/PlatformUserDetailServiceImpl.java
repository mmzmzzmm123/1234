package com.ruoyi.onethinker.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.onethinker.mapper.PlatformUserDetailMapper;
import com.ruoyi.onethinker.domain.PlatformUserDetail;
import com.ruoyi.onethinker.service.IPlatformUserDetailService;

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
}
