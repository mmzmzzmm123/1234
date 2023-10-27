package com.ruoyi.onethinker.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.onethinker.mapper.PlatformUserIntegralMapper;
import com.ruoyi.onethinker.domain.PlatformUserIntegral;
import com.ruoyi.onethinker.service.IPlatformUserIntegralService;
import lombok.extern.log4j.Log4j2;

/**
 * 平台用户积分Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralServiceImpl implements IPlatformUserIntegralService {
    @Autowired
    private PlatformUserIntegralMapper platformUserIntegralMapper;

    /**
     * 查询平台用户积分
     *
     * @param id 平台用户积分主键
     * @return 平台用户积分
     */
    @Override
    public PlatformUserIntegral selectPlatformUserIntegralById(Long id) {
        return platformUserIntegralMapper.selectPlatformUserIntegralById(id);
    }

    /**
     * 查询平台用户积分列表
     *
     * @param platformUserIntegral 平台用户积分
     * @return 平台用户积分
     */
    @Override
    public List<PlatformUserIntegral> selectPlatformUserIntegralList(PlatformUserIntegral platformUserIntegral) {
        return platformUserIntegralMapper.selectPlatformUserIntegralList(platformUserIntegral);
    }

    /**
     * 新增平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    @Override
    public int insertPlatformUserIntegral(PlatformUserIntegral platformUserIntegral) {
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegral.get));
                platformUserIntegral.setCreateTime(DateUtils.getNowDate());
            return platformUserIntegralMapper.insertPlatformUserIntegral(platformUserIntegral);
    }

    /**
     * 修改平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    @Override
    public int updatePlatformUserIntegral(PlatformUserIntegral platformUserIntegral) {
                platformUserIntegral.setUpdateTime(DateUtils.getNowDate());
        return platformUserIntegralMapper.updatePlatformUserIntegral(platformUserIntegral);
    }

    /**
     * 批量删除平台用户积分
     *
     * @param ids 需要删除的平台用户积分主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserIntegralByIds(Long[] ids) {
        return platformUserIntegralMapper.deletePlatformUserIntegralByIds(ids);
    }

    /**
     * 删除平台用户积分信息
     *
     * @param id 平台用户积分主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserIntegralById(Long id) {
        return platformUserIntegralMapper.deletePlatformUserIntegralById(id);
    }
}
