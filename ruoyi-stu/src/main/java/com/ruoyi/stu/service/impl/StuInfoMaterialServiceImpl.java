package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.mapper.StuInfoMaterialMapper;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@Service
public class StuInfoMaterialServiceImpl implements IStuInfoMaterialService
{
    @Autowired
    private StuInfoMaterialMapper stuInfoMaterialMapper;


    @Override
    public List<StuInfoMaterial> selectStuMaterialList(StuInfoMaterial stuInfoMaterial) {
        return stuInfoMaterialMapper.selectStuMaterialList(stuInfoMaterial);
    }

    @Override
    public int insertStuMaterial(StuInfoMaterial stuInfoMaterial) {
        return stuInfoMaterialMapper.insertStuMaterial(stuInfoMaterial);
    }

    @Override
    public int updateStuMaterial(StuInfoMaterial stuInfoMaterial) {
        return stuInfoMaterialMapper.updateStuMaterial(stuInfoMaterial);
    }

    @Override
    public int deleteStuInfoMaterialById(Integer id) {
        return stuInfoMaterialMapper.deleteStuInfoMaterialById(id);
    }

    @Override
    public int deleteStuInfoMaterialByIds(Long[] ids) {
        return stuInfoMaterialMapper.deleteStuInfoMaterialByIds(ids);
    }
}
