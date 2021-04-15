package com.ruoyi.zcrpsgl.mapper;

import java.util.List;
import com.ruoyi.zcrpsgl.domain.TsbzZcrbmsq;

/**
 * 主持人报名申请Mapper接口
 * 
 * @author tsbz
 * @date 2021-04-15
 */
public interface TsbzZcrbmsqMapper 
{
    /**
     * 查询主持人报名申请
     * 
     * @param id 主持人报名申请ID
     * @return 主持人报名申请
     */
    public TsbzZcrbmsq selectTsbzZcrbmsqById(Long id);

    /**
     * 查询主持人报名申请列表
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 主持人报名申请集合
     */
    public List<TsbzZcrbmsq> selectTsbzZcrbmsqList(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 新增主持人报名申请
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 结果
     */
    public int insertTsbzZcrbmsq(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 修改主持人报名申请
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 结果
     */
    public int updateTsbzZcrbmsq(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 删除主持人报名申请
     * 
     * @param id 主持人报名申请ID
     * @return 结果
     */
    public int deleteTsbzZcrbmsqById(Long id);

    /**
     * 批量删除主持人报名申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzZcrbmsqByIds(Long[] ids);
}
