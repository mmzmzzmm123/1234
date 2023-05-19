package com.ruoyi.stu.mapper;

import com.ruoyi.stu.vo.StuInfoMaterial;

import java.util.List;

/**
 * 毕业Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
 public interface StuInfoMaterialMapper
{

    /**
     * 查询提交材料参数列表
     * 
     * @param stuInfoMaterial 毕业实践
     * @return 提交材料参数集合
     */
     List<StuInfoMaterial> selectStuMaterialList(StuInfoMaterial stuInfoMaterial);

    /**
     * 新增提交材料参数
     * 
     * @param stuInfoMaterial 毕业实践
     * @return 结果
     */
     int insertStuMaterial(StuInfoMaterial stuInfoMaterial);

    /**
     * 修改提交材料参数
     * 
     * @param stuInfoMaterial 毕业实践
     * @return 结果
     */
     int updateStuMaterial(StuInfoMaterial stuInfoMaterial);

    /**
     * 删除提交材料参数
     * 
     * @param id 提交材料参数主键
     * @return 结果
     */
     int deleteStuInfoMaterialById(Integer id);

    /**
     * 批量删除提交材料参数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
     int deleteStuInfoMaterialByIds(Long[] ids);
}
