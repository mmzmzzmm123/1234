package com.ruoyi.stu.service;

import java.util.List;
import com.ruoyi.stu.domain.StuMaterial;

/**
 * 提交材料参数Service接口
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
public interface IStuMaterialService 
{
    /**
     * 查询提交材料参数
     * 
     * @param id 提交材料参数主键
     * @return 提交材料参数
     */
    public StuMaterial selectStuMaterialById(Long id);

    /**
     * 查询提交材料参数列表
     * 
     * @param stuMaterial 提交材料参数
     * @return 提交材料参数集合
     */
    public List<StuMaterial> selectStuMaterialList(StuMaterial stuMaterial);

    /**
     * 新增提交材料参数
     * 
     * @param stuMaterial 提交材料参数
     * @return 结果
     */
    public int insertStuMaterial(StuMaterial stuMaterial);

    /**
     * 修改提交材料参数
     * 
     * @param stuMaterial 提交材料参数
     * @return 结果
     */
    public int updateStuMaterial(StuMaterial stuMaterial);

    /**
     * 批量删除提交材料参数
     * 
     * @param ids 需要删除的提交材料参数主键集合
     * @return 结果
     */
    public int deleteStuMaterialByIds(Long[] ids);

    /**
     * 删除提交材料参数信息
     * 
     * @param id 提交材料参数主键
     * @return 结果
     */
    public int deleteStuMaterialById(Long id);

}
