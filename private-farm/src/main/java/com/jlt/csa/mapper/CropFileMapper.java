package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.CropFile;
import com.jlt.csa.domain.CropPhase;

/**
 * 作物档案Mapper接口
 * 
 * @author 郏磊涛
 * @date 2022-03-30
 */
public interface CropFileMapper 
{
    /**
     * 查询作物档案
     * 
     * @param cropId 作物档案主键
     * @return 作物档案
     */
    public CropFile selectCropFileByCropId(Long cropId);

    /**
     * 查询作物档案列表
     * 
     * @param cropFile 作物档案
     * @return 作物档案集合
     */
    public List<CropFile> selectCropFileList(CropFile cropFile);

    /**
     * 新增作物档案
     * 
     * @param cropFile 作物档案
     * @return 结果
     */
    public int insertCropFile(CropFile cropFile);

    /**
     * 修改作物档案
     * 
     * @param cropFile 作物档案
     * @return 结果
     */
    public int updateCropFile(CropFile cropFile);

    /**
     * 删除作物档案
     * 
     * @param cropId 作物档案主键
     * @return 结果
     */
    public int deleteCropFileByCropId(Long cropId);

    /**
     * 批量删除作物档案
     * 
     * @param cropIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCropFileByCropIds(Long[] cropIds);

    /**
     * 批量删除作物生长阶段
     * 
     * @param cropIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCropPhaseByCropIds(Long[] cropIds);
    
    /**
     * 批量新增作物生长阶段
     * 
     * @param cropPhaseList 作物生长阶段列表
     * @return 结果
     */
    public int batchCropPhase(List<CropPhase> cropPhaseList);
    

    /**
     * 通过作物档案主键删除作物生长阶段信息
     * 
     * @param cropId 作物档案ID
     * @return 结果
     */
    public int deleteCropPhaseByCropId(Long cropId);
}
