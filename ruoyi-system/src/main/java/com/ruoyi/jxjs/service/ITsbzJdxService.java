package com.ruoyi.jxjs.service;

import java.util.List;
import com.ruoyi.jxjs.domain.TsbzJdx;

/**
 * 基地校Service接口
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public interface ITsbzJdxService 
{
    /**
     * 查询基地校
     * 
     * @param id 基地校ID
     * @return 基地校
     */
    public TsbzJdx selectTsbzJdxById(String id);

    /**
     * 查询基地校列表
     * 
     * @param tsbzJdx 基地校
     * @return 基地校集合
     */
    public List<TsbzJdx> selectTsbzJdxList(TsbzJdx tsbzJdx);

    /**
     * 新增基地校
     * 
     * @param tsbzJdx 基地校
     * @return 结果
     */
    public int insertTsbzJdx(TsbzJdx tsbzJdx);

    /**
     * 修改基地校
     * 
     * @param tsbzJdx 基地校
     * @return 结果
     */
    public int updateTsbzJdx(TsbzJdx tsbzJdx);

    /**
     * 批量删除基地校
     * 
     * @param ids 需要删除的基地校ID
     * @return 结果
     */
    public int deleteTsbzJdxByIds(String[] ids);

    /**
     * 删除基地校信息
     * 
     * @param id 基地校ID
     * @return 结果
     */
    public int deleteTsbzJdxById(String id);
}
