package com.ruoyi.xypsgl.service;

import java.util.List;
import com.ruoyi.xypsgl.domain.TsbzXybmsq;

/**
 * 学员报名申请Service接口
 * 
 * @author tsbz
 * @date 2021-04-23
 */
public interface ITsbzXybmsqService 
{
    /**
     * 查询学员报名申请
     * 
     * @param id 学员报名申请ID
     * @return 学员报名申请
     */
    public TsbzXybmsq selectTsbzXybmsqById(Long id);

    /**
     * 查询学员报名申请列表
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 学员报名申请集合
     */
    public List<TsbzXybmsq> selectTsbzXybmsqList(TsbzXybmsq tsbzXybmsq);

    // 查询已经报名人数
    public int selectCountYibaoming(TsbzXybmsq tsbzXybmsq);

    /**
     * 新增学员报名申请
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 结果
     */
    public int insertTsbzXybmsq(TsbzXybmsq tsbzXybmsq);

    /**
     * 修改学员报名申请
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 结果
     */
    public int updateTsbzXybmsq(TsbzXybmsq tsbzXybmsq);

    /**
     * 批量删除学员报名申请
     * 
     * @param ids 需要删除的学员报名申请ID
     * @return 结果
     */
    public int deleteTsbzXybmsqByIds(Long[] ids);

    /**
     * 删除学员报名申请信息
     * 
     * @param id 学员报名申请ID
     * @return 结果
     */
    public int deleteTsbzXybmsqById(Long id);
}
