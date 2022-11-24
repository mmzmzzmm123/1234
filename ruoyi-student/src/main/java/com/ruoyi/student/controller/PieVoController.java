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
        return mapToTable(map);
    }

    @PreAuthorize("@ss.hasPermi('student:pie:residence')")
    @GetMapping("/residence")
    public TableDataInfo residence()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoResidence();
        return mapToTable(map);
    }
    @PreAuthorize("@ss.hasPermi('student:pie:nobackschool')")
    @GetMapping("/nobackschool")
    public TableDataInfo nobackschool()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoNoBackSchool();
        return mapToTable(map);
    }
    @PreAuthorize("@ss.hasPermi('student:pie:control')")
    @GetMapping("/control")
    public TableDataInfo control()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoControl();
        return mapToTable(map);
    }
    @PreAuthorize("@ss.hasPermi('student:pie:placetoschool')")
    @GetMapping("/placetoschool")
    public TableDataInfo placetoschool()
    {
        Map<String, BigDecimal> map = pieVoService.selectPieVoPlaceToSchool();

        return mapToTable(map);
    }
    public TableDataInfo mapToTable(Map<String, BigDecimal> map){
        List<PieVo> list = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for(String key :keySet){
            PieVo pieVo = new PieVo();
            pieVo.setName(key);
            pieVo.setValueNumber(map.get(key));
            list.add(pieVo);
        }
        return getDataTable(list);
    }




}
