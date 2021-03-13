package com.gox.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.WorkMapper;
import com.gox.basic.domain.Work;
import com.gox.basic.service.IWorkService;

/**
 *   Service业务层处理
 * 
 * @author gox
 * @date 2021-03-11
 */
@Service
public class WorkServiceImpl implements IWorkService 
{
    @Autowired
    private WorkMapper workMapper;

    /**
     * 查询  
     * 
     * @param id   ID
     * @return   
     */
    @Override
    public Work selectWorkById(Long id)
    {
        return workMapper.selectWorkById(id);
    }

    /**
     * 查询  列表
     * 
     * @param work   
     * @return   
     */
    @Override
    public List<Work> selectWorkList(Work work)
    {
        return workMapper.selectWorkList(work);
    }

    /**
     * 新增  
     * 
     * @param work   
     * @return 结果
     */
    @Override
    public int insertWork(Work work)
    {
        return workMapper.insertWork(work);
    }

    /**
     * 修改  
     * 
     * @param work   
     * @return 结果
     */
    @Override
    public int updateWork(Work work)
    {
        return workMapper.updateWork(work);
    }

    /**
     * 批量删除  
     * 
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteWorkByIds(Long[] ids)
    {
        return workMapper.deleteWorkByIds(ids);
    }

    /**
     * 删除  信息
     * 
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteWorkById(Long id)
    {
        return workMapper.deleteWorkById(id);
    }
}
