package com.ruoyi.stu.service.impl;

import com.google.gson.Gson;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.domain.StuClsCountData;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.domain.StuMaterial;
import com.ruoyi.stu.mapper.StuClsMapper;
import com.ruoyi.stu.mapper.StuInfoMapper;
import com.ruoyi.stu.mapper.StuMaterialMapper;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.mapper.StuInfoMaterialMapper;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.DataVO;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private StuInfoMapper stuInfoMapper;

    @Autowired
    private StuInfoMaterialMapper stuInfoMaterialMapper;

    @Autowired
    private StuClsMapper stuClsMapper;

    @Autowired
    private StuMaterialMapper stuMaterialMapper;

    @Override
    public int batchAddBiye(Long stuId) {
        int rows = selectIsCreatedMaterialId(stuId);
        List<BiyeForm> forms = new ArrayList<>();
        if(rows!=6){
            for(int i = 5 ; i<=10;i++){
                BiyeForm form = new BiyeForm();
                form.setFlag(1);
                form.setCreateBy("admin");
                form.setCreateTime(new Date());
                form.setMaterialId(i);
                form.setStuId(Math.toIntExact(stuId));
                forms.add(form);
            }
            stuInfoMaterialMapper.batchAddBiye(forms);
        }
        return 1;
    }

    @Override
    public int selectIsCreatedMaterialId(Long userId) {
        return stuInfoMaterialMapper.selectStuInfMaterialoByUserId(userId);
    }

    @Override
    public int selectStuFinishedMaterials(String clsYear,String clsYear2) {
        return stuInfoMaterialMapper.selectStuFinishedMaterials(clsYear,clsYear2);
    }

    @Override
    public DataVO showTopData(String clsYear) {
        DataVO dataVO = new DataVO();
        String clsYear2 = (Integer.parseInt(clsYear.substring(0,2))+1)+"级";
        int total = stuInfoMapper.selectBiYeStuNumByYear(clsYear,clsYear2);
        dataVO.setStuNum(total);
        dataVO.setTotalEnMaterial(total*5);
        dataVO.setFinshedEnMaterial(selectStuFinishedMaterials(clsYear,clsYear2));
        List<StuCls> clsList = stuClsMapper.selectBiYeStuNumByYearAndCls(clsYear,clsYear2);
        dataVO.setClsInfo(clsList);
        List<StuClsCountData> clsData = stuInfoMaterialMapper.selectClsData(clsYear, clsYear2);
        dataVO.setClsCountData(clsData);
        List<StuMaterial> stuMaterials = stuMaterialMapper.selectAllMaterial();
        dataVO.setStuMaterials(stuMaterials);
        return dataVO;
    }

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
