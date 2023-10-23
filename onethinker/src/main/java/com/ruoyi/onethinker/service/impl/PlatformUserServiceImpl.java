package com.ruoyi.onethinker.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;
import com.ruoyi.onethinker.mapper.PlatformUserMapper;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.service.IPlatformUserService;

/**
 * 平台用户Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Service
public class PlatformUserServiceImpl implements IPlatformUserService {
    @Autowired
    private PlatformUserMapper platformUserMapper;

    /**
     * 查询平台用户
     *
     * @param id 平台用户主键
     * @return 平台用户
     */
    @Override
    public PlatformUser selectPlatformUserById(Long id) {
        return platformUserMapper.selectPlatformUserById(id);
    }

    /**
     * 查询平台用户列表
     *
     * @param platformUser 平台用户
     * @return 平台用户
     */
    @Override
    public List<PlatformUser> selectPlatformUserList(PlatformUser platformUser) {
        return platformUserMapper.selectPlatformUserList(platformUser);
    }

    /**
     * 新增平台用户
     *
     * @param platformUser 平台用户
     * @return 结果
     */
    @Override
    public int insertPlatformUser(PlatformUser platformUser) {
        platformUser.setCreateTime(DateUtils.getNowDate());
        return platformUserMapper.insertPlatformUser(platformUser);
    }

    /**
     * 修改平台用户
     *
     * @param platformUser 平台用户
     * @return 结果
     */
    @Override
    public int updatePlatformUser(PlatformUser platformUser) {
        platformUser.setUpdateTime(DateUtils.getNowDate());
        return platformUserMapper.updatePlatformUser(platformUser);
    }

    /**
     * 批量删除平台用户
     *
     * @param ids 需要删除的平台用户主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserByIds(Long[] ids) {
        return platformUserMapper.deletePlatformUserByIds(ids);
    }

    /**
     * 删除平台用户信息
     *
     * @param id 平台用户主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserById(Long id) {
        return platformUserMapper.deletePlatformUserById(id);
    }

    @Override
    public PlatformUserResDTO platformUserServiceLogin(PlatformUserReqDTO reqDTO) {

        return null;
    }
}
