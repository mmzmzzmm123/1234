package com.ruoyi.stu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.mapper.StuInfoMaterialMapper;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public Map<String,List<StuInfoMaterial>> selectStuMaterialList(BiyeForm stuInfoMaterial) {
        List<StuInfoMaterial> stuInfoMaterials = stuInfoMaterialMapper.selectStuMaterialList(stuInfoMaterial);
        Map<StuInfo, List<StuInfoMaterial>> collect = stuInfoMaterials.stream().collect(Collectors.groupingBy(StuInfoMaterial::getStuInfo));
        Set<Map.Entry<StuInfo, List<StuInfoMaterial>>> entries = collect.entrySet();
        Map<String,List<StuInfoMaterial>> map  = new HashMap<>();
        Gson gson = new Gson();
        for (Map.Entry<StuInfo, List<StuInfoMaterial>> entry : entries) {
            StuInfo key = entry.getKey();
                String s = gson.toJson(key);
                map.put(s,entry.getValue());
        }
        return map;
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
