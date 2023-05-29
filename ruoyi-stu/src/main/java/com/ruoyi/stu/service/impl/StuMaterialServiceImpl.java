package com.ruoyi.stu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stu.mapper.StuMaterialMapper;
import com.ruoyi.stu.domain.StuMaterial;
import com.ruoyi.stu.service.IStuMaterialService;

/**
 * 提交材料参数Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@Service
public class StuMaterialServiceImpl implements IStuMaterialService 
{
    @Autowired
    private StuMaterialMapper stuMaterialMapper;

    /**
     * 查询提交材料参数
     * 
     * @param id 提交材料参数主键
     * @return 提交材料参数
     */
    @Override
    public StuMaterial selectStuMaterialById(Long id)
    {
        return stuMaterialMapper.selectStuMaterialById(id);
    }

    /**
     * 查询提交材料参数列表
     * 
     * @param stuMaterial 提交材料参数
     * @return 提交材料参数
     */
    @Override
    public List<StuMaterial> selectStuMaterialList(StuMaterial stuMaterial)
    {
        return stuMaterialMapper.selectStuMaterialList(stuMaterial);
    }

    /**
     * 新增提交材料参数
     * 
     * @param stuMaterial 提交材料参数
     * @return 结果
     */
    @Override
    public int insertStuMaterial(StuMaterial stuMaterial)
    {
        return stuMaterialMapper.insertStuMaterial(stuMaterial);
    }

    /**
     * 修改提交材料参数
     * 
     * @param stuMaterial 提交材料参数
     * @return 结果
     */
    @Override
    public int updateStuMaterial(StuMaterial stuMaterial)
    {
        return stuMaterialMapper.updateStuMaterial(stuMaterial);
    }

    /**
     * 批量删除提交材料参数
     * 
     * @param ids 需要删除的提交材料参数主键
     * @return 结果
     */
    @Override
    public int deleteStuMaterialByIds(Long[] ids)
    {
        return stuMaterialMapper.deleteStuMaterialByIds(ids);
    }

    /**
     * 删除提交材料参数信息
     * 
     * @param id 提交材料参数主键
     * @return 结果
     */
    @Override
    public int deleteStuMaterialById(Long id)
    {
        return stuMaterialMapper.deleteStuMaterialById(id);
    }

}
