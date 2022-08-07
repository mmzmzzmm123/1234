package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkReaction;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface WorkReactionMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param reactionId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkReaction selectWorkReactionByReactionId(Long reactionId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workReaction 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkReaction> selectWorkReactionList(WorkReaction workReaction);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workReaction 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkReaction(WorkReaction workReaction);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workReaction 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkReaction(WorkReaction workReaction);

    /**
     * 删除【请填写功能名称】
     * 
     * @param reactionId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkReactionByReactionId(Long reactionId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param reactionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkReactionByReactionIds(Long[] reactionIds);
}
