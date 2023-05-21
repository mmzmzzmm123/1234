package com.ruoyi.stu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.mapper.StuInfoMaterialMapper;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 学生信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@Service
public class StuInfoMaterialServiceImpl implements IStuInfoMaterialService {
    @Autowired
    private StuInfoMaterialMapper stuInfoMaterialMapper;

    @Override
    public Map<String,List<StuInfoMaterial>> selectStuMaterialList(BiyeForm stuInfoMaterial) {
        List<StuInfoMaterial> stuInfoMaterials = stuInfoMaterialMapper.selectStuMaterialList(stuInfoMaterial);
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return  transer(stuInfoMaterials,user );
    }


    private Map<String,List<StuInfoMaterial>> transer(List<StuInfoMaterial>  stuInfoMaterials ,SysUser sysUser){
        Map<StuInfo, List<StuInfoMaterial>> collect = stuInfoMaterials.stream()
                    .filter(e-> {
                        if(sysUser.isAdmin()){
                            return  true;
                        }else{
                            return e.getStuInfo().getStuName().equals(sysUser.getUserName());
                        }
                    })
                    .collect(Collectors.groupingBy(StuInfoMaterial::getStuInfo));
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
    public int updateStuMaterial(BiyeForm biyeForm) {
        return stuInfoMaterialMapper.updateStuMaterial(biyeForm);
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

    @Override
    public void updateImgUrl(BiyeForm form) {
        stuInfoMaterialMapper.updateImgUrl(form);
    }
}
