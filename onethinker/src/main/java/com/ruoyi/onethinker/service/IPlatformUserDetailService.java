package com.ruoyi.onethinker.service;

import java.util.List;

import com.ruoyi.onethinker.domain.PlatformUserDetail;

/**
 * 平台用户详情信息Service接口
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public interface IPlatformUserDetailService {
    /**
     * 查询平台用户详情信息
     *
     * @param id 平台用户详情信息主键
     * @return 平台用户详情信息
     */
    public PlatformUserDetail selectPlatformUserDetailById(Long id);

    /**
     * 查询平台用户详情信息列表
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 平台用户详情信息集合
     */
    public List<PlatformUserDetail> selectPlatformUserDetailList(PlatformUserDetail platformUserDetail);

    /**
     * 新增平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    public int insertPlatformUserDetail(PlatformUserDetail platformUserDetail);

    /**
     * 修改平台用户详情信息
     *
     * @param platformUserDetail 平台用户详情信息
     * @return 结果
     */
    public int updatePlatformUserDetail(PlatformUserDetail platformUserDetail);

    /**
     * 批量删除平台用户详情信息
     *
     * @param ids 需要删除的平台用户详情信息主键集合
     * @return 结果
     */
    public int deletePlatformUserDetailByIds(Long[] ids);

    /**
     * 删除平台用户详情信息信息
     *
     * @param id 平台用户详情信息主键
     * @return 结果
     */
    public int deletePlatformUserDetailById(Long id);
}
