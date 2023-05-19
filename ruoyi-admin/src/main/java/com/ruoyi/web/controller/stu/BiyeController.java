package com.ruoyi.web.controller.stu;

import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.service.IStuInfoMaterialService;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 提交材料参数Controller
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/stu/biye")
public class BiyeController extends BaseController
{
    @Autowired
    private IStuInfoMaterialService stuInfoMaterialService;

    @PreAuthorize("@ss.hasPermi('stu:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuInfoMaterialService.selectStuInfMaterialoById(id));
    }

    /**
     * 查询提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:list')")
    @GetMapping("/list")
    public TableDataInfo list(BiyeForm stuInfoMaterial)
    {
        startPage();
        Map<String, List<StuInfoMaterial>> list = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        List<Object> objects = Arrays.asList(list.values().toArray());
        rspData.setRows(objects);
        rspData.setTotal(new PageInfo(objects).getTotal());
        return rspData;
    }

    /**
     * 导出提交材料参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:export')")
    @Log(title = "提交材料参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BiyeForm stuInfoMaterial)
    {
//        Map<String, List<StuInfoMaterial>> list = stuInfoMaterialService.selectStuMaterialList(stuInfoMaterial);
//        ExcelUtil<StuInfoMaterial> util = new ExcelUtil<>(StuInfoMaterial.class);
//        util.exportExcel(response, list, "提交材料参数数据");
    }



    /**
     * 新增提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:add')")
    @Log(title = "提交材料参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuInfoMaterial stuInfoMaterial)
    {
        return toAjax(stuInfoMaterialService.insertStuMaterial(stuInfoMaterial));
    }

    /**
     * 修改提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:edit')")
    @Log(title = "提交材料参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuInfoMaterial stuInfoMaterial)
    {
        return toAjax(stuInfoMaterialService.updateStuMaterial(stuInfoMaterial));
    }

    /**
     * 删除提交材料参数
     */
    @PreAuthorize("@ss.hasPermi('stu:biye:remove')")
    @Log(title = "提交材料参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuInfoMaterialService.deleteStuInfoMaterialByIds(ids));
    }
}
