package com.baoli.order.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.baoli.order.domain.BaoliBizOrderLabel;
import com.baoli.order.service.IBaoliBizOrderLabelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.client.RestTemplate;

/**
 * 订单标签管理Controller
 * 
 * @author niujs
 * @date 2024-04-25
 */
@RestController
@RequestMapping("/order/label")
public class BaoliBizOrderLabelController extends BaseController
{
    @Autowired
    private IBaoliBizOrderLabelService baoliBizOrderLabelService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询订单标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:label:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizOrderLabel baoliBizOrderLabel)
    {
        startPage();
        List<BaoliBizOrderLabel> list = baoliBizOrderLabelService.selectBaoliBizOrderLabelList(baoliBizOrderLabel);
        return getDataTable(list);
    }

    /**
     * 导出订单标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('order:label:export')")
    @Log(title = "订单标签管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizOrderLabel baoliBizOrderLabel)
    {
        List<BaoliBizOrderLabel> list = baoliBizOrderLabelService.selectBaoliBizOrderLabelList(baoliBizOrderLabel);
        ExcelUtil<BaoliBizOrderLabel> util = new ExcelUtil<BaoliBizOrderLabel>(BaoliBizOrderLabel.class);
        util.exportExcel(response, list, "订单标签管理数据");
    }

    /**
     * 获取订单标签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:label:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizOrderLabelService.selectBaoliBizOrderLabelById(id));
    }

    /**
     * 新增订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:add')")
    @Log(title = "订单标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizOrderLabel baoliBizOrderLabel)
    {
        baoliBizOrderLabel.setSendUserId(getUserId());
        baoliBizOrderLabel.setStatus("01");//未审核

        int insertResult = baoliBizOrderLabelService.insertBaoliBizOrderLabel(baoliBizOrderLabel);
        JSONObject request = JSONObject.parseObject("{\"processDefinitionId\":\"Flowable1784092644025155584:8:1784236651778682880\",\"formData\":{},\"processUsers\":{},\"startUserInfo\":{\"id\":\"1\",\"name\":\"admin\",\"type\":\"user\"}}");
        JSONObject startUserInfo = request.getJSONObject("startUserInfo");
        startUserInfo.put("id",getUserId());
        startUserInfo.put("name", getLoginUser().getUser().getNickName());
        String processDefinitionId = "";

        String labelKey="Flowable1784236563664744448:1:1788527854795919360";
        String selectedUser = "{\"form_assign_user\":[{\"id\":5,\"name\":\"驻店测试用户\",\"type\":\"user\",\"sex\":false,\"selected\":false}]}";
        JSONObject assignUser = JSONObject.parseObject(selectedUser);
        assignUser.getJSONArray("form_assign_user").getJSONObject(0).put("id",baoliBizOrderLabel.getReceiveUserId());
        request.put("formData", assignUser);
        request.getJSONObject("formData").put("label",baoliBizOrderLabel);
        processDefinitionId =labelKey;
        request.put("processDefinitionId",processDefinitionId);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/process/start", request, String.class);
        return toAjax(insertResult);
    }

    /**
     * 修改订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:edit')")
    @Log(title = "订单标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizOrderLabel baoliBizOrderLabel)
    {
        baoliBizOrderLabel.setStatus("02");//已审核
        return toAjax(baoliBizOrderLabelService.updateBaoliBizOrderLabel(baoliBizOrderLabel));
    }

    /**
     * 删除订单标签管理
     */
    @PreAuthorize("@ss.hasPermi('order:label:remove')")
    @Log(title = "订单标签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizOrderLabelService.deleteBaoliBizOrderLabelByIds(ids));
    }
}
