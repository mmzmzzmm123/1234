package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlSysCarouselMapper;
import com.baoli.sysmanage.domain.BlSysCarousel;
import com.baoli.sysmanage.service.IBlSysCarouselService;

/**
 * 轮播图管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-10
 */
@Service
public class BlSysCarouselServiceImpl implements IBlSysCarouselService 
{
    @Autowired
    private BlSysCarouselMapper blSysCarouselMapper;

    /**
     * 查询轮播图管理
     * 
     * @param id 轮播图管理主键
     * @return 轮播图管理
     */
    @Override
    public BlSysCarousel selectBlSysCarouselById(Long id)
    {
        return blSysCarouselMapper.selectBlSysCarouselById(id);
    }

    /**
     * 查询轮播图管理列表
     * 
     * @param blSysCarousel 轮播图管理
     * @return 轮播图管理
     */
    @Override
    public List<BlSysCarousel> selectBlSysCarouselList(BlSysCarousel blSysCarousel)
    {
        return blSysCarouselMapper.selectBlSysCarouselList(blSysCarousel);
    }

    /**
     * 新增轮播图管理
     * 
     * @param blSysCarousel 轮播图管理
     * @return 结果
     */
    @Override
    public int insertBlSysCarousel(BlSysCarousel blSysCarousel)
    {
        return blSysCarouselMapper.insertBlSysCarousel(blSysCarousel);
    }

    /**
     * 修改轮播图管理
     * 
     * @param blSysCarousel 轮播图管理
     * @return 结果
     */
    @Override
    public int updateBlSysCarousel(BlSysCarousel blSysCarousel)
    {
        return blSysCarouselMapper.updateBlSysCarousel(blSysCarousel);
    }

    /**
     * 批量删除轮播图管理
     * 
     * @param ids 需要删除的轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteBlSysCarouselByIds(Long[] ids)
    {
        return blSysCarouselMapper.deleteBlSysCarouselByIds(ids);
    }

    /**
     * 删除轮播图管理信息
     * 
     * @param id 轮播图管理主键
     * @return 结果
     */
    @Override
    public int deleteBlSysCarouselById(Long id)
    {
        return blSysCarouselMapper.deleteBlSysCarouselById(id);
    }
}
