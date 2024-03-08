package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SourceDynamicRelativeMapper;
import com.ruoyi.system.domain.SourceDynamicRelative;
import com.ruoyi.system.service.ISourceDynamicRelativeService;

/**
 * 车源&动态关联表Service业务层处理
 * 
 * @author carol
 * @date 2024-03-06
 */
@Service
public class SourceDynamicRelativeServiceImpl implements ISourceDynamicRelativeService 
{
    @Autowired
    private SourceDynamicRelativeMapper sourceDynamicRelativeMapper;

    /**
     * 查询车源&动态关联表
     * 
     * @param id 车源&动态关联表主键
     * @return 车源&动态关联表
     */
    @Override
    public SourceDynamicRelative selectSourceDynamicRelativeById(Long id)
    {
        return sourceDynamicRelativeMapper.selectSourceDynamicRelativeById(id);
    }

    /**
     * 查询车源&动态关联表列表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 车源&动态关联表
     */
    @Override
    public List<SourceDynamicRelative> selectSourceDynamicRelativeList(SourceDynamicRelative sourceDynamicRelative)
    {
        return sourceDynamicRelativeMapper.selectSourceDynamicRelativeList(sourceDynamicRelative);
    }

    /**
     * 新增车源&动态关联表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 结果
     */
    @Override
    public int insertSourceDynamicRelative(SourceDynamicRelative sourceDynamicRelative)
    {
        sourceDynamicRelative.setCreateTime(DateUtils.getNowDate());
        return sourceDynamicRelativeMapper.insertSourceDynamicRelative(sourceDynamicRelative);
    }

    /**
     * 修改车源&动态关联表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 结果
     */
    @Override
    public int updateSourceDynamicRelative(SourceDynamicRelative sourceDynamicRelative)
    {
        sourceDynamicRelative.setUpdateTime(DateUtils.getNowDate());
        return sourceDynamicRelativeMapper.updateSourceDynamicRelative(sourceDynamicRelative);
    }

    /**
     * 批量删除车源&动态关联表
     * 
     * @param ids 需要删除的车源&动态关联表主键
     * @return 结果
     */
    @Override
    public int deleteSourceDynamicRelativeByIds(Long[] ids)
    {
        return sourceDynamicRelativeMapper.deleteSourceDynamicRelativeByIds(ids);
    }

    /**
     * 删除车源&动态关联表信息
     * 
     * @param id 车源&动态关联表主键
     * @return 结果
     */
    @Override
    public int deleteSourceDynamicRelativeById(Long id)
    {
        return sourceDynamicRelativeMapper.deleteSourceDynamicRelativeById(id);
    }
}
