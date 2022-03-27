package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CropPhaseMapper;
import com.jlt.csa.domain.CropPhase;
import com.jlt.csa.service.ICropPhaseService;

/**
 * 作物生长阶段Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@Service
public class CropPhaseServiceImpl implements ICropPhaseService 
{
    @Autowired
    private CropPhaseMapper cropPhaseMapper;

    /**
     * 查询作物生长阶段
     * 
     * @param id 作物生长阶段主键
     * @return 作物生长阶段
     */
    @Override
    public CropPhase selectCropPhaseById(Long id)
    {
        return cropPhaseMapper.selectCropPhaseById(id);
    }

    /**
     * 查询作物生长阶段列表
     * 
     * @param cropPhase 作物生长阶段
     * @return 作物生长阶段
     */
    @Override
    public List<CropPhase> selectCropPhaseList(CropPhase cropPhase)
    {
        return cropPhaseMapper.selectCropPhaseList(cropPhase);
    }

    /**
     * 新增作物生长阶段
     * 
     * @param cropPhase 作物生长阶段
     * @return 结果
     */
    @Override
    public int insertCropPhase(CropPhase cropPhase)
    {
        cropPhase.setCreateTime(DateUtils.getNowDate());
        return cropPhaseMapper.insertCropPhase(cropPhase);
    }

    /**
     * 修改作物生长阶段
     * 
     * @param cropPhase 作物生长阶段
     * @return 结果
     */
    @Override
    public int updateCropPhase(CropPhase cropPhase)
    {
        cropPhase.setUpdateTime(DateUtils.getNowDate());
        return cropPhaseMapper.updateCropPhase(cropPhase);
    }

    /**
     * 批量删除作物生长阶段
     * 
     * @param ids 需要删除的作物生长阶段主键
     * @return 结果
     */
    @Override
    public int deleteCropPhaseByIds(Long[] ids)
    {
        return cropPhaseMapper.deleteCropPhaseByIds(ids);
    }

    /**
     * 删除作物生长阶段信息
     * 
     * @param id 作物生长阶段主键
     * @return 结果
     */
    @Override
    public int deleteCropPhaseById(Long id)
    {
        return cropPhaseMapper.deleteCropPhaseById(id);
    }
}
