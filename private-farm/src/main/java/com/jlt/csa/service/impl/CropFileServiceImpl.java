package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CropFileMapper;
import com.jlt.csa.domain.CropFile;
import com.jlt.csa.service.ICropFileService;

/**
 * 作物档案Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-03-31
 */
@Service
public class CropFileServiceImpl implements ICropFileService 
{
    @Autowired
    private CropFileMapper cropFileMapper;

    /**
     * 查询作物档案
     * 
     * @param cropId 作物档案主键
     * @return 作物档案
     */
    @Override
    public CropFile selectCropFileByCropId(Long cropId)
    {
        return cropFileMapper.selectCropFileByCropId(cropId);
    }

    /**
     * 查询作物档案列表
     * 
     * @param cropFile 作物档案
     * @return 作物档案
     */
    @Override
    public List<CropFile> selectCropFileList(CropFile cropFile)
    {
        return cropFileMapper.selectCropFileList(cropFile);
    }

    /**
     * 新增作物档案
     * 
     * @param cropFile 作物档案
     * @return 结果
     */
    @Override
    public int insertCropFile(CropFile cropFile)
    {
        return cropFileMapper.insertCropFile(cropFile);
    }

    /**
     * 修改作物档案
     * 
     * @param cropFile 作物档案
     * @return 结果
     */
    @Override
    public int updateCropFile(CropFile cropFile)
    {
        return cropFileMapper.updateCropFile(cropFile);
    }

    /**
     * 批量删除作物档案
     * 
     * @param cropIds 需要删除的作物档案主键
     * @return 结果
     */
    @Override
    public int deleteCropFileByCropIds(Long[] cropIds)
    {
        return cropFileMapper.deleteCropFileByCropIds(cropIds);
    }

    /**
     * 删除作物档案信息
     * 
     * @param cropId 作物档案主键
     * @return 结果
     */
    @Override
    public int deleteCropFileByCropId(Long cropId)
    {
        return cropFileMapper.deleteCropFileByCropId(cropId);
    }
}
