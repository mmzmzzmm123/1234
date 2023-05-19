package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.mapper.StuInfoMaterialMapper;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Map<String, List<StuInfoMaterial>> selectStuMaterialList(BiyeForm stuInfoMaterial) {
        List<StuInfoMaterial> stuInfoMaterials = stuInfoMaterialMapper.selectStuMaterialList(stuInfoMaterial);
        Map<String, List<StuInfoMaterial>> collect = stuInfoMaterials.stream().collect(Collectors.groupingBy(e -> e.getStuInfo().getStuName()));
        System.out.println(collect);
//        Map<StuInfo, StuInfoMaterial> collect = stuInfoMaterials.stream().collect(Collectors.toMap(StuInfoMaterial::getStuInfo, (stu) -> stu));
//  TODO
        return collect;
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

    @Override
    public StuInfoMaterial selectStuInfMaterialoById(Long id) {
        return  stuInfoMaterialMapper.selectStuInfMaterialoById(id);
    }
}
