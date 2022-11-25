package com.ruoyi.student.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.student.domain.PieVo;
import com.ruoyi.student.service.IPieVoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-11-23
 */
@RestController
@RequestMapping("/student/pie")
public class PieVoController extends BaseController
{
    @Autowired
    private IPieVoService pieVoService;

    @PreAuthorize("@ss.hasPermi('student:pie:acc')")
    @GetMapping("/acc")
    public TableDataInfo acc()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoAcc();
        if(map==null){
            List<PieVo> list = new ArrayList<>();
            //学生标签:0在校 1当日离校 2未在校 3当日返校
            String[] keys = {"在校","当日离校","家属未在校区","当日返校"};
            for(int i=0;i<keys.length;i++){
                PieVo pieVo = new PieVo();
                pieVo.setName(keys[i]);
                pieVo.setValueNumber(BigDecimal.valueOf(0));
                list.add(pieVo);
            }
            return getDataTable(list);
        }else{
            return mapToTable(map);
        }

    }

    @PreAuthorize("@ss.hasPermi('student:pie:residence')")
    @GetMapping("/residence")
    public TableDataInfo residence()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoResidence();
        if(map==null){
            List<PieVo> list = new ArrayList<>();
            //是否校内住宿:住宿:把在校生分为0教学区宿舍，1教学区其他区2家属区3其他,
            String[] keys = {"教学区宿舍","教学区其他区","家属区","其他"};
            for(int i=0;i<keys.length;i++){
                PieVo pieVo = new PieVo();
                pieVo.setName(keys[i]);
                pieVo.setValueNumber(BigDecimal.valueOf(0));
                list.add(pieVo);
            }
            return getDataTable(list);
        }else{
            return mapToTable(map);
        }
    }
    @PreAuthorize("@ss.hasPermi('student:pie:nobackschool')")
    @GetMapping("/nobackschool")
    public TableDataInfo nobackschool()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoNoBackSchool();
        if(map==null){
            List<PieVo> list = new ArrayList<>();
            //未返校原因:0因疫暂缓,1因就业实习暂缓,2因其他暂缓返
            String[] keys = {"因疫暂缓","因就业实习暂缓","因其他暂缓返回"};
            for(int i=0;i<keys.length;i++){
                PieVo pieVo = new PieVo();
                pieVo.setName(keys[i]);
                pieVo.setValueNumber(BigDecimal.valueOf(0));
                list.add(pieVo);
            }
            return getDataTable(list);
        }else{
            return mapToTable(map);
        }
    }
    @PreAuthorize("@ss.hasPermi('student:pie:control')")
    @GetMapping("/control")
    public TableDataInfo control()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoControl();
        if(map==null){
            List<PieVo> list = new ArrayList<>();
            //管控等级:0无管控，1校内居家监测,2校内居家隔离,3校外集中隔离
            String[] keys = {"无管控","校内居家监测","校内居家隔离","校外集中隔离"};
            for(int i=0;i<keys.length;i++){
                PieVo pieVo = new PieVo();
                pieVo.setName(keys[i]);
                pieVo.setValueNumber(BigDecimal.valueOf(0));
                list.add(pieVo);
            }
            return getDataTable(list);
        }else{
            return mapToTable(map);
        }
    }
    @PreAuthorize("@ss.hasPermi('student:pie:placetoschool')")
    @GetMapping("/placetoschool")
    public TableDataInfo placetoschool()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoPlaceToSchool();

        if(map==null){
            List<PieVo> list = new ArrayList<>();
            //返校出发地:0市内,1省内市外2省外.
            String[] keys = {"市内","省内市外","省外"};
            for(int i=0;i<keys.length;i++){
                PieVo pieVo = new PieVo();
                pieVo.setName(keys[i]);
                pieVo.setValueNumber(BigDecimal.valueOf(0));
                list.add(pieVo);
            }
            return getDataTable(list);
        }else{
            return mapToTable(map);
        }
    }
    public TableDataInfo mapToTable(Map<String, BigDecimal> map){
        List<PieVo> list = new ArrayList<>();
        System.out.println(map.size());
        Set<String> keySet = map.keySet();
        for(String key :keySet){
            PieVo pieVo = new PieVo();
            pieVo.setName(key);
            if(map.get(key)==null){
                pieVo.setValueNumber(BigDecimal.valueOf(0));
            }else{
                pieVo.setValueNumber(map.get(key));
            }
            System.out.println(key+map.get(key));
            list.add(pieVo);
        }
        return getDataTable(list);
    }




}
