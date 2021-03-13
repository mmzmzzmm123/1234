package com.gox.basic.service;

import java.util.List;
import com.gox.basic.domain.Work;

/**
 *   Service接口
 * 
 * @author gox
 * @date 2021-03-11
 */
public interface IWorkService 
{
    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    public Work selectWorkById(Long id);

    /**
     * 查询  列表
     * 
     * @param work   
     * @return   集合
     */
    public List<Work> selectWorkList(Work work);

    /**
     * 新增  
     * 
     * @param work   
     * @return 结果
     */
    public int insertWork(Work work);

    /**
     * 修改  
     * 
     * @param work   
     * @return 结果
     */
    public int updateWork(Work work);

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteWorkByIds(Long[] ids);

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    public int deleteWorkById(Long id);
}
