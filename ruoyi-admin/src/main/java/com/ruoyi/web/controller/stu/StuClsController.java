package com.ruoyi.web.controller.stu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.service.IStuClsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班级年级参数Controller
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/stu/base")
public class StuClsController extends BaseController
{
    @Autowired
    private IStuClsService stuClsService;

    /**
     * 查询班级年级参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:base:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuCls stuCls)
    {
        startPage();
        List<StuCls> list = stuClsService.selectStuClsList(stuCls);
        return getDataTable(list);
    }

    @GetMapping("/listAll")
    public AjaxResult listAll(StuCls stuCls)
    {
        Map<String, List<StuCls>> collect = stuClsService.selectStuClsList(stuCls).stream().collect(Collectors.groupingBy(StuCls::getClsYear));
        return AjaxResult.success(collect);
    }



    /**
     * 导出班级年级参数列表
     */
    @PreAuthorize("@ss.hasPermi('stu:base:export')")
    @Log(title = "班级年级参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuCls stuCls)
    {
        List<StuCls> list = stuClsService.selectStuClsList(stuCls);
        ExcelUtil<StuCls> util = new ExcelUtil<StuCls>(StuCls.class);
        util.exportExcel(response, list, "班级年级参数数据");
    }

    /**
     * 获取班级年级参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('stu:base:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuClsService.selectStuClsById(id));
    }

    /**
     * 新增班级年级参数
     */
    @PreAuthorize("@ss.hasPermi('stu:base:add')")
    @Log(title = "班级年级参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuCls stuCls)
    {
        return toAjax(stuClsService.insertStuCls(stuCls));
    }

    /**
     * 修改班级年级参数
     */
    @PreAuthorize("@ss.hasPermi('stu:base:edit')")
    @Log(title = "班级年级参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuCls stuCls)
    {
        return toAjax(stuClsService.updateStuCls(stuCls));
    }

    /**
     * 删除班级年级参数
     */
    @PreAuthorize("@ss.hasPermi('stu:base:remove')")
    @Log(title = "班级年级参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuClsService.deleteStuClsByIds(ids));
    }
}
