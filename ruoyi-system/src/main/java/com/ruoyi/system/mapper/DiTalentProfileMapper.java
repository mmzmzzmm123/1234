package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DiTalentProfile;

/**
 * 人才档案管理Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
public interface DiTalentProfileMapper 
{
    /**
     * 查询人才档案管理
     * 
     * @param id 人才档案管理主键
     * @return 人才档案管理
     */
    public DiTalentProfile selectDiTalentProfileById(Long id);

    /**
     * 查询人才档案管理列表
     * 
     * @param diTalentProfile 人才档案管理
     * @return 人才档案管理集合
     */
    public List<DiTalentProfile> selectDiTalentProfileList(DiTalentProfile diTalentProfile);

    /**
     * 新增人才档案管理
     * 
     * @param diTalentProfile 人才档案管理
     * @return 结果
     */
    public int insertDiTalentProfile(DiTalentProfile diTalentProfile);

    /**
     * 修改人才档案管理
     * 
     * @param diTalentProfile 人才档案管理
     * @return 结果
     */
    public int updateDiTalentProfile(DiTalentProfile diTalentProfile);

    /**
     * 删除人才档案管理
     * 
     * @param id 人才档案管理主键
     * @return 结果
     */
    public int deleteDiTalentProfileById(Long id);

    /**
     * 批量删除人才档案管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDiTalentProfileByIds(Long[] ids);
}