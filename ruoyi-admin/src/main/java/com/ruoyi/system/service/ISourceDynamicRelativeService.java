package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SourceDynamicRelative;

/**
 * 车源&动态关联表Service接口
 * 
 * @author carol
 * @date 2024-03-06
 */
public interface ISourceDynamicRelativeService 
{
    /**
     * 查询车源&动态关联表
     * 
     * @param id 车源&动态关联表主键
     * @return 车源&动态关联表
     */
    public SourceDynamicRelative selectSourceDynamicRelativeById(Long id);

    /**
     * 查询车源&动态关联表列表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 车源&动态关联表集合
     */
    public List<SourceDynamicRelative> selectSourceDynamicRelativeList(SourceDynamicRelative sourceDynamicRelative);

    /**
     * 新增车源&动态关联表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 结果
     */
    public int insertSourceDynamicRelative(SourceDynamicRelative sourceDynamicRelative);

    /**
     * 修改车源&动态关联表
     * 
     * @param sourceDynamicRelative 车源&动态关联表
     * @return 结果
     */
    public int updateSourceDynamicRelative(SourceDynamicRelative sourceDynamicRelative);

    /**
     * 批量删除车源&动态关联表
     * 
     * @param ids 需要删除的车源&动态关联表主键集合
     * @return 结果
     */
    public int deleteSourceDynamicRelativeByIds(Long[] ids);

    /**
     * 删除车源&动态关联表信息
     * 
     * @param id 车源&动态关联表主键
     * @return 结果
     */
    public int deleteSourceDynamicRelativeById(Long id);
}
