package com.ruoyi.headmaster.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.headmaster.domain.HeadmasterVo;
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
import com.ruoyi.headmaster.domain.Headmaster;
import com.ruoyi.headmaster.service.IHeadmasterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主任信息Controller
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
@RestController
@RequestMapping("/headmaster/headmaster")
public class HeadmasterController extends BaseController
{
    @Autowired
    private IHeadmasterService headmasterService;

    /**
     * 查询主任信息列表
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:list')")
    @GetMapping("/list")
    public TableDataInfo list(HeadmasterVo headmaster)
    {
        startPage();
        List<HeadmasterVo> list = headmasterService.selectHeadmasterList(headmaster);
        return getDataTable(list);
    }

    /**
     * 导出主任信息列表
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:export')")
    @Log(title = "主任信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HeadmasterVo headmaster)
    {
        List<HeadmasterVo> list = headmasterService.selectHeadmasterList(headmaster);
        ExcelUtil<HeadmasterVo> util = new ExcelUtil<HeadmasterVo>(HeadmasterVo.class);
        util.exportExcel(response, list, "主任信息数据");
    }

    /**
     * 获取主任信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(headmasterService.selectHeadmasterById(id));
    }

    /**
     * 新增主任信息
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:add')")
    @Log(title = "主任信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Headmaster headmaster)
    {
        return toAjax(headmasterService.insertHeadmaster(headmaster));
    }

    /**
     * 修改主任信息
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:edit')")
    @Log(title = "主任信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Headmaster headmaster)
    {
        return toAjax(headmasterService.updateHeadmaster(headmaster));
    }

    /**
     * 删除主任信息
     */
    @PreAuthorize("@ss.hasPermi('headmaster:headmaster:remove')")
    @Log(title = "主任信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(headmasterService.deleteHeadmasterByIds(ids));
    }
}
