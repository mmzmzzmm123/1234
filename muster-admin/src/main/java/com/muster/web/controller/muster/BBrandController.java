package com.muster.web.controller.muster;

import com.muster.common.annotation.Log;
import com.muster.common.core.controller.BaseController;
import com.muster.common.core.domain.AjaxResult;
import com.muster.common.core.page.TableDataInfo;
import com.muster.common.enums.BusinessType;
import com.muster.logic.DbLogicService;
import com.muster.logic.model.ProcedureResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 品牌Controller
 *
 * @author muster
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/muster")
public class BBrandController extends BaseController
{
    @Autowired
    private DbLogicService dbLogicService;

    /**
     * 查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('WD01P')")
    @GetMapping("/WD01P")
    public TableDataInfo list(@RequestBody Map<String, Object> params)
    {
        ProcedureResult result = dbLogicService.exec("proc_WD01P_b_brand", params);

        return getDataTable(result.getResult());
    }

    /**
     * 获取品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('WD01V')")
    @GetMapping(value = "WD01V/{brand_id}")
    public AjaxResult getInfo(@PathVariable("brand_id") Long brand_id)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("brand_id", brand_id);
        ProcedureResult result = dbLogicService.exec("proc_WD01V_b_brand", params);

        return AjaxResult.success(result.getResult());
    }

    /**
     * 新增品牌
     */
    @PreAuthorize("@ss.hasPermi('WD01C')")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping("WD01C")
    public AjaxResult add(@RequestBody  Map<String, Object> params)
    {
        ProcedureResult result = dbLogicService.exec("proc_WD01C_b_brand", params);

        return toAjax(result.getRes());
    }

    /**
     * 修改品牌
     */
    @PreAuthorize("@ss.hasPermi('WD01E')")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping("WD01E")
    public AjaxResult edit(@RequestBody Map<String, Object> params)
    {
        ProcedureResult result = dbLogicService.exec("proc_WD01E_b_brand", params);

        return toAjax(result.getRes());
    }

    /**
     * 删除品牌
     */
    @PreAuthorize("@ss.hasPermi('WD01D')")
    @Log(title = "品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/WD01D/{brand_ids}")
    public AjaxResult remove(@PathVariable Long[] brand_ids, @RequestBody Map<String, Object> params)
    {
        params.put("brand_ids", brand_ids);
        ProcedureResult result = dbLogicService.exec("proc_WD01D_b_brand", params);

        return toAjax(result.getRes());
    }
}