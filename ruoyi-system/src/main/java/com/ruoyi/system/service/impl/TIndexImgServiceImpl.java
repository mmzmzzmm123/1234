package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TIndexImgMapper;
import com.ruoyi.system.domain.TIndexImg;
import com.ruoyi.system.service.ITIndexImgService;

/**
 * 首页图片配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-24
 */
@Service
public class TIndexImgServiceImpl implements ITIndexImgService 
{
    @Autowired
    private TIndexImgMapper tIndexImgMapper;

    /**
     * 查询首页图片配置
     * 
     * @param id 首页图片配置主键
     * @return 首页图片配置
     */
    @Override
    public TIndexImg selectTIndexImgById(Long id)
    {
        return tIndexImgMapper.selectTIndexImgById(id);
    }

    /**
     * 查询首页图片配置列表
     * 
     * @param tIndexImg 首页图片配置
     * @return 首页图片配置
     */
    @Override
    public List<TIndexImg> selectTIndexImgList(TIndexImg tIndexImg)
    {
        return tIndexImgMapper.selectTIndexImgList(tIndexImg);
    }

    /**
     * 新增首页图片配置
     * 
     * @param tIndexImg 首页图片配置
     * @return 结果
     */
    @Override
    public int insertTIndexImg(TIndexImg tIndexImg)
    {
        tIndexImg.setCreateTime(DateUtils.getNowDate());
        return tIndexImgMapper.insertTIndexImg(tIndexImg);
    }

    /**
     * 修改首页图片配置
     * 
     * @param tIndexImg 首页图片配置
     * @return 结果
     */
    @Override
    public int updateTIndexImg(TIndexImg tIndexImg)
    {
        tIndexImg.setUpdateTime(DateUtils.getNowDate());
        return tIndexImgMapper.updateTIndexImg(tIndexImg);
    }

    /**
     * 批量删除首页图片配置
     * 
     * @param ids 需要删除的首页图片配置主键
     * @return 结果
     */
    @Override
    public int deleteTIndexImgByIds(Long[] ids)
    {
        return tIndexImgMapper.deleteTIndexImgByIds(ids);
    }

    /**
     * 删除首页图片配置信息
     * 
     * @param id 首页图片配置主键
     * @return 结果
     */
    @Override
    public int deleteTIndexImgById(Long id)
    {
        return tIndexImgMapper.deleteTIndexImgById(id);
    }
}
