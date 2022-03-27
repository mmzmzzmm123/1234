package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.CropFiles;

/**
 * 作物档案Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-25
 */
public interface ICropFilesService 
{
    /**
     * 查询作物档案
     * 
     * @param id 作物档案主键
     * @return 作物档案
     */
    public CropFiles selectCropFilesById(Long id);

    /**
     * 查询作物档案列表
     * 
     * @param cropFiles 作物档案
     * @return 作物档案集合
     */
    public List<CropFiles> selectCropFilesList(CropFiles cropFiles);

    /**
     * 新增作物档案
     * 
     * @param cropFiles 作物档案
     * @return 结果
     */
    public int insertCropFiles(CropFiles cropFiles);

    /**
     * 修改作物档案
     * 
     * @param cropFiles 作物档案
     * @return 结果
     */
    public int updateCropFiles(CropFiles cropFiles);

    /**
     * 批量删除作物档案
     * 
     * @param ids 需要删除的作物档案主键集合
     * @return 结果
     */
    public int deleteCropFilesByIds(Long[] ids);

    /**
     * 删除作物档案信息
     * 
     * @param id 作物档案主键
     * @return 结果
     */
    public int deleteCropFilesById(Long id);
}
