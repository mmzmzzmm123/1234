package com.gox.web.controller.system;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.gox.common.utils.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
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
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;

/**
 * 电子文件信息Controller
 * 
 * @author gox
 * @date 2020-12-29
 */
@RestController
@RequestMapping("/system/attributes")
public class ElectronicAttributesController extends BaseController
{
    @Autowired
    private IElectronicAttributesService electronicAttributesService;

    /**
     * 查询电子文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElectronicAttributes electronicAttributes)
    {
        startPage();
        List<ElectronicAttributes> list = electronicAttributesService.selectElectronicAttributesList(electronicAttributes);
        return getDataTable(list);
    }
    /**
     * 获取base64
     */
    @GetMapping("/base64/{id}")
    public AjaxResult getBase64(@PathVariable String id){
        String s = electronicAttributesService.getBase64(id);
        if (StrUtil.isNotBlank(s)){
            return AjaxResult.success(s);
        }
        return AjaxResult.error(500,"文件不存在!");
    }
    /**
     * 导出电子文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:export')")
    @Log(title = "电子文件信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ElectronicAttributes electronicAttributes)
    {
        List<ElectronicAttributes> list = electronicAttributesService.selectElectronicAttributesList(electronicAttributes);
        ExcelUtil<ElectronicAttributes> util = new ExcelUtil<ElectronicAttributes>(ElectronicAttributes.class);
        return util.exportExcel(list, "attributes");
    }

    /**
     * 获取电子文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(electronicAttributesService.selectElectronicAttributesById(id));
    }

    /**
     * 新增电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:add')")
    @Log(title = "电子文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElectronicAttributes electronicAttributes)
    {
        return toAjax(electronicAttributesService.insertElectronicAttributes(electronicAttributes));
    }

    /**
     * 修改电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:edit')")
    @Log(title = "电子文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElectronicAttributes electronicAttributes)
    {
        return toAjax(electronicAttributesService.updateElectronicAttributes(electronicAttributes));
    }

    /**
     * 删除电子文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:attributes:remove')")
    @Log(title = "电子文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(electronicAttributesService.deleteElectronicAttributesByIds(ids));
    }
}
