package com.ruoyi.web.controller.jdgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.jdgl.domain.TsbzJdglzwfz;
import com.ruoyi.jdgl.service.ITsbzJdglzwfzService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理自我发展Controller
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@RestController
@RequestMapping("/jdgl/jdglzwfz")
public class TsbzJdglzwfzController extends BaseController
{
    @Autowired
    private ITsbzJdglzwfzService tsbzJdglzwfzService;

    /**
     * 查询基地管理自我发展列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdglzwfz tsbzJdglzwfz)
    {
        tsbzJdglzwfz.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJdglzwfz> list = tsbzJdglzwfzService.selectTsbzJdglzwfzList(tsbzJdglzwfz);
        return getDataTable(list);
    }

    /**
     * 导出基地管理自我发展列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:export')")
    @Log(title = "基地管理自我发展", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdglzwfz tsbzJdglzwfz)
    {
        List<TsbzJdglzwfz> list = tsbzJdglzwfzService.selectTsbzJdglzwfzList(tsbzJdglzwfz);
        ExcelUtil<TsbzJdglzwfz> util = new ExcelUtil<TsbzJdglzwfz>(TsbzJdglzwfz.class);
        return util.exportExcel(list, "jdglzwfz");
    }

    /**
     * 获取基地管理自我发展详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdglzwfzService.selectTsbzJdglzwfzById(id));
    }

    /**
     * 新增基地管理自我发展
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:add')")
    @Log(title = "基地管理自我发展", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdglzwfz tsbzJdglzwfz)
    {
        tsbzJdglzwfz.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJdglzwfz.setCreateTime(new Date());
        tsbzJdglzwfz.setSctime(new Date());
        return toAjax(tsbzJdglzwfzService.insertTsbzJdglzwfz(tsbzJdglzwfz));
    }

    /**
     * 修改基地管理自我发展
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:edit')")
    @Log(title = "基地管理自我发展", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdglzwfz tsbzJdglzwfz)
    {
        tsbzJdglzwfz.setSctime(new Date());
        return toAjax(tsbzJdglzwfzService.updateTsbzJdglzwfz(tsbzJdglzwfz));
    }

    /**
     * 删除基地管理自我发展
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzwfz:remove')")
    @Log(title = "基地管理自我发展", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdglzwfzService.deleteTsbzJdglzwfzByIds(ids));
    }
}
