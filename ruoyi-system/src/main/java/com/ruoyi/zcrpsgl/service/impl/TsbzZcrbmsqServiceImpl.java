package com.ruoyi.zcrpsgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zcrpsgl.mapper.TsbzZcrbmsqMapper;
import com.ruoyi.zcrpsgl.domain.TsbzZcrbmsq;
import com.ruoyi.zcrpsgl.service.ITsbzZcrbmsqService;

/**
 * 主持人报名申请Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-15
 */
@Service
public class TsbzZcrbmsqServiceImpl implements ITsbzZcrbmsqService 
{
    @Autowired
    private TsbzZcrbmsqMapper tsbzZcrbmsqMapper;

    /**
     * 查询主持人报名申请
     * 
     * @param id 主持人报名申请ID
     * @return 主持人报名申请
     */
    @Override
    public TsbzZcrbmsq selectTsbzZcrbmsqById(Long id)
    {
        return tsbzZcrbmsqMapper.selectTsbzZcrbmsqById(id);
    }

    /**
     * 查询主持人报名申请列表
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 主持人报名申请
     */
    @Override
    public List<TsbzZcrbmsq> selectTsbzZcrbmsqList(TsbzZcrbmsq tsbzZcrbmsq)
    {
        return tsbzZcrbmsqMapper.selectTsbzZcrbmsqList(tsbzZcrbmsq);
    }

    /**
     * 新增主持人报名申请
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 结果
     */
    @Override
    public int insertTsbzZcrbmsq(TsbzZcrbmsq tsbzZcrbmsq)
    {
        tsbzZcrbmsq.setCreateTime(DateUtils.getNowDate());
        return tsbzZcrbmsqMapper.insertTsbzZcrbmsq(tsbzZcrbmsq);
    }

    /**
     * 修改主持人报名申请
     * 
     * @param tsbzZcrbmsq 主持人报名申请
     * @return 结果
     */
    @Override
    public int updateTsbzZcrbmsq(TsbzZcrbmsq tsbzZcrbmsq)
    {
        return tsbzZcrbmsqMapper.updateTsbzZcrbmsq(tsbzZcrbmsq);
    }

    /**
     * 批量删除主持人报名申请
     * 
     * @param ids 需要删除的主持人报名申请ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrbmsqByIds(Long[] ids)
    {
        return tsbzZcrbmsqMapper.deleteTsbzZcrbmsqByIds(ids);
    }

    /**
     * 删除主持人报名申请信息
     * 
     * @param id 主持人报名申请ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrbmsqById(Long id)
    {
        return tsbzZcrbmsqMapper.deleteTsbzZcrbmsqById(id);
    }
}
