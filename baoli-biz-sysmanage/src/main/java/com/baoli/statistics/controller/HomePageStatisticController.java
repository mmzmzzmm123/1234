package com.baoli.statistics.controller;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistic/homePage")
public class HomePageStatisticController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CODE_TASK_PROCESS="task-process";
    private static final String CODE_ORDER_PROCESS="order-process";
    private static final String CODE_BIZ_DATA="biz-data";
    private static final String CODE_RISK_DATA="risk-data";
    private static final String CODE_STORE_DATA="store-data";
    private static final String CODE_TASK_COMPLETE_STATUS="task-complete-status";

    @RequestMapping(value = "/statisticItem",method = RequestMethod.GET)
    public Map<String,Object> statisticItem(String itemCode){
        Map<String,Object> result = null;
        switch(itemCode){
            case CODE_TASK_PROCESS:
                result = taskProcess();
                break;
            case CODE_ORDER_PROCESS:
                result = orderProcess();
                break;
            case CODE_BIZ_DATA:
                result = bizData();
                break;
            case CODE_RISK_DATA:
                result = riskData();
                break;
            case  CODE_TASK_COMPLETE_STATUS:
                result = taskCompleteStatus();
                break;
            case CODE_STORE_DATA:
                result = storeData();
        }
        return result;
    }

    @RequestMapping(value = "/statisticList",method = RequestMethod.POST)
    public List<Map<String,Object>> statisticItemList(String itemCode,Map<String,Object> param){
        List<Map<String,Object>> result = null;
        return result;
    }

    /**
     * 待办信息统计
     * @return
     */
    private Map<String,Object> taskProcess(){
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("todo",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("done",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("byMe",item);
        return result;
    }

    /**
     * 年任务达成率
     * @return
     */
    private Map<String,Object> taskCompleteStatus(){
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearComplete",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearTotal",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearSeqComplete",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearSeqTotal",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearSeqOrder",item);
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("yearSeqOrderTotal",item);
        return result;
    }
    /**
     * 我的订单统计
     * @return
     */
    private Map<String,Object> orderProcess(){
        Long userId = SecurityUtils.getUserId();
        String status = "09";
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> countResult = null;
        Map<String,Object> item = null;
        // 非结单状态, 当前用户Id,非拒单
        String countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` <> ? and applicant_id = ? and refuse_order_id is null";
        countResult = jdbcTemplate.queryForMap(countSql,status,userId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("notComplete",item);
        // 结单状态, 当前用户Id,非拒单
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` =? and applicant_id = ? and refuse_order_id is null";
        countResult = jdbcTemplate.queryForMap(countSql,status,userId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("completed",item);
        return result;
    }
    /**
     * 风险数据
     * @return
     */
    private Map<String,Object> riskData(){
        Map<String,Object> result = new HashMap<>(1);
        String countSql = "";
        Map<String,Object> countResult = null;
        countSql = "select * FROM `ruoyi-vue`.sys_user_post_region where user_id = ?";
        long userId = SecurityUtils.getUserId();
        countResult = jdbcTemplate.queryForMap(countSql,userId);
        String regionId = countResult.get("region_id").toString();

        countSql = "select (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ? and label= '01') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ?) ratio from dual";
        countResult = jdbcTemplate.queryForMap(countSql,regionId,regionId);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",countResult.get("ratio"));
        item.put("param","");
        result.put("clear",item);

        countSql = "select (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ? and label= '02') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ?) ratio from dual";
        countResult = jdbcTemplate.queryForMap(countSql,regionId,regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("ratio"));
        item.put("param","");
        result.put("bad",item);
        countSql = "select (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ? and status>='08') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where city = ? and (status>='06')) ratio from dual;";
        countResult = jdbcTemplate.queryForMap(countSql,regionId,regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("ratio"));
        item.put("param","");
        result.put("mortgage",item);
        return result;
    }

    /**
     * 业务数据
     * @return
     */
    private Map<String,Object> bizData(){
        String status = "05";
        Map<String,Object> result = new HashMap<>(1);
        String countSql = "";
        Map<String,Object> countResult = null;
        countSql = "select * FROM `ruoyi-vue`.sys_user_post_region where user_id = ?";
        long userId = SecurityUtils.getUserId();
        countResult = jdbcTemplate.queryForMap(countSql,userId);
        String regionId = countResult.get("region_id").toString();

        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-01-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        // 年出单
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("yearOrderCount01",item);
        item = new HashMap<>(1);
        item.put("value",countResult.get("sum"));
        item.put("param","");
        result.put("yearOrderSum01",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-01-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,"07",regionId);
        // 年放款
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("yearOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",countResult.get("sum"));
        item.put("param","");
        result.put("yearOrderSum02",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        // 月出单
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("monthOrderCount01",item);
        item = new HashMap<>(1);
        // 月出单合计金额
        double monthOrder= Double.valueOf(countResult.get("sum").toString());
        item.put("value",monthOrder);
        item.put("param","");
        result.put("monthOrderSum01",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,"07",regionId);
        // 月放款
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("monthOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",countResult.get("sum"));
        item.put("param","");
        result.put("monthOrderSum02",item);
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,"04",regionId);
        int passed = Integer.valueOf(countResult.get("cnt").toString());
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where refuse_order_id is not null  and city= ? and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countResult = jdbcTemplate.queryForMap(countSql,regionId);
        int refuse = Integer.valueOf(countResult.get("cnt").toString());

        Float valid = 0f;
        if((passed + refuse) > 0){
            valid = (float)(passed / passed + refuse);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(valid);
        valid = Float.parseFloat(formattedNumber);
        // 审批通过率
        item = new HashMap<>(1);
        item.put("value",valid);
        item.put("param","");
        result.put("approvePassed",item);

        // 月任务完成率（暂时只考虑了按月度考核）
        countSql = "SELECT * FROM `ruoyi-vue`.baoli_biz_month_task_evaluate WHERE YEAR = DATE_FORMAT(now(), '%Y') AND MONTH = DATE_FORMAT(now(), '%m') and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,regionId);
        double amount = 0;
        String evaluateType = countResult.get("evaluate_type").toString();
        String refrencePlan = countResult.get("refrence_plan").toString();
        amount = Double.valueOf(countResult.get("amount").toString());
        double monthTaskCompleted = (double)(monthOrder / amount);
        formattedNumber = df.format(monthTaskCompleted);
        monthTaskCompleted = Double.valueOf(formattedNumber);
        item = new HashMap<>(1);
        item.put("value",monthTaskCompleted);
        item.put("param","");
        result.put("monthTaskCompleted",item);

        // 费用占比
        countSql = "SELECT   (sum(fee1+fee2+fee3+fee4) / any_value(income)) fee_ratio FROM `ruoyi-vue`.baoli_biz_order where  city= ? and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s') group by city";
        countResult = jdbcTemplate.queryForMap(countSql,regionId);

        amount = Double.valueOf(countResult.get("fee_ratio").toString());
        item = new HashMap<>(1);
        item.put("value",amount);
        item.put("param","");
        result.put("feeRatio",item);
        return result;
    }

    // 商户数据
    private Map<String,Object> storeData(){
        String status = "01"; // 已签约商户
        Map<String,Object> result = new HashMap<>(1);
        String countSql = "";
        Map<String,Object> countResult = null;

        countSql = "select * FROM `ruoyi-vue`.sys_user_post_region where user_id = ?";
        long userId = SecurityUtils.getUserId();
        countResult = jdbcTemplate.queryForMap(countSql,userId);
        String regionId = countResult.get("region_id").toString();

        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store where status = ? and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        int total =Integer.valueOf(countResult.get("cnt").toString());
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",total);
        item.put("param","");
        result.put("total",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(NOW(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        int monthOrder =Integer.valueOf(countResult.get("cnt").toString());
        item = new HashMap<>(1);
        item.put("value",monthOrder);
        item.put("param","");
        result.put("monthOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and not exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(NOW(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("monthNoOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and not exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and  b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(date_sub(NOW(), interval 3 month), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,status,regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("threeMonthNoOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store where status = ? and city_id = ?";
        countResult = jdbcTemplate.queryForMap(countSql,"03",regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("cnt"));
        item.put("param","");
        result.put("except",item);

        Float valid = (float)(monthOrder / total);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(valid);
        valid = Float.parseFloat(formattedNumber);
        item = new HashMap<>(1);
        item.put("value",valid);
        item.put("param","");
        result.put("valid",item);

        countSql ="select (SELECT count(1) FROM `ruoyi-vue`.baoli_biz_store where city_id = ? and (status = '01' or exception_reason ='01')) / (SELECT count(1) FROM `ruoyi-vue`.baoli_biz_store where city_id = ?)  expand from dual";
        countResult = jdbcTemplate.queryForMap(countSql,regionId,regionId);
        item = new HashMap<>(1);
        item.put("value",countResult.get("expand"));
        item.put("param","");
        result.put("expand",item);
        return result;
    }
}
