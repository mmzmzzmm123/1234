package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.vo.IdNameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PanoramaMapper;
import com.ruoyi.system.domain.Panorama;
import com.ruoyi.system.service.IPanoramaService;

/**
 * 全景管理Service业务层处理
 *
 * @author ruoyi
 * @date 2021-11-02
 */
@Service
public class PanoramaServiceImpl implements IPanoramaService
{
    @Autowired
    private PanoramaMapper panoramaMapper;

    /**
     * 查询全景管理
     *
     * @param id 全景管理主键
     * @return 全景管理
     */
    @Override
    public Panorama selectPanoramaById(Long id)
    {
        return panoramaMapper.selectPanoramaById(id);
    }

    /**
     * 查询全景管理列表
     *
     * @param panorama 全景管理
     * @return 全景管理
     */
    @Override
    public List<Panorama> selectPanoramaList(Panorama panorama)
    {
        return panoramaMapper.selectPanoramaList(panorama);
    }

    /**
     * 新增全景管理
     *
     * @param panorama 全景管理
     * @return 结果
     */
    @Override
    public int insertPanorama(Panorama panorama)
    {
        panorama.setCreateTime(DateUtils.getNowDate());
        return panoramaMapper.insertPanorama(panorama);
    }

    /**
     * 修改全景管理
     *
     * @param panorama 全景管理
     * @return 结果
     */
    @Override
    public int updatePanorama(Panorama panorama)
    {
        panorama.setUpdateTime(DateUtils.getNowDate());
        return panoramaMapper.updatePanorama(panorama);
    }

    /**
     * 批量删除全景管理
     *
     * @param ids 需要删除的全景管理主键
     * @return 结果
     */
    @Override
    public int deletePanoramaByIds(Long[] ids)
    {
        return panoramaMapper.deletePanoramaByIds(ids);
    }

    /**
     * 删除全景管理信息
     *
     * @param id 全景管理主键
     * @return 结果
     */
    @Override
    public int deletePanoramaById(Long id)
    {
        return panoramaMapper.deletePanoramaById(id);
    }

    @Override
    public List<IdNameVo> getIdNames() {

        return panoramaMapper.getIdNames();
    }
}
