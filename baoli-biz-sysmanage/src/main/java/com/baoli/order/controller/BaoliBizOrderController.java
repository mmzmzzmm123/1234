package com.baoli.order.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 *
 * @author niujs
 * @date 2024-04-07
 */
@RestController
@RequestMapping("/order/orderManage")
public class BaoliBizOrderController extends BaseController
{
    @Autowired
    private IBaoliBizOrderService baoliBizOrderService;

    @Autowired
    private RestTemplate restTemplate;

    //订单流水号
    private static int serialNumber = 0;
    //订单日期前缀
    private static String orderDatePrefix = null;
    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizOrder baoliBizOrder)
    {
        startPage();
        List<BaoliBizOrder> list = baoliBizOrderService.selectBaoliBizOrderList(baoliBizOrder);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/myList")
    public TableDataInfo myList(BaoliBizOrder baoliBizOrder)
    {
        startPage();
        baoliBizOrder.setApplicantId(getUserId());
        List<BaoliBizOrder> list = baoliBizOrderService.selectMyOrderList(baoliBizOrder);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/getOrderNumber")
    public AjaxResult getOrderNumber(BaoliBizOrder baoliBizOrder)
    {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 创建一个SimpleDateFormat对象，指定目标格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 格式化日期
        String formattedDate = sdf.format(calendar.getTime());
        if(orderDatePrefix == null){
            orderDatePrefix = formattedDate;
        }
        if(!orderDatePrefix.equals(formattedDate)){
            orderDatePrefix = formattedDate;
            serialNumber = 0;
        }
        String loanType = "X";
        switch(baoliBizOrder.getLoanType()){
            case "01":
                loanType = "X";
                break;
            case "02":
                loanType = "D";
                break;
        }
        String carType = "N";
        switch(baoliBizOrder.getCarType()){
            case "01":
                carType = "B";
                break;
            case "02":
                carType = "O";
                break;
        }
        serialNumber++;
        baoliBizOrder.setOrderNumber(loanType + carType + orderDatePrefix + padZero(String.valueOf(serialNumber),4));
        return success(baoliBizOrder);
    }


    public String padZero(String str, int length) {
        int zeroCount = length - str.length();
        if (zeroCount <= 0) {
            return str;
        }
        char[] zeros = new char[zeroCount];
        Arrays.fill(zeros, '0');
        return new String(zeros) + str;
    }
    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/myOrder")
    public TableDataInfo myOrder(BaoliBizOrder baoliBizOrder)
    {
        startPage();
        baoliBizOrder.setApplicantId(getUserId());
        List<Map<String,Object>> list = baoliBizOrderService.selectMyOrder(baoliBizOrder);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('order:orderManage:list')")
    @GetMapping("/myOrderCount")
    public TableDataInfo myOrderCount(BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setApplicantId(getUserId());
        List<Map<String,Object>> list = baoliBizOrderService.selectMyOrderCount(baoliBizOrder);
        TableDataInfo result = getDataTable(list);
        result.setTotal(0);
        if(list!=null&& list.size()>0){
            list.forEach(sItem ->{
                final Long total;
                total = Long.valueOf(sItem.get("cnt").toString());
                result.setTotal(result.getTotal()+total);
            });
        }
        return result;
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizOrder baoliBizOrder)
    {
        List<BaoliBizOrder> list = baoliBizOrderService.selectBaoliBizOrderList(baoliBizOrder);
        ExcelUtil<BaoliBizOrder> util = new ExcelUtil<BaoliBizOrder>(BaoliBizOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizOrderService.selectBaoliBizOrderById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        baoliBizOrder.setApplicantId(getUserId());
        int result = baoliBizOrderService.insertBaoliBizOrder(baoliBizOrder);
        //如果status 是 02 提交 则发起工作流
        if(baoliBizOrder.getStatus().equals("02")){
            runProcess(baoliBizOrder);
        }
        return toAjax(result);
    }

    @PreAuthorize("@ss.hasPermi('order:orderManage:add')")
    @PostMapping("/transRefuseOrder")
    public AjaxResult transRefuseOrder(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        int result = baoliBizOrderService.transRefuseOrder(baoliBizOrder);
        if(baoliBizOrder.getAuditInfo()!=null){
            refuse(baoliBizOrder);
        }
        return toAjax(result);
    }
    private void agree(BaoliBizOrder baoliBizOrder){
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/agree", baoliBizOrder.getAuditInfo(), String.class);
    }

    private void refuse(BaoliBizOrder baoliBizOrder){
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/refuse", baoliBizOrder.getAuditInfo(), String.class);
    }
    private void runProcess(BaoliBizOrder baoliBizOrder){
        JSONObject request = JSONObject.parseObject("{\"processDefinitionId\":\"\",\"formData\":{},\"processUsers\":{},\"startUserInfo\":{\"id\":\"1\",\"name\":\"admin\",\"type\":\"user\"}}");
        JSONObject startUserInfo = request.getJSONObject("startUserInfo");
        startUserInfo.put("id",getUserId());
        startUserInfo.put("name", getLoginUser().getUser().getNickName());
        String processDefinitionId = "";

        String orderKey="Flowable1788388037957324800:1:1788494584678973440";
        String selectedUser = "{\"form_assign_user\":[{\"id\":5,\"name\":\"驻店测试用户\",\"type\":\"user\",\"sex\":false,\"selected\":false}]}";

        JSONObject assignUser = JSONObject.parseObject(selectedUser);
        assignUser.getJSONArray("form_assign_user").getJSONObject(0).put("id",getUserId());
        assignUser.put("form_pre_check_area","130000");
        assignUser.put("orderId",baoliBizOrder.getId());
        request.put("formData", assignUser);
        processDefinitionId =orderKey;
        request.put("processDefinitionId",processDefinitionId);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/process/start", request, String.class);

    }
    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        int result = baoliBizOrderService.updateBaoliBizOrder(baoliBizOrder);
        //如果status 是 02 提交 则发起工作流
        if(baoliBizOrder.getStatus().equals("02")){
            runProcess(baoliBizOrder);
        }
        // 不是 暂存，发起， 执行同意
        if(!baoliBizOrder.getStatus().equals("01") && !baoliBizOrder.getStatus().equals("02")){
            if(baoliBizOrder.getAuditInfo()!=null) {
                agree(baoliBizOrder);
            }
        }
        return toAjax(result);
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizOrderService.deleteBaoliBizOrderByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('order:orderManage:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/batchUpdateOrder")
    public AjaxResult batchUpdateOrder(@RequestBody Map<String,Object> shareOrders)
    {
        int result = baoliBizOrderService.batchUpdateOrder(shareOrders);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/agree", shareOrders.get("auditInfo"), String.class);
        return toAjax(result);
    }
}
