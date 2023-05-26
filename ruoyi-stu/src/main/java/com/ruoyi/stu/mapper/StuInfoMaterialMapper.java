package com.ruoyi.stu.mapper;

import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.apache.ibatis.annotations.Param;

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
     * @param form 毕业实践
     * @return 提交材料参数集合
     */
    List<StuInfoMaterial> selectStuMaterialList(@Param("biye") BiyeForm form);


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
     * @param biyeForm 毕业实践
     * @return 结果
     */
     int updateStuMaterial(BiyeForm biyeForm);

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

    StuInfoMaterial selectStuInfMaterialoById(Long id);

    void updateImgUrl(BiyeForm form);

    int batchAddBiye(List<BiyeForm> list);

    int selectStuInfMaterialoByUserId(Long userId);
    /**
     * 查询企业实习材料以及照片以及视频提交情况总数量
     */
    int selectStuFinishedMaterials(@Param("clsYear")String clsYear,@Param("clsYear2")String clsYear2);
}
