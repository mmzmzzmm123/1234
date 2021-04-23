package com.ruoyi.zcrpsgl.service;

import java.util.List;
import com.ruoyi.zcrpsgl.domain.TsbzZcrjdcj;

/**
 * 主持人基地Service接口
 * 
 * @author tsbz
 * @date 2021-04-23
 */
public interface ITsbzZcrjdcjService 
{
    /**
     * 查询主持人基地
     * 
     * @param id 主持人基地ID
     * @return 主持人基地
     */
    public TsbzZcrjdcj selectTsbzZcrjdcjById(Long id);

    /**
     * 查询主持人基地列表
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 主持人基地集合
     */
    public List<TsbzZcrjdcj> selectTsbzZcrjdcjList(TsbzZcrjdcj tsbzZcrjdcj);

    /**
     * 新增主持人基地
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 结果
     */
    public int insertTsbzZcrjdcj(TsbzZcrjdcj tsbzZcrjdcj);

    /**
     * 修改主持人基地
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 结果
     */
    public int updateTsbzZcrjdcj(TsbzZcrjdcj tsbzZcrjdcj);

    /**
     * 批量删除主持人基地
     * 
     * @param ids 需要删除的主持人基地ID
     * @return 结果
     */
    public int deleteTsbzZcrjdcjByIds(Long[] ids);

    /**
     * 删除主持人基地信息
     * 
     * @param id 主持人基地ID
     * @return 结果
     */
    public int deleteTsbzZcrjdcjById(Long id);
}
