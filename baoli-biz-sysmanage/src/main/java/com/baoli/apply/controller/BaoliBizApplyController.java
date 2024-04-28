package com.baoli.apply.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baoli.apply.domain.BaoliBizApply;
import com.baoli.apply.service.IBaoliBizApplyService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请管理Controller
 * 
 * @author niujs
 * @date 2024-04-21
 */
@RestController
@RequestMapping("/apply/apply")
public class BaoliBizApplyController extends BaseController
{
    @Autowired
    private IBaoliBizApplyService baoliBizApplyService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizApply baoliBizApply)
    {
        startPage();
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        return getDataTable(list);
    }

    /**
     * 导出申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:export')")
    @Log(title = "申请管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizApply baoliBizApply)
    {
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        ExcelUtil<BaoliBizApply> util = new ExcelUtil<BaoliBizApply>(BaoliBizApply.class);
        util.exportExcel(response, list, "申请管理数据");
    }

    /**
     * 获取申请管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizApplyService.selectBaoliBizApplyById(id));
    }

    /**
     * 新增申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:add')")
    @Log(title = "申请管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizApply baoliBizApply)
    {
        baoliBizApply.setApplicantId(getUserId());
        if(baoliBizApply.getStatus() == null){
            baoliBizApply.setStatus("02");
        }
        int insertResult = baoliBizApplyService.insertBaoliBizApply(baoliBizApply);
        JSONObject request = JSONObject.parseObject("{\"processDefinitionId\":\"Flowable1784092644025155584:8:1784236651778682880\",\"formData\":{},\"processUsers\":{},\"startUserInfo\":{\"id\":\"1\",\"name\":\"admin\",\"type\":\"user\"}}");
        JSONObject startUserInfo = request.getJSONObject("startUserInfo");
        startUserInfo.put("id",getUserId());
        startUserInfo.put("name", getUsername());
        String processDefinitionId = "";
        String accountReplyKey ="Flowable1784092644025155584:1:1784505501761953792";
        String feeKey ="Flowable1784249957583171584:1:1784505585077608448";
        String storeKey="Flowable1784238566084194304:4:1784505555042197504";
        String labelKey="Flowable1784236563664744448:1:1784505613502406656";
        request.getJSONObject("formData").put("applyId",baoliBizApply.getId());
        switch(baoliBizApply.getApplyType()){
            case "01":
                processDefinitionId =storeKey;
                request.getJSONObject("formData").put("form_applicant_role",getLoginUser().getUser().getRoles().get(0).getRoleId());
                break;
            case "02":
                processDefinitionId =feeKey;
                request.getJSONObject("formData").put("form_applicant_role",getLoginUser().getUser().getRoles().get(0).getRoleId());
                break;
            case "03":
                processDefinitionId =accountReplyKey;
                break;
            default:
                String selectedUser = "{\"form_assign_user\":[{\"id\":5,\"name\":\"驻店测试用户\",\"type\":\"user\",\"sex\":false,\"selected\":false}]}";
                request.put("formData", JSONObject.parseObject(selectedUser));
                processDefinitionId =labelKey;
        }
        request.put("processDefinitionId",processDefinitionId);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/process/start", request, String.class);
        return toAjax(insertResult);
    }

    /**
     * 修改申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:edit')")
    @Log(title = "申请管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizApply baoliBizApply)
    {
        return toAjax(baoliBizApplyService.updateBaoliBizApply(baoliBizApply));
    }

    /**
     * 删除申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:remove')")
    @Log(title = "申请管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizApplyService.deleteBaoliBizApplyByIds(ids));
    }
}
