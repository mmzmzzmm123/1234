package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DiIntegrityArchivesMapper;
import com.ruoyi.system.domain.DiIntegrityArchives;
import com.ruoyi.system.service.IDiIntegrityArchivesService;

/**
 * 廉政档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@Service
public class DiIntegrityArchivesServiceImpl implements IDiIntegrityArchivesService 
{
    @Autowired
    private DiIntegrityArchivesMapper diIntegrityArchivesMapper;

    /**
     * 查询廉政档案
     * 
     * @param id 廉政档案主键
     * @return 廉政档案
     */
    @Override
    public DiIntegrityArchives selectDiIntegrityArchivesById(Long id)
    {
        return diIntegrityArchivesMapper.selectDiIntegrityArchivesById(id);
    }

    /**
     * 查询廉政档案列表
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 廉政档案
     */
    @Override
    public List<DiIntegrityArchives> selectDiIntegrityArchivesList(DiIntegrityArchives diIntegrityArchives)
    {
        return diIntegrityArchivesMapper.selectDiIntegrityArchivesList(diIntegrityArchives);
    }

    /**
     * 新增廉政档案
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 结果
     */
    @Override
    public int insertDiIntegrityArchives(DiIntegrityArchives diIntegrityArchives)
    {
        return diIntegrityArchivesMapper.insertDiIntegrityArchives(diIntegrityArchives);
    }

    /**
     * 修改廉政档案
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 结果
     */
    @Override
    public int updateDiIntegrityArchives(DiIntegrityArchives diIntegrityArchives)
    {
        return diIntegrityArchivesMapper.updateDiIntegrityArchives(diIntegrityArchives);
    }

    /**
     * 批量删除廉政档案
     * 
     * @param ids 需要删除的廉政档案主键
     * @return 结果
     */
    @Override
    public int deleteDiIntegrityArchivesByIds(Long[] ids)
    {
        return diIntegrityArchivesMapper.deleteDiIntegrityArchivesByIds(ids);
    }

    /**
     * 删除廉政档案信息
     * 
     * @param id 廉政档案主键
     * @return 结果
     */
    @Override
    public int deleteDiIntegrityArchivesById(Long id)
    {
        return diIntegrityArchivesMapper.deleteDiIntegrityArchivesById(id);
    }
}
