package com.ruoyi.xypsgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xypsgl.mapper.TsbzXybmsqMapper;
import com.ruoyi.xypsgl.domain.TsbzXybmsq;
import com.ruoyi.xypsgl.service.ITsbzXybmsqService;

/**
 * 学员报名申请Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-23
 */
@Service
public class TsbzXybmsqServiceImpl implements ITsbzXybmsqService 
{
    @Autowired
    private TsbzXybmsqMapper tsbzXybmsqMapper;

    /**
     * 查询学员报名申请
     * 
     * @param id 学员报名申请ID
     * @return 学员报名申请
     */
    @Override
    public TsbzXybmsq selectTsbzXybmsqById(Long id)
    {
        return tsbzXybmsqMapper.selectTsbzXybmsqById(id);
    }

    /**
     * 查询学员报名申请列表
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 学员报名申请
     */
    @Override
    public List<TsbzXybmsq> selectTsbzXybmsqList(TsbzXybmsq tsbzXybmsq)
    {
        return tsbzXybmsqMapper.selectTsbzXybmsqList(tsbzXybmsq);
    }

    // 查询学员学校审核列表
    @Override
    public List<TsbzXybmsq> selectTsbzXybmsqListXxsh(TsbzXybmsq tsbzXybmsq) {

        return tsbzXybmsqMapper.selectTsbzXybmsqList(tsbzXybmsq);
    }

    // 查询已报名人数
    @Override
    public TsbzXybmsq selectCountYibaoming(TsbzXybmsq tsbzXybmsq) {
        return tsbzXybmsqMapper.selectCountYibaoming(tsbzXybmsq);
    }

    /**
     * 新增学员报名申请
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 结果
     */
    @Override
    public int insertTsbzXybmsq(TsbzXybmsq tsbzXybmsq)
    {
        tsbzXybmsq.setCreateTime(DateUtils.getNowDate());
        return tsbzXybmsqMapper.insertTsbzXybmsq(tsbzXybmsq);
    }

    /**
     * 修改学员报名申请
     * 
     * @param tsbzXybmsq 学员报名申请
     * @return 结果
     */
    @Override
    public int updateTsbzXybmsq(TsbzXybmsq tsbzXybmsq)
    {
        return tsbzXybmsqMapper.updateTsbzXybmsq(tsbzXybmsq);
    }

    /**
     * 批量删除学员报名申请
     * 
     * @param ids 需要删除的学员报名申请ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXybmsqByIds(Long[] ids)
    {
        return tsbzXybmsqMapper.deleteTsbzXybmsqByIds(ids);
    }

    /**
     * 删除学员报名申请信息
     * 
     * @param id 学员报名申请ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXybmsqById(Long id)
    {
        return tsbzXybmsqMapper.deleteTsbzXybmsqById(id);
    }
}
