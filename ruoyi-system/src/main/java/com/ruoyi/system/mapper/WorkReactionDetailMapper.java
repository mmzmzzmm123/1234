package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkReactionDetail;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface WorkReactionDetailMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param reactionDetailId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkReactionDetail selectWorkReactionDetailByReactionDetailId(Long reactionDetailId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workReactionDetail 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkReactionDetail> selectWorkReactionDetailList(WorkReactionDetail workReactionDetail);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workReactionDetail 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkReactionDetail(WorkReactionDetail workReactionDetail);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workReactionDetail 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkReactionDetail(WorkReactionDetail workReactionDetail);

    /**
     * 删除【请填写功能名称】
     * 
     * @param reactionDetailId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkReactionDetailByReactionDetailId(Long reactionDetailId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param reactionDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkReactionDetailByReactionDetailIds(Long[] reactionDetailIds);
}
