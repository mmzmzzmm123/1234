package com.gox.basic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.domain.vo.TemplatesCopyVo;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.plugin.SnowIdUtils;
import com.gox.common.utils.StringUtils;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.domain.FormJson;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.service.IFormJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 模板管理Controller
 *
 * @author gox
 * @date 2020-12-25
 */
@RestController
@RequestMapping("/system/json")
public class FormJsonController extends BaseController {
    @Autowired
    private IFormJsonService formJsonService;

    /**
     * id获取
     */
    @PostMapping("/id")
    public AjaxResult getId() {
        return AjaxResult.success(SnowIdUtils.uniqueLong());
    }

    /**
     * 查询模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:json:list')")
    @GetMapping("/list")
    public TableDataInfo list(FormJson formJson) {
        startPage();
        List<FormJson> list = formJsonService.selectFormJsonList(formJson);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('basic:json:edit')")
    @Log(title = "模板管理",businessType = BusinessType.UPDATE)
    @PostMapping("/copy")
    public AjaxResult copy(@RequestBody TemplatesCopyVo vo){
        return AjaxResult.success(formJsonService.copy(vo));
    }
    /**
     * 导出模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:json:export')")
    @Log(title = "模板管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FormJson formJson) {
        List<FormJson> list = formJsonService.selectFormJsonList(formJson);
        ExcelUtil<FormJson> util = new ExcelUtil<FormJson>(FormJson.class);
        return util.exportExcel(list, "json");
    }

    /**
     * 获取模板管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:json:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        FormJson formJson = formJsonService.selectFormJsonById(id);
        return AjaxResult.success(formJson);

    }

    /**
     * 新增模板管理
     * 修改表单
     */
    @PreAuthorize("@ss.hasPermi('basic:json:add')")
    @Log(title = "表单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FormJson formJson) {
        JSONObject json = JSON.parseObject(formJson.getFormData());
        String id = json.getString("id");
        json.remove("formname");
        json.remove("id");
        FormDesignerData fd = json.toJavaObject(FormDesignerData.class);
        formJson.setFormDesignerData(fd);
        if (StringUtils.isNotEmpty(id)) {
            formJson.setId(Long.valueOf(id));
            return toAjax(formJsonService.updateFormJson(formJson));
        }
        return toAjax(formJsonService.insertFormJson(formJson));
    }

    @PreAuthorize("@ss.hasPermi('basic:json:edit')")
    @Log(title = "模板管理排序", businessType = BusinessType.UPDATE)
    @PutMapping("/order")
    public AjaxResult editOrder(@RequestBody List<FormJson> formJsons) {
        return toAjax(formJsonService.updateFormOrderBatch(formJsons));
    }

    /**
     * 删除模板管理
     */
    @PreAuthorize("@ss.hasPermi('basic:json:remove')")
    @Log(title = "模板管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(formJsonService.deleteFormJsonByIds(ids));
    }
}
