package com.gox.basic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.service.IFieldsItemService;
import com.gox.common.annotation.Log;
import com.gox.common.core.controller.BaseController;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.page.TableDataInfo;
import com.gox.common.enums.BusinessType;
import com.gox.common.utils.StringUtils;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.domain.FormJson;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.service.IFormDesignerDataService;
import com.gox.basic.service.IFormJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;


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
    @Autowired
    private IFieldsItemService fieldsItemService;
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
    @PreAuthorize("@ss.hasPermi('basic:json:list')")
    @GetMapping("/list")
    public TableDataInfo list(FormJson formJson)
    {
        startPage();
        List<FormJson> list = formJsonService.selectFormJsonList(formJson);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('basic:json:list')")
    @GetMapping("/table-field/{nodeId}/{deptId}")
    public AjaxResult getTableFields(@PathVariable Long nodeId,@PathVariable Long deptId){
        List<TableFieldVo> f = fieldsItemService.selectTableFieldByNodeIdAndDeptId(nodeId, deptId);
        return AjaxResult.success(f);
    }
    @PutMapping("/table-field")
    @Log(title = "表格表头", businessType = BusinessType.UPDATE)
    public AjaxResult updateTableFields(@RequestBody List<TableFieldVo> fieldVos){
        return AjaxResult.success(fieldsItemService.updateTableFieldsBatch(fieldVos));
    }
    @GetMapping("/table-title/{nodeId}/{deptId}")
    public AjaxResult getTableTile(@PathVariable Long nodeId,@PathVariable Long deptId){
        return AjaxResult.success(fieldsItemService.selectTableTitle(nodeId,deptId));
    }
    /**
     * 导出表单json列表
     */
    @PreAuthorize("@ss.hasPermi('basic:json:export')")
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
    @PreAuthorize("@ss.hasPermi('basic:json:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        FormJson formJson = formJsonService.selectFormJsonById(id);
//        FormDesignerData data = formDesignerDataService.selectFormDesignerDataById(id);
//        formJson.setFormData(JSONObject.toJSONString(data));
        return AjaxResult.success(formJson);

    }

    /**
     * 新增表单json
     * 修改表单
     */
    @PreAuthorize("@ss.hasPermi('basic:json:add')")
    @Log(title = "表单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FormJson formJson)
    {
        //FormJson formJson = new FormJson();
        JSONObject json = JSON.parseObject(formJson.getFormData());
        //String formname = json.getString("formname");
        String id = json.getString("id");
//        formJson.setFormName(formname);
//        formJson.setFormData(jsonStr);
        json.remove("formname");
        json.remove("id");
        FormDesignerData fd = json.toJavaObject(FormDesignerData.class);
        formJson.setFormDesignerData(fd);
        //formJson.setFormData(json.toJSONString());
        if (StringUtils.isNotEmpty(id)){
            formJson.setId(Long.valueOf(id));
//            json.remove("id");
            return toAjax(formJsonService.updateFormJson(formJson));
        }
        return toAjax(formJsonService.insertFormJson(formJson));
    }
    @PreAuthorize("@ss.hasPermi('basic:json:edit')")
    @Log(title = "表单json排序", businessType = BusinessType.UPDATE)
    @PutMapping("/order")
    public AjaxResult editOrder(@RequestBody List<FormJson> formJsons){
        return toAjax(formJsonService.updateFormOrderBatch(formJsons));
    }
//    /**
//     * 修改表单json
//     */
//    @PreAuthorize("@ss.hasPermi('basic:json:edit')")
//    @Log(title = "表单json", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody FormJson formJson)
//    {
//        return toAjax(formJsonService.updateFormJson(formJson));
//    }

    /**
     * 删除表单json
     */
    @PreAuthorize("@ss.hasPermi('basic:json:remove')")
    @Log(title = "表单json", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(formJsonService.deleteFormJsonByIds(ids));
    }
}
