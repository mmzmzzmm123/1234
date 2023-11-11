package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.PlatformNightFeeConfigureMapper;
import com.ruoyi.platform.domain.PlatformNightFeeConfigure;
import com.ruoyi.platform.service.IPlatformNightFeeConfigureService;

/**
 * 夜间费用配置Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class PlatformNightFeeConfigureServiceImpl implements IPlatformNightFeeConfigureService {

    private final PlatformNightFeeConfigureMapper platformNightFeeConfigureMapper;

    /**
     * 查询夜间费用配置
     *
     * @param id 夜间费用配置主键
     * @return 夜间费用配置
     */
    @Override
    public PlatformNightFeeConfigure selectPlatformNightFeeConfigureById(Long id) {
        return platformNightFeeConfigureMapper.selectPlatformNightFeeConfigureById(id);
    }

    /**
     * 查询夜间费用配置列表
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 夜间费用配置
     */
    @Override
    public List<PlatformNightFeeConfigure> selectPlatformNightFeeConfigureList(PlatformNightFeeConfigure platformNightFeeConfigure) {
        return platformNightFeeConfigureMapper.selectPlatformNightFeeConfigureList(platformNightFeeConfigure);
    }

    /**
     * 新增夜间费用配置
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 结果
     */
    @Override
    public int insertPlatformNightFeeConfigure(PlatformNightFeeConfigure platformNightFeeConfigure) {
        // 存在记录就不可添加了
        if (ObjectUtil.isNotEmpty(platformNightFeeConfigureMapper.selectPlatformNightFeeConfigureList(new PlatformNightFeeConfigure()))){
            throw new ServiceException("已存在记录，不可再添加");
        }
        String loginUser = SecurityUtils.getUsername();
        Date now = DateUtils.getNowDate();
        platformNightFeeConfigure.setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateBy(loginUser)
                .setCreateBy(loginUser);
        return platformNightFeeConfigureMapper.insertPlatformNightFeeConfigure(platformNightFeeConfigure);
    }

    /**
     * 修改夜间费用配置
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 结果
     */
    @Override
    public int updatePlatformNightFeeConfigure(PlatformNightFeeConfigure platformNightFeeConfigure) {
        platformNightFeeConfigure.setUpdateBy(SecurityUtils.getUsername());
        platformNightFeeConfigure.setUpdateTime(DateUtils.getNowDate());
        return platformNightFeeConfigureMapper.updatePlatformNightFeeConfigure(platformNightFeeConfigure);
    }

    /**
     * 批量删除夜间费用配置
     *
     * @param ids 需要删除的夜间费用配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformNightFeeConfigureByIds(Long[] ids) {
        return platformNightFeeConfigureMapper.deletePlatformNightFeeConfigureByIds(ids);
    }

    /**
     * 删除夜间费用配置信息
     *
     * @param id 夜间费用配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformNightFeeConfigureById(Long id) {
        return platformNightFeeConfigureMapper.deletePlatformNightFeeConfigureById(id);
    }
}
