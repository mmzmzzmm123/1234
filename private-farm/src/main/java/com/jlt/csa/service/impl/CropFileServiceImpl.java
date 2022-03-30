package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.jlt.csa.domain.CropPhase;
import com.jlt.csa.mapper.CropFileMapper;
import com.jlt.csa.domain.CropFile;
import com.jlt.csa.service.ICropFileService;

/**
 * 作物档案Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-03-30
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
    @Transactional
    @Override
    public int insertCropFile(CropFile cropFile)
    {
        cropFile.setCreateTime(DateUtils.getNowDate());
        int rows = cropFileMapper.insertCropFile(cropFile);
        insertCropPhase(cropFile);
        return rows;
    }

    /**
     * 修改作物档案
     * 
     * @param cropFile 作物档案
     * @return 结果
     */
    @Transactional
    @Override
    public int updateCropFile(CropFile cropFile)
    {
        cropFile.setUpdateTime(DateUtils.getNowDate());
        cropFileMapper.deleteCropPhaseByCropId(cropFile.getCropId());
        insertCropPhase(cropFile);
        return cropFileMapper.updateCropFile(cropFile);
    }

    /**
     * 批量删除作物档案
     * 
     * @param cropIds 需要删除的作物档案主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCropFileByCropIds(Long[] cropIds)
    {
        cropFileMapper.deleteCropPhaseByCropIds(cropIds);
        return cropFileMapper.deleteCropFileByCropIds(cropIds);
    }

    /**
     * 删除作物档案信息
     * 
     * @param cropId 作物档案主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCropFileByCropId(Long cropId)
    {
        cropFileMapper.deleteCropPhaseByCropId(cropId);
        return cropFileMapper.deleteCropFileByCropId(cropId);
    }

    /**
     * 新增作物生长阶段信息
     * 
     * @param cropFile 作物档案对象
     */
    public void insertCropPhase(CropFile cropFile)
    {
        List<CropPhase> cropPhaseList = cropFile.getCropPhaseList();
        Long cropId = cropFile.getCropId();
        if (StringUtils.isNotNull(cropPhaseList))
        {
            List<CropPhase> list = new ArrayList<CropPhase>();
            for (CropPhase cropPhase : cropPhaseList)
            {
                cropPhase.setCropId(cropId);
                list.add(cropPhase);
            }
            if (list.size() > 0)
            {
                cropFileMapper.batchCropPhase(list);
            }
        }
    }
}
