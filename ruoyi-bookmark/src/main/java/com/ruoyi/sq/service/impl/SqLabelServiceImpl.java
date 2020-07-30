package com.ruoyi.sq.service.impl;

import com.ruoyi.sq.domain.SqLabel;
import com.ruoyi.sq.service.ISqLabelService;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.sq.mapper.SqLabelMapper;

/**
 * 书签_标签Service业务层处理
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@Service
public class SqLabelServiceImpl implements ISqLabelService
{
    @Autowired
    private SqLabelMapper sqLabelMapper;

    /**
     * 查询书签_标签
     * 
     * @param id 书签_标签ID
     * @return 书签_标签
     */
    @Override
    public SqLabel selectSqLabelById(Long id)
    {
        return sqLabelMapper.selectSqLabelById(id);
    }

    /**
     * 查询书签_标签列表
     * 
     * @param sqLabel 书签_标签
     * @return 书签_标签
     */
    @Override
    public List<SqLabel> selectSqLabelList(SqLabel sqLabel)
    {
        return sqLabelMapper.selectSqLabelList(sqLabel);
    }

    /**
     * 新增书签_标签
     * 
     * @param sqLabel 书签_标签
     * @return 结果
     */
    @Override
    public int insertSqLabel(SqLabel sqLabel)
    {
        sqLabel.setCreateTime(DateUtils.getNowDate());
        return sqLabelMapper.insertSqLabel(sqLabel);
    }

    /**
     * 修改书签_标签
     * 
     * @param sqLabel 书签_标签
     * @return 结果
     */
    @Override
    public int updateSqLabel(SqLabel sqLabel)
    {
        return sqLabelMapper.updateSqLabel(sqLabel);
    }

    /**
     * 批量删除书签_标签
     * 
     * @param ids 需要删除的书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqLabelByIds(Long[] ids)
    {
        return sqLabelMapper.deleteSqLabelByIds(ids);
    }

    /**
     * 删除书签_标签信息
     * 
     * @param id 书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqLabelById(Long id)
    {
        return sqLabelMapper.deleteSqLabelById(id);
    }
}
