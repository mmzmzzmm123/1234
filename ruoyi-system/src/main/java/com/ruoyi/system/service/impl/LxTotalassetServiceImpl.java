package com.ruoyi.system.service.impl;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.LxTotalassetMapper;
import com.ruoyi.system.domain.LxTotalasset;
import com.ruoyi.system.service.ILxTotalassetService;

/**
 * 总资产走势Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-19
 */
@Service
public class LxTotalassetServiceImpl implements ILxTotalassetService 
{
    @Autowired
    private LxTotalassetMapper lxTotalassetMapper;

    /**
     * 查询总资产走势
     * 
     * @param riqi 总资产走势主键
     * @return 总资产走势
     */
    @Override
    public LxTotalasset selectLxTotalassetByRiqi(Date riqi)
    {
        return lxTotalassetMapper.selectLxTotalassetByRiqi(riqi);
    }

    /**
     * 查询总资产走势列表
     * 
     * @param lxTotalasset 总资产走势
     * @return 总资产走势
     */
    @Override
    public List<LxTotalasset> selectLxTotalassetList(LxTotalasset lxTotalasset)
    {
        return lxTotalassetMapper.selectLxTotalassetList(lxTotalasset);
    }

    /**
     * 新增总资产走势
     * 
     * @param lxTotalasset 总资产走势
     * @return 结果
     */
    @Override
    public int insertLxTotalasset(LxTotalasset lxTotalasset)
    {
        return lxTotalassetMapper.insertLxTotalasset(lxTotalasset);
    }

    /**
     * 修改总资产走势
     * 
     * @param lxTotalasset 总资产走势
     * @return 结果
     */
    @Override
    public int updateLxTotalasset(LxTotalasset lxTotalasset)
    {
        return lxTotalassetMapper.updateLxTotalasset(lxTotalasset);
    }

    /**
     * 批量删除总资产走势
     * 
     * @param riqis 需要删除的总资产走势主键
     * @return 结果
     */
    @Override
    public int deleteLxTotalassetByRiqis(Date[] riqis)
    {
        return lxTotalassetMapper.deleteLxTotalassetByRiqis(riqis);
    }

    /**
     * 删除总资产走势信息
     * 
     * @param riqi 总资产走势主键
     * @return 结果
     */
    @Override
    public int deleteLxTotalassetByRiqi(Date riqi)
    {
        return lxTotalassetMapper.deleteLxTotalassetByRiqi(riqi);
    }
}
