package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkFeeding;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface WorkFeedingMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param feedingId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkFeeding selectWorkFeedingByFeedingId(Long feedingId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workFeeding 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkFeeding> selectWorkFeedingList(WorkFeeding workFeeding);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workFeeding 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkFeeding(WorkFeeding workFeeding);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workFeeding 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkFeeding(WorkFeeding workFeeding);

    /**
     * 删除【请填写功能名称】
     * 
     * @param feedingId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkFeedingByFeedingId(Long feedingId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param feedingIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkFeedingByFeedingIds(Long[] feedingIds);
}
