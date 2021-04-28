package com.ruoyi.zcrpsgl.service;

import java.util.List;
import com.ruoyi.zcrpsgl.domain.TsbzZcrbmsq;

/**
 * 主持人报名申请Service接口
 * 
 * @author tsbz
 * @date 2021-04-15
 */
public interface ITsbzZcrbmsqService 
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
     * 批量删除主持人报名申请
     * 
     * @param ids 需要删除的主持人报名申请ID
     * @return 结果
     */
    public int deleteTsbzZcrbmsqByIds(Long[] ids);

    /**
     * 删除主持人报名申请信息
     * 
     * @param id 主持人报名申请ID
     * @return 结果
     */
    public int deleteTsbzZcrbmsqById(Long id);

    /**
     * * 通过学校审核(主持人评审管理-学校审核)
    */
    public int passXxpsStatus(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 退回学校审核(主持人评审管理-学校审核)
     */
    public int backXxpsStatus(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * * 通过区级审核(主持人评审管理-区级审核)
     */
    public int passQjpsStatus(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 退回区级审核(主持人评审管理-区级审核)
     */
    public int backQjpsStatus(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * * 通过结果确认(主持人评审管理-结果确认)
     */
    public int passJgqrStatus(TsbzZcrbmsq tsbzZcrbmsq);

    /**
     * 退回结果确认(主持人评审管理-结果确认)
     */
    public int backJgqrStatus(TsbzZcrbmsq tsbzZcrbmsq);

}
