package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PanoramaInfoMapper;
import com.ruoyi.system.domain.PanoramaInfo;
import com.ruoyi.system.service.IPanoramaInfoService;

/**
 * 全景图管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-01
 */
@Service
public class PanoramaInfoServiceImpl implements IPanoramaInfoService 
{
    @Autowired
    private PanoramaInfoMapper panoramaInfoMapper;

    /**
     * 查询全景图管理
     * 
     * @param id 全景图管理主键
     * @return 全景图管理
     */
    @Override
    public PanoramaInfo selectPanoramaInfoById(Long id)
    {
        return panoramaInfoMapper.selectPanoramaInfoById(id);
    }

    /**
     * 查询全景图管理列表
     * 
     * @param panoramaInfo 全景图管理
     * @return 全景图管理
     */
    @Override
    public List<PanoramaInfo> selectPanoramaInfoList(PanoramaInfo panoramaInfo)
    {
        return panoramaInfoMapper.selectPanoramaInfoList(panoramaInfo);
    }

    /**
     * 新增全景图管理
     * 
     * @param panoramaInfo 全景图管理
     * @return 结果
     */
    @Override
    public int insertPanoramaInfo(PanoramaInfo panoramaInfo)
    {
        panoramaInfo.setCreateTime(DateUtils.getNowDate());
        return panoramaInfoMapper.insertPanoramaInfo(panoramaInfo);
    }

    /**
     * 修改全景图管理
     * 
     * @param panoramaInfo 全景图管理
     * @return 结果
     */
    @Override
    public int updatePanoramaInfo(PanoramaInfo panoramaInfo)
    {
        panoramaInfo.setUpdateTime(DateUtils.getNowDate());
        return panoramaInfoMapper.updatePanoramaInfo(panoramaInfo);
    }

    /**
     * 批量删除全景图管理
     * 
     * @param ids 需要删除的全景图管理主键
     * @return 结果
     */
    @Override
    public int deletePanoramaInfoByIds(Long[] ids)
    {
        return panoramaInfoMapper.deletePanoramaInfoByIds(ids);
    }

    /**
     * 删除全景图管理信息
     * 
     * @param id 全景图管理主键
     * @return 结果
     */
    @Override
    public int deletePanoramaInfoById(Long id)
    {
        return panoramaInfoMapper.deletePanoramaInfoById(id);
    }
}
