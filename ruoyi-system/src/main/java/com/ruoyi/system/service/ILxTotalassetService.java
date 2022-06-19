package com.ruoyi.system.service;

import java.sql.Date;
import java.util.List;
import com.ruoyi.system.domain.LxTotalasset;

/**
 * 总资产走势Service接口
 * 
 * @author ruoyi
 * @date 2022-06-19
 */
public interface ILxTotalassetService 
{
    /**
     * 查询总资产走势
     * 
     * @param riqi 总资产走势主键
     * @return 总资产走势
     */
    public LxTotalasset selectLxTotalassetByRiqi(Date riqi);

    /**
     * 查询总资产走势列表
     * 
     * @param lxTotalasset 总资产走势
     * @return 总资产走势集合
     */
    public List<LxTotalasset> selectLxTotalassetList(LxTotalasset lxTotalasset);

    /**
     * 新增总资产走势
     * 
     * @param lxTotalasset 总资产走势
     * @return 结果
     */
    public int insertLxTotalasset(LxTotalasset lxTotalasset);

    /**
     * 修改总资产走势
     * 
     * @param lxTotalasset 总资产走势
     * @return 结果
     */
    public int updateLxTotalasset(LxTotalasset lxTotalasset);

    /**
     * 批量删除总资产走势
     * 
     * @param riqis 需要删除的总资产走势主键集合
     * @return 结果
     */
    public int deleteLxTotalassetByRiqis(Date[] riqis);

    /**
     * 删除总资产走势信息
     * 
     * @param riqi 总资产走势主键
     * @return 结果
     */
    public int deleteLxTotalassetByRiqi(Date riqi);
}
