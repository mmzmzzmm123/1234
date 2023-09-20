package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserCleanerMapper;
import com.ruoyi.office.domain.TWxUserCleaner;
import com.ruoyi.office.service.ITWxUserCleanerService;

/**
 * 保洁员信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-20
 */
@Service
public class TWxUserCleanerServiceImpl extends ServiceImpl<TWxUserCleanerMapper, TWxUserCleaner> implements ITWxUserCleanerService
{
    @Autowired
    private TWxUserCleanerMapper tWxUserCleanerMapper;

    /**
     * 查询保洁员信息
     * 
     * @param id 保洁员信息主键
     * @return 保洁员信息
     */
    @Override
    public TWxUserCleaner selectTWxUserCleanerById(Long id)
    {
        return tWxUserCleanerMapper.selectTWxUserCleanerById(id);
    }

    /**
     * 查询保洁员信息列表
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 保洁员信息
     */
    @Override
    public List<TWxUserCleaner> selectTWxUserCleanerList(TWxUserCleaner tWxUserCleaner)
    {
        return tWxUserCleanerMapper.selectTWxUserCleanerList(tWxUserCleaner);
    }

    /**
     * 新增保洁员信息
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    @Override
    public int insertTWxUserCleaner(TWxUserCleaner tWxUserCleaner)
    {
        tWxUserCleaner.setCreateTime(DateUtils.getNowDate());
        return tWxUserCleanerMapper.insertTWxUserCleaner(tWxUserCleaner);
    }

    /**
     * 修改保洁员信息
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    @Override
    public int updateTWxUserCleaner(TWxUserCleaner tWxUserCleaner)
    {
        tWxUserCleaner.setUpdateTime(DateUtils.getNowDate());
        return tWxUserCleanerMapper.updateTWxUserCleaner(tWxUserCleaner);
    }

    /**
     * 批量删除保洁员信息
     * 
     * @param ids 需要删除的保洁员信息主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserCleanerByIds(Long[] ids)
    {
        return tWxUserCleanerMapper.deleteTWxUserCleanerByIds(ids);
    }

    /**
     * 删除保洁员信息信息
     * 
     * @param id 保洁员信息主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserCleanerById(Long id)
    {
        return tWxUserCleanerMapper.deleteTWxUserCleanerById(id);
    }
}
