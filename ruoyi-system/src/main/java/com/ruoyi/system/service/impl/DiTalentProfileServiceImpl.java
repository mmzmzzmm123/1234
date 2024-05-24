package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DiTalentProfileMapper;
import com.ruoyi.system.domain.DiTalentProfile;
import com.ruoyi.system.service.IDiTalentProfileService;

/**
 * 人才档案管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@Service
public class DiTalentProfileServiceImpl implements IDiTalentProfileService 
{
    @Autowired
    private DiTalentProfileMapper diTalentProfileMapper;

    /**
     * 查询人才档案管理
     * 
     * @param id 人才档案管理主键
     * @return 人才档案管理
     */
    @Override
    public DiTalentProfile selectDiTalentProfileById(Long id)
    {
        return diTalentProfileMapper.selectDiTalentProfileById(id);
    }

    /**
     * 查询人才档案管理列表
     * 
     * @param diTalentProfile 人才档案管理
     * @return 人才档案管理
     */
    @Override
    public List<DiTalentProfile> selectDiTalentProfileList(DiTalentProfile diTalentProfile)
    {
        return diTalentProfileMapper.selectDiTalentProfileList(diTalentProfile);
    }

    /**
     * 新增人才档案管理
     * 
     * @param diTalentProfile 人才档案管理
     * @return 结果
     */
    @Override
    public int insertDiTalentProfile(DiTalentProfile diTalentProfile)
    {
        return diTalentProfileMapper.insertDiTalentProfile(diTalentProfile);
    }

    /**
     * 修改人才档案管理
     * 
     * @param diTalentProfile 人才档案管理
     * @return 结果
     */
    @Override
    public int updateDiTalentProfile(DiTalentProfile diTalentProfile)
    {
        return diTalentProfileMapper.updateDiTalentProfile(diTalentProfile);
    }

    /**
     * 批量删除人才档案管理
     * 
     * @param ids 需要删除的人才档案管理主键
     * @return 结果
     */
    @Override
    public int deleteDiTalentProfileByIds(Long[] ids)
    {
        return diTalentProfileMapper.deleteDiTalentProfileByIds(ids);
    }

    /**
     * 删除人才档案管理信息
     * 
     * @param id 人才档案管理主键
     * @return 结果
     */
    @Override
    public int deleteDiTalentProfileById(Long id)
    {
        return diTalentProfileMapper.deleteDiTalentProfileById(id);
    }
}
