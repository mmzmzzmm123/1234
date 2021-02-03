package com.gox.web.controller.system;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.common.plugin.AutoId;
import com.gox.common.utils.StringUtils;
import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.system.domain.form.FormDesignerData;
import com.gox.system.service.IFormDesignerDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.enums.BusinessType;
import com.gox.system.domain.FormJson;
import com.gox.system.service.IFormJsonService;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.core.page.TableDataInfo;


/**
 * 表单jsonController
 * 
 * @author gox
 * @date 2020-12-25
 */
@RestController
@RequestMapping("/system/json")
public class FormJsonController extends BaseController
{
    @Autowired
    private IFormJsonService formJsonService;
    @Autowired
    private IFormDesignerDataService formDesignerDataService;
    /**
     * id获取
     */
    @PostMapping("/id")
    public AjaxResult getId(){
        return AjaxResult.success(SnowflakesTools.WORKER.nextId());
    }
    /**
     * 查询表单json列表
     */
    @PreAuthorize("@ss.hasPermi('system:json:list')")
    @GetMapping("/list")
    public TableDataInfo list(FormJson formJson)
    {
        startPage();
        List<FormJson> list = formJsonService.selectFormJsonList(formJson);
        return getDataTable(list);
    }

    /**
     * 导出表单json列表
     */
    @PreAuthorize("@ss.hasPermi('system:json:export')")
    @Log(title = "表单json", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FormJson formJson)
    {
        List<FormJson> list = formJsonService.selectFormJsonList(formJson);
        ExcelUtil<FormJson> util = new ExcelUtil<FormJson>(FormJson.class);
        return util.exportExcel(list, "json");
    }

    /**
     * 获取表单json详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:json:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(formJsonService.selectFormJsonById(id));
    }

    /**
     * 新增表单json
     * 修改表单
     */
    @PreAuthorize("@ss.hasPermi('system:json:add')")
    @Log(title = "表单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody String jsonStr)
    {
        FormJson formJson = new FormJson();
        JSONObject json = JSON.parseObject(jsonStr);
        String formname = json.getString("formname");
        String id = json.getString("id");
        formJson.setFormName(formname);
        formJson.setFormData(jsonStr);
        json.remove("formname");
        json.remove("id");
        FormDesignerData fd = json.toJavaObject(FormDesignerData.class);
        formDesignerDataService.insertFormDesignerData(fd);
        formJson.setFormData(json.toJSONString());
        if (StringUtils.isNotEmpty(id)){
            formJson.setId(Long.valueOf(id));
            json.remove("id");
            return toAjax(formJsonService.updateFormJson(formJson));
        }
        return toAjax(formJsonService.insertFormJson(formJson));
    }
    /**
     * 修改表单json
     */
    @PreAuthorize("@ss.hasPermi('system:json:edit')")
    @Log(title = "表单json", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FormJson formJson)
    {
        return toAjax(formJsonService.updateFormJson(formJson));
    }

    /**
     * 删除表单json
     */
    @PreAuthorize("@ss.hasPermi('system:json:remove')")
    @Log(title = "表单json", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(formJsonService.deleteFormJsonByIds(ids));
    }
}
