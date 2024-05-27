package com.baoli.order.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baoli.order.domain.BaoliBizOrder;
import com.baoli.order.service.IBaoliBizOrderService;
import com.baoli.store.domain.BaoliBizFeeRateRule;
import com.baoli.store.domain.BaoliBizStore;
import com.baoli.store.service.IBaoliBizFeeRateRuleService;
import com.baoli.store.service.IBaoliBizStoreService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.ibatis.annotations.Param;
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
    private IBaoliBizStoreService baoliBizStoreService;
    @Autowired
    private IBaoliBizFeeRateRuleService baoliBizFeeRateRuleService;

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
    @GetMapping("/listFeeRate")
    public Map<String,Object> listFeeRate(@Param("orderId") Long orderId,@Param("opType") String opType){
        Map<String,Object> result = new HashMap<>(1);
        List<Map<String,Object>> storeFeeRateList = new ArrayList<>(1);
        List<Map<String,Object>> customRateList = new ArrayList<>(1);
        List<Map<String,Object>> totalRateList = new ArrayList<>(1);
        //1 获取订单信息，取得 费率类型（新车、二手车、特殊车型），车型，银行，贴息，期数，商户id
        BaoliBizOrder baoliBizOrder = baoliBizOrderService.selectBaoliBizOrderById(orderId);
        String isDiscount = (opType == null || opType.equals("")) ? "01" : opType.equals("N") ? "01": "02";
        Long storeId = baoliBizOrder.getStoreId();
        Long modelId = baoliBizOrder.getCarModelId();
        Long bankId = baoliBizOrder.getBank();
        String period =baoliBizOrder.getPeriodNumber();
        String carType = baoliBizOrder.getCarType();
        //2 根据商户Id，获取费率信息，用上面的参数过滤费率
        BaoliBizStore baoliBizStore = baoliBizStoreService.selectBaoliBizStoreById(storeId);
        JSONArray feeRate = null;
        if(baoliBizStore.getFeeRateSchema()!=null){
            feeRate = JSONArray.parse(baoliBizStore.getFeeRateSchema());
            // 获取全部费率规则
            for(int i=0;i<feeRate.size();i++){
                JSONArray ruleIds = (JSONArray)feeRate.getJSONObject(i).get("ruleIds");
                BaoliBizFeeRateRule baoliBizFeeRateRule = baoliBizFeeRateRuleService.selectBaoliBizFeeRateRuleById(ruleIds.getLong(0));
                //判断是否贴息、银行
                if(isDiscount.equals(baoliBizFeeRateRule.getSubsidyType()) && bankId.equals(baoliBizFeeRateRule.getBankId())){
                    //如果是特殊车型
                    if(baoliBizFeeRateRule.getModelId()!=null){
                        if(modelId!=null && modelId == baoliBizFeeRateRule.getModelId()){

                        }
                    } else {
                        JSONArray rules = null;
                        if(baoliBizFeeRateRule.getContent()!=null){
                            rules = JSONArray.parse(baoliBizFeeRateRule.getContent());
                            for(int j=0;j<rules.size();j++){
                                if(rules.getJSONObject(j).getString("periodValue").equals(period)){
                                    Map<String,Object> map = new HashMap<>(1);
                                    map.put("label",rules.getJSONObject(j).get("cardholderRate"));
                                    map.put("value",rules.getJSONObject(j).get("cardholderRate"));
                                    storeFeeRateList.add(map);
                                    map = new HashMap<>(1);
                                    map.put("label",rules.getJSONObject(j).get("totalFeeRate"));
                                    map.put("value",rules.getJSONObject(j).get("totalFeeRate"));
                                    totalRateList.add(map);
                                    map = new HashMap<>(1);
                                    double cust = rules.getJSONObject(j).getDouble("totalFeeRate") - rules.getJSONObject(j).getDouble("cardholderRate") ;
                                    map.put("label",cust);
                                    map.put("value",cust);
                                    customRateList.add(map);
                                }
                            }
                        }
                    }
                }
            }
        }
        result.put("totalFeeRate",totalRateList);
        result.put("custFeeRate",customRateList);
        result.put("storeFeeRate",storeFeeRateList);
        //3 返回 费率列表
        return result;
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
            formattedDateKey = formattedDate;
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
        if(baoliBizOrder.getAuditInfo()==null){
            JSONObject taskInfo = getTaskInfo(baoliBizOrder.getId(),baoliBizOrder);
            baoliBizOrder.setAuditInfo(taskInfo);
        }
        if(baoliBizOrder.getAuditInfo()!=null){
            refuse(baoliBizOrder);
        }
        int result = baoliBizOrderService.transRefuseOrder(baoliBizOrder);
        return toAjax(result);
    }
    private void agree(BaoliBizOrder baoliBizOrder){
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/agree", baoliBizOrder.getAuditInfo(), String.class);
        System.out.println(response);
    }

    private void refuse(BaoliBizOrder baoliBizOrder){
        baoliBizOrder.getAuditInfo().put("comments","否决");
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/refuse", baoliBizOrder.getAuditInfo(), String.class);
    }
    private void runProcess(BaoliBizOrder baoliBizOrder){
        JSONObject request = JSONObject.parseObject("{\"processDefinitionId\":\"\",\"formData\":{},\"processUsers\":{},\"startUserInfo\":{\"id\":\"1\",\"name\":\"admin\",\"type\":\"user\"}}");
        JSONObject startUserInfo = request.getJSONObject("startUserInfo");
        startUserInfo.put("id",getUserId());
        startUserInfo.put("name", getLoginUser().getUser().getNickName());
        String processDefinitionId = "";

        String orderKey="Flowable1788388037957324800:1:1794721563241689088"; // 先抵押后放款
        if(baoliBizOrder.getOrderSchema().equals("03")){
            orderKey="Flowable1788388037957324900:1:1794721531859906560"; // 免抵押
        }
        String selectedUser = "{\"form_assign_user\":[{\"id\":5,\"name\":\"驻店测试用户\",\"type\":\"user\",\"sex\":false,\"selected\":false}]}";

        JSONObject assignUser = JSONObject.parseObject(selectedUser);
        assignUser.getJSONArray("form_assign_user").getJSONObject(0).put("id",getUserId());
        //获取当前登录用户所属省份
        String getUserRegionSql="select province_id from sys_user_role where user_id =? group by province_id";
        List<Map<String,Object>> region = jdbcTemplate.queryForList(getUserRegionSql,getUserId());
        StringBuffer buf =new StringBuffer();
        if(region!=null && region.size()>0){
            region.stream().forEach( item ->{
                buf.append(",").append(item.get("province_id"));
            });
        }
        assignUser.put("form_pre_check_area",buf.toString().substring(1));
        //如果预审区域不是河北省内
        if(!buf.toString().equals("130000")){
            assignUser.put("form_pre_check","2");
            //如果不预审,并且status == 02 ，则修改status = 03
            jdbcTemplate.update("update baoli_biz_order set status ='03' where id = ?",baoliBizOrder.getId());
        }
        assignUser.put("orderId",baoliBizOrder.getId());
        assignUser.put("customerName",baoliBizOrder.getCustomerName());
        request.put("formData", assignUser);
        processDefinitionId =orderKey;
        request.put("processDefinitionId",processDefinitionId);
        String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/process/start", request, String.class);
        System.out.println(response);
    }
    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('order:orderManage:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizOrder baoliBizOrder)
    {
        //如果status 是 02 提交 则发起工作流
        if(baoliBizOrder.getStatus().equals("02")){
            runProcess(baoliBizOrder);
        }
        // 不是 暂存，发起， 执行同意
        if(!baoliBizOrder.getStatus().equals("01") && !baoliBizOrder.getStatus().equals("02")){
            if(baoliBizOrder.getAuditInfo()==null){
                JSONObject taskInfo = getTaskInfo(baoliBizOrder.getId(),baoliBizOrder);
                baoliBizOrder.setAuditInfo(taskInfo);
            }
            if(baoliBizOrder.getAuditInfo()!=null) {
                JSONObject taskInfo = baoliBizOrder.getAuditInfo();
                if(baoliBizOrder.getPreCheckResult()!=null){
                    taskInfo.put("comments",baoliBizOrder.getPreCheckResult());
                }
                if(baoliBizOrder.getAuditResult()!=null){
                    taskInfo.put("comments",baoliBizOrder.getAuditResult());
                }
                if(taskInfo.getString("comments")==null || taskInfo.getString("comments").equals("")){
                    taskInfo.put("comments","同意");
                }
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
                //内勤 审核放款金额 , 将 adjustLoanAmount 替换 loanAmount;(如果审批通过)
                if(baoliBizOrder.getStatus().equals("09")){
                    //内勤 审核放款金额 , 将 adjustLoanAmount 替换 loanAmount;
                    if(baoliBizOrder.getAuditResult() == null || baoliBizOrder.getAuditResult().equals("01")) {
                        baoliBizOrder.setLoanAmount(baoliBizOrder.getAdjustLoanAmount());
                    }
                }
                if(baoliBizOrder.getStatus().equals("10")){
                    BaoliBizOrder blOrder = baoliBizOrderService.selectBaoliBizOrderById(baoliBizOrder.getId());
                    if(blOrder.getOrderSchema().equals("03")){
                        baoliBizOrder.setStatus("11");
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
        int result = baoliBizOrderService.updateBaoliBizOrder(baoliBizOrder);
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
        if(shareOrders!=null){
           List<Integer> ids = (List<Integer>) shareOrders.get("ids");
            for (Integer id : ids) {
                JSONObject auditInfo = getTaskInfo(Long.valueOf(id.toString()),null);
                String response = restTemplate.postForObject("http://127.0.0.1:9999/workspace/agree", auditInfo, String.class);
            }
        }
        int result = baoliBizOrderService.batchUpdateOrder(shareOrders);
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
       if(taskVari != Optional.empty()){
           taskObject = (JSONObject) taskVari.get();
           // 构造auditInfo
           auditInfo = new JSONObject();
           String comments = null;
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
