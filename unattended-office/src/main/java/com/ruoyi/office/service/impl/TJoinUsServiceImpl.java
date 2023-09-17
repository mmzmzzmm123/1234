package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TJoinUsMapper;
import com.ruoyi.office.domain.TJoinUs;
import com.ruoyi.office.service.ITJoinUsService;

/**
 * 我要加盟Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-17
 */
@Service
public class TJoinUsServiceImpl extends ServiceImpl<TJoinUsMapper, TJoinUs> implements ITJoinUsService
{
    @Autowired
    private TJoinUsMapper tJoinUsMapper;

    /**
     * 查询我要加盟
     * 
     * @param id 我要加盟主键
     * @return 我要加盟
     */
    @Override
    public TJoinUs selectTJoinUsById(Long id)
    {
        return tJoinUsMapper.selectTJoinUsById(id);
    }

    /**
     * 查询我要加盟列表
     * 
     * @param tJoinUs 我要加盟
     * @return 我要加盟
     */
    @Override
    public List<TJoinUs> selectTJoinUsList(TJoinUs tJoinUs)
    {
        return tJoinUsMapper.selectTJoinUsList(tJoinUs);
    }

    /**
     * 新增我要加盟
     * 
     * @param tJoinUs 我要加盟
     * @return 结果
     */
    @Override
    public int insertTJoinUs(TJoinUs tJoinUs)
    {
        tJoinUs.setCreateTime(DateUtils.getNowDate());
        return tJoinUsMapper.insertTJoinUs(tJoinUs);
    }

    /**
     * 修改我要加盟
     * 
     * @param tJoinUs 我要加盟
     * @return 结果
     */
    @Override
    public int updateTJoinUs(TJoinUs tJoinUs)
    {
        tJoinUs.setUpdateTime(DateUtils.getNowDate());
        return tJoinUsMapper.updateTJoinUs(tJoinUs);
    }

    /**
     * 批量删除我要加盟
     * 
     * @param ids 需要删除的我要加盟主键
     * @return 结果
     */
    @Override
    public int deleteTJoinUsByIds(Long[] ids)
    {
        return tJoinUsMapper.deleteTJoinUsByIds(ids);
    }

    /**
     * 删除我要加盟信息
     * 
     * @param id 我要加盟主键
     * @return 结果
     */
    @Override
    public int deleteTJoinUsById(Long id)
    {
        return tJoinUsMapper.deleteTJoinUsById(id);
    }
}
