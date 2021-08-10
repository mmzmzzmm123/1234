package com.ruoyi.web.controller.system;

import java.util.List;
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
import com.ruoyi.system.domain.QzBrithdayPu;
import com.ruoyi.system.service.IQzBrithdayPuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生日溥Controller
 * 
 * @author zlx
 * @date 2021-08-10
 */
@RestController
@RequestMapping("/system/birthdaypu")
public class QzBrithdayPuController extends BaseController
{
    @Autowired
    private IQzBrithdayPuService qzBrithdayPuService;

    /**
     * 查询生日溥列表
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:list')")
    @GetMapping("/list")
    public TableDataInfo list(QzBrithdayPu qzBrithdayPu)
    {
        startPage();
        List<QzBrithdayPu> list = qzBrithdayPuService.selectQzBrithdayPuList(qzBrithdayPu);
        return getDataTable(list);
    }

    /**
     * 导出生日溥列表
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:export')")
    @Log(title = "生日溥", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QzBrithdayPu qzBrithdayPu)
    {
        List<QzBrithdayPu> list = qzBrithdayPuService.selectQzBrithdayPuList(qzBrithdayPu);
        ExcelUtil<QzBrithdayPu> util = new ExcelUtil<QzBrithdayPu>(QzBrithdayPu.class);
        return util.exportExcel(list, "生日溥数据");
    }

    /**
     * 获取生日溥详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qzBrithdayPuService.selectQzBrithdayPuById(id));
    }

    /**
     * 新增生日溥
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:add')")
    @Log(title = "生日溥", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QzBrithdayPu qzBrithdayPu)
    {
        return toAjax(qzBrithdayPuService.insertQzBrithdayPu(qzBrithdayPu));
    }

    /**
     * 修改生日溥
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:edit')")
    @Log(title = "生日溥", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QzBrithdayPu qzBrithdayPu)
    {
        return toAjax(qzBrithdayPuService.updateQzBrithdayPu(qzBrithdayPu));
    }

    /**
     * 删除生日溥
     */
    @PreAuthorize("@ss.hasPermi('system:birthdaypu:remove')")
    @Log(title = "生日溥", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qzBrithdayPuService.deleteQzBrithdayPuByIds(ids));
    }
}
