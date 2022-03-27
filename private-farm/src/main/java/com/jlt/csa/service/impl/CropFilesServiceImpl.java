package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CropFilesMapper;
import com.jlt.csa.domain.CropFiles;
import com.jlt.csa.service.ICropFilesService;

/**
 * 作物档案Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-25
 */
@Service
public class CropFilesServiceImpl implements ICropFilesService 
{
    @Autowired
    private CropFilesMapper cropFilesMapper;

    /**
     * 查询作物档案
     * 
     * @param id 作物档案主键
     * @return 作物档案
     */
    @Override
    public CropFiles selectCropFilesById(Long id)
    {
        return cropFilesMapper.selectCropFilesById(id);
    }

    /**
     * 查询作物档案列表
     * 
     * @param cropFiles 作物档案
     * @return 作物档案
     */
    @Override
    public List<CropFiles> selectCropFilesList(CropFiles cropFiles)
    {
        return cropFilesMapper.selectCropFilesList(cropFiles);
    }

    /**
     * 新增作物档案
     * 
     * @param cropFiles 作物档案
     * @return 结果
     */
    @Override
    public int insertCropFiles(CropFiles cropFiles)
    {
        cropFiles.setCreateTime(DateUtils.getNowDate());
        return cropFilesMapper.insertCropFiles(cropFiles);
    }

    /**
     * 修改作物档案
     * 
     * @param cropFiles 作物档案
     * @return 结果
     */
    @Override
    public int updateCropFiles(CropFiles cropFiles)
    {
        cropFiles.setUpdateTime(DateUtils.getNowDate());
        return cropFilesMapper.updateCropFiles(cropFiles);
    }

    /**
     * 批量删除作物档案
     * 
     * @param ids 需要删除的作物档案主键
     * @return 结果
     */
    @Override
    public int deleteCropFilesByIds(Long[] ids)
    {
        return cropFilesMapper.deleteCropFilesByIds(ids);
    }

    /**
     * 删除作物档案信息
     * 
     * @param id 作物档案主键
     * @return 结果
     */
    @Override
    public int deleteCropFilesById(Long id)
    {
        return cropFilesMapper.deleteCropFilesById(id);
    }
}
