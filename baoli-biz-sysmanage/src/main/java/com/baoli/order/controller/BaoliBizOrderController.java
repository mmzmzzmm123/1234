package com.baoli.order.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
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
    @GetMapping("/feeList")
    public TableDataInfo feeList(String ids)
    {
        String sql ="select count(1) cnt,ifnull(sum(loan_amount),0) loanAmount,ifnull(sum(fee1),0) fee1,ifnull(sum(fee2),0) fee2,ifnull(sum(fee3),0) fee3,ifnull(sum(fee4),0) fee4 from baoli_biz_order where id in("+ids+")";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
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
        //订单流水号
        int serialNumber = 0;
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 创建一个SimpleDateFormat对象，指定目标格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 格式化日期
        String formattedDate = sdf.format(calendar.getTime());
        Object formattedDateKey = redisTemplate.opsForValue().get(formattedDate);
        if(formattedDateKey == null){
            redisTemplate.expire(formattedDate,24, TimeUnit.HOURS);
            redisTemplate.opsForValue().set("nextSerialNumber",0);
            redisTemplate.opsForValue().set(formattedDate,formattedDate);
        }
        serialNumber =redisTemplate.opsForValue().get("nextSerialNumber") ==null ? 0 :  Integer.valueOf(redisTemplate.opsForValue().get("nextSerialNumber").toString());
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
        redisTemplate.opsForValue().set("nextSerialNumber",serialNumber);
        baoliBizOrder.setOrderNumber(loanType + carType + formattedDateKey.toString() + padZero(String.valueOf(serialNumber),4));
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
        getTaskInfo(id,null);
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
        //如果是提交转预审
        if(baoliBizOrder.getStatus().equals("02")){
            baoliBizOrder.setSubmitDate(DateUtils.getNowDate());
        }
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

        String orderKey="Flowable1788388037957324800:7:1791008320736206848";
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
                JSONObject bizFormData = new JSONObject();
                //在银行放款环节，增加默认参数
                if(baoliBizOrder.getStatus().equals("07")){
                    //设置默认银行审核放款结果为通过
                    bizFormData.put("form_check_release_loan_result",1);
                }
                //判断实际放款金额（输入值）是否与 驻店录入值一致
                if(baoliBizOrder.getStatus().equals("08")){
                    if(baoliBizOrder.getAdjustLoanAmount()!=baoliBizOrder.getLoanAmount()){
                        bizFormData.put("form_bank_release_loan",2);
                    }
                }
                baoliBizOrder.getAuditInfo().put("bizFormData",bizFormData);
                if(baoliBizOrder.getAuditResult() == null || baoliBizOrder.getAuditResult().equals("01")){
                    agree(baoliBizOrder);
                } else {
                    refuse(baoliBizOrder);
                }
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

    @PreAuthorize("@ss.hasPermi('order:orderManage:edit')")
    @PostMapping("/batchUpdateOrder")
    public AjaxResult batchUpdateOrder(@RequestBody Map<String,Object> shareOrders)
    {
        int result = baoliBizOrderService.batchUpdateOrder(shareOrders);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/agree", shareOrders.get("auditInfo"), String.class);
        return toAjax(result);
    }

    private JSONObject getTaskInfo(Long orderId,BaoliBizOrder baoliBizOrder){
        JSONObject auditInfo = null;
        JSONObject param = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("id",getUserId());
        userInfo.put("name",getLoginUser().getUser().getNickName());
        userInfo.put("type","user");
        param.put("currentUserInfo",userInfo);
        param.put("pageNo",0);
        param.put("pageSize",0);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/process/toDoList",param, String.class);
        JSONObject result = JSONObject.parseObject(response);
        JSONArray records = result.getJSONObject("result").getJSONArray("records");
       Optional taskVari = records.stream().filter(obj->{
            JSONObject item = (JSONObject) obj;
            Long taskOrderId = null;
            if(item.getJSONObject("variables").containsKey("orderId")){
                taskOrderId = item.getJSONObject("variables").getLong("orderId");
            }
            return taskOrderId == orderId;
        }).findFirst();

       JSONObject taskObject = null;
       if(taskVari.get()!=null){
           taskObject = (JSONObject) taskVari.get();
           // 构造auditInfo
           auditInfo = new JSONObject();
           String comments = "";
           comments = baoliBizOrder == null ? "":baoliBizOrder.getAuditComment() ==null ? "":baoliBizOrder.getAuditComment();
           auditInfo.put("currentUserInfo",userInfo);
           auditInfo.put("processInstanceId",taskObject.get("processInstanceId"));
           auditInfo.put("taskId",taskObject.get("taskId"));
           auditInfo.put("signFlag",false);
           auditInfo.put("formData",taskObject.get("variables"));
           auditInfo.put("comments",comments);
           auditInfo.put("attachments",null);
       }
       return auditInfo;
    }
}
