package com.ruoyi.jdgl.mapper;

import java.util.List;
import com.ruoyi.jdgl.domain.TsbzJdgljxsj;

/**
 * 基地管理教学实践Mapper接口
 * 
 * @author tsbz
 * @date 2021-05-17
 */
public interface TsbzJdgljxsjMapper 
{
    /**
     * 查询基地管理教学实践
     * 
     * @param id 基地管理教学实践ID
     * @return 基地管理教学实践
     */
    public TsbzJdgljxsj selectTsbzJdgljxsjById(Long id);

    /**
     * 查询基地管理教学实践列表
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 基地管理教学实践集合
     */
    public List<TsbzJdgljxsj> selectTsbzJdgljxsjList(TsbzJdgljxsj tsbzJdgljxsj);

    /**
     * 新增基地管理教学实践
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 结果
     */
    public int insertTsbzJdgljxsj(TsbzJdgljxsj tsbzJdgljxsj);

    /**
     * 修改基地管理教学实践
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 结果
     */
    public int updateTsbzJdgljxsj(TsbzJdgljxsj tsbzJdgljxsj);

    /**
     * 删除基地管理教学实践
     * 
     * @param id 基地管理教学实践ID
     * @return 结果
     */
    public int deleteTsbzJdgljxsjById(Long id);

    /**
     * 批量删除基地管理教学实践
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJdgljxsjByIds(Long[] ids);
}
