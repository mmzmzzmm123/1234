package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.CropPhase;

/**
 * 作物生长阶段Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
public interface CropPhaseMapper 
{
    /**
     * 查询作物生长阶段
     * 
     * @param id 作物生长阶段主键
     * @return 作物生长阶段
     */
    public CropPhase selectCropPhaseById(Long id);

    /**
     * 查询作物生长阶段列表
     * 
     * @param cropPhase 作物生长阶段
     * @return 作物生长阶段集合
     */
    public List<CropPhase> selectCropPhaseList(CropPhase cropPhase);

    /**
     * 新增作物生长阶段
     * 
     * @param cropPhase 作物生长阶段
     * @return 结果
     */
    public int insertCropPhase(CropPhase cropPhase);

    /**
     * 修改作物生长阶段
     * 
     * @param cropPhase 作物生长阶段
     * @return 结果
     */
    public int updateCropPhase(CropPhase cropPhase);

    /**
     * 删除作物生长阶段
     * 
     * @param id 作物生长阶段主键
     * @return 结果
     */
    public int deleteCropPhaseById(Long id);

    /**
     * 批量删除作物生长阶段
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCropPhaseByIds(Long[] ids);
}
