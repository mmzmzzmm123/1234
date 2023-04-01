package com.geek.system.service.impl;

import java.util.List;
import com.geek.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geek.system.mapper.SysBannerMapper;
import com.geek.system.domain.SysBanner;
import com.geek.system.service.ISysBannerService;

/**
 * banner配置Service业务层处理
 * 
 * @author xuek
 * @date 2023-03-30
 */
@Service
public class SysBannerServiceImpl implements ISysBannerService {
    @Autowired
    private SysBannerMapper sysBannerMapper;

    /**
     * 查询banner配置
     * 
     * @param id banner配置主键
     * @return banner配置
     */
    @Override
    public SysBanner selectSysBannerById(Long id){
        return sysBannerMapper.selectSysBannerById(id);
    }

    /**
     * 查询banner配置列表
     * 
     * @param sysBanner banner配置
     * @return banner配置
     */
    @Override
    public List<SysBanner> selectSysBannerList(SysBanner sysBanner){
        return sysBannerMapper.selectSysBannerList(sysBanner);
    }

    /**
     * 新增banner配置
     * 
     * @param sysBanner banner配置
     * @return 结果
     */
    @Override
    public int insertSysBanner(SysBanner sysBanner){
        sysBanner.setCreateTime(DateUtils.getNowDate());
        return sysBannerMapper.insertSysBanner(sysBanner);
    }

    /**
     * 修改banner配置
     * 
     * @param sysBanner banner配置
     * @return 结果
     */
    @Override
    public int updateSysBanner(SysBanner sysBanner){
        return sysBannerMapper.updateSysBanner(sysBanner);
    }

    /**
     * 批量删除banner配置
     * 
     * @param ids 需要删除的banner配置主键
     * @return 结果
     */
    @Override
    public int deleteSysBannerByIds(Long[] ids){
        return sysBannerMapper.deleteSysBannerByIds(ids);
    }

    /**
     * 删除banner配置信息
     * 
     * @param id banner配置主键
     * @return 结果
     */
    @Override
    public int deleteSysBannerById(Long id){
        return sysBannerMapper.deleteSysBannerById(id);
    }
}
