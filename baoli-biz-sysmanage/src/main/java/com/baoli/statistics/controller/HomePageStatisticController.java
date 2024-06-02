package com.baoli.statistics.controller;

import com.ruoyi.common.annotation.Anonymous;
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

    @Anonymous
    @RequestMapping(value = "/feedBack",method = RequestMethod.GET)
    public Map<String,Object> feedBack(String applyId){
        Map<String,Object> result = null;
        return result;
    }
    @Anonymous
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
        String status = "11";
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> countResult = null;
        Map<String,Object> item = null;
        // 非结单状态, 当前用户Id,非拒单
        String countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` <> ? and applicant_id = ? and refuse_order_id is null";
        List<Map<String,Object>> countList = jdbcTemplate.queryForList(countSql,status,userId);
        String notComplete ="0";
        if(countList.size()>0){
            countResult = countList.get(0);
            notComplete = countResult.get("cnt").toString();
        }
        item = new HashMap<>(1);
        item.put("value",notComplete);
        item.put("param","");
        result.put("notComplete",item);
        // 结单状态, 当前用户Id,非拒单
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` =? and applicant_id = ? and refuse_order_id is null";
        countList = jdbcTemplate.queryForList(countSql,status,userId);
        if(countList.size()>0){
            countResult = countList.get(0);
            notComplete = countResult.get("cnt").toString();
        }
        item = new HashMap<>(1);
        item.put("value",notComplete);
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
        Long userId = SecurityUtils.getUserId();
        countSql = "select (select b.area_region from `ruoyi-vue`.sys_role b where b.role_id = a.role_id) area_region,province_id,city_id FROM `ruoyi-vue`.sys_user_role a where a.user_id = ?";
        String areaRegion = null;
        String cityId = "-1";
        String provinceId = "-1";
        List<Map<String,Object>> countList = jdbcTemplate.queryForList(countSql,userId);
        if(countList.size()>0){
            countResult = countList.get(0);
            cityId = countResult.get("city_id")!=null ? countResult.get("city_id").toString() : null;
            provinceId= countResult.get("province_id")!=null ? countResult.get("province_id").toString() :null;
            areaRegion = countResult.get("area_region").toString();
        }

        String regionParam =" 1=1 ";
        if(areaRegion.equals("01")){
            regionParam = " city = '"+cityId+"'";
        } else if (areaRegion.equals("02")){
            regionParam = " province_id = '"+provinceId+"'";
        }
        String regionId = null;
        countSql = "select ifnull(round((select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +" and label= '01') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +"),4),0) ratio from dual";
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size()>0){
            countResult = countList.get(0);
            regionId = countResult.get("ratio").toString();
        }
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",regionId);
        item.put("param","");
        result.put("clear",item);

        countSql = "select ifnull(round((select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +" and label= '02') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +"),4),0) ratio from dual";
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size()>0){
            countResult = countList.get(0);
            regionId = countResult.get("ratio").toString();
        }
        item = new HashMap<>(1);
        item.put("value",regionId);
        item.put("param","");
        result.put("bad",item);
        countSql = "select ifnull(round((select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +" and status>='08') / (select count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where "+ regionParam +" and (status>='06')),4),0) ratio from dual";
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size()>0){
            countResult = countList.get(0);
            regionId = countResult.get("ratio").toString();
        }
        item = new HashMap<>(1);
        item.put("value",regionId);
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
        Long userId = SecurityUtils.getUserId();
        countSql = "select (select b.area_region from `ruoyi-vue`.sys_role b where b.role_id = a.role_id) area_region,province_id,city_id FROM `ruoyi-vue`.sys_user_role a where a.user_id = ?";
        String areaRegion = null;
        String cityId = "-1";
        String provinceId = "-1";
        List<Map<String,Object>> countList = jdbcTemplate.queryForList(countSql,userId);
        if(countList.size()>0){
            countResult = countList.get(0);
            cityId = countResult.get("city_id")!=null ? countResult.get("city_id").toString() : null;
            provinceId= countResult.get("province_id")!=null ? countResult.get("province_id").toString() :null;
            areaRegion = countResult.get("area_region").toString();
        }
        String regionParam =" 1=1 ";
        if(areaRegion.equals("01")){
            regionParam = " city = '"+cityId+"'";
        } else if (areaRegion.equals("02")){
            regionParam = " province_id = '"+provinceId+"'";
        }

        String regionId = "-1";


        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and "+ regionParam +" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-01-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql,status);
        String cnt = "0";
        String sum = "0";
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
            sum = countResult.get("sum").toString();
        }
        // 年出单
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("yearOrderCount01",item);
        item = new HashMap<>(1);
        item.put("value",sum);
        item.put("param","");
        result.put("yearOrderSum01",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and "+regionParam+" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-01-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql,"08");
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
            sum = countResult.get("sum").toString();
        }
        // 年放款
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("yearOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",sum);
        item.put("param","");
        result.put("yearOrderSum02",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and "+regionParam+" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql,status);
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
            sum = countResult.get("sum").toString();
        }
        // 月出单
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("monthOrderCount01",item);
        item = new HashMap<>(1);
        // 月出单合计金额
        double monthOrder= Double.valueOf(sum);
        item.put("value",monthOrder);
        item.put("param","");
        result.put("monthOrderSum01",item);
        countSql = "SELECT count(1) cnt,ifnull(sum(loan_amount),0) sum FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and "+regionParam+" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql,"07");
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
            sum = countResult.get("sum").toString();
        }
        // 月放款
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("monthOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",sum);
        item.put("param","");
        result.put("monthOrderSum02",item);
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where `status` >= ? and "+regionParam+" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql,"04");
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        int passed = Integer.valueOf(cnt);
        countSql = "SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_order where refuse_order_id is not null  and "+regionParam+" and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')";
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size()>0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        int refuse = Integer.valueOf(cnt);

        Float valid = 0f;
        if((passed + refuse) > 0){
            valid = (float)(passed / (passed + refuse));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(valid);
        valid = Float.parseFloat(formattedNumber);
        // 审批通过率
        item = new HashMap<>(1);
        item.put("value",valid);
        item.put("param","");
        result.put("approvePassed",item);

        if(areaRegion.equals("01")){
            regionParam = " city_id = '"+cityId+"'";
        } else if (areaRegion.equals("02")){
            regionParam = " province_id = '"+provinceId+"'";
        }
        // 月任务完成率（暂时只考虑了按月度考核）
        double amount = 0;
        countSql = "SELECT * FROM `ruoyi-vue`.baoli_biz_month_task_evaluate WHERE YEAR = DATE_FORMAT(now(), '%Y') AND MONTH = DATE_FORMAT(now(), '%m') and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size()>0){
            countResult = countList.get(0);
            amount = Double.valueOf(countResult.get("amount").toString());
        }

        if(areaRegion.equals("01")){
            regionParam = " city = '"+cityId+"'";
        } else if (areaRegion.equals("02")){
            regionParam = " province_id = '"+provinceId+"'";
        }
        double monthTaskCompleted = 0;
        if(amount!=0){
            monthTaskCompleted = (double)(monthOrder / amount);
        }
        formattedNumber = df.format(monthTaskCompleted);
        monthTaskCompleted = Double.valueOf(formattedNumber);
        item = new HashMap<>(1);
        item.put("value",monthTaskCompleted);
        item.put("param","");
        result.put("monthTaskCompleted",item);

        // 费用占比
        countSql = "SELECT   ifnull((sum(fee1+fee2+fee3+fee4) / any_value(income)),0) fee_ratio FROM `ruoyi-vue`.baoli_biz_order where  "+regionParam+" and refuse_order_id is null and create_time >= str_to_date(DATE_FORMAT(now(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s') group by city";
        if(jdbcTemplate.queryForList(countSql).size()>0){
            countResult = jdbcTemplate.queryForMap(countSql);
            amount = Double.valueOf(countResult.get("fee_ratio").toString());
        } else {
            amount = 0;
        }


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

        Long userId = SecurityUtils.getUserId();
        countSql = "select (select b.area_region from `ruoyi-vue`.sys_role b where b.role_id = a.role_id) area_region,province_id,city_id FROM `ruoyi-vue`.sys_user_role a where a.user_id = ?";
        String areaRegion = null;
        String cityId = "-1";
        String provinceId = "-1";
        List<Map<String,Object>> countList = jdbcTemplate.queryForList(countSql,userId);
        if(countList.size()>0){
            countResult = countList.get(0);
            cityId = countResult.get("city_id")!=null ? countResult.get("city_id").toString() : null;
            provinceId= countResult.get("province_id")!=null ? countResult.get("province_id").toString() :null;
            areaRegion = countResult.get("area_region").toString();
        }
        String regionParam =" 1=1 ";
        if(areaRegion.equals("01")){
            regionParam = " city_id = '"+cityId+"'";
        } else if (areaRegion.equals("02")){
            regionParam = " province_id = '"+provinceId+"'";
        }
        String regionId = null;


        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store where status = ? and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql,status);
        String cnt = "0";
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        int total =Integer.valueOf(cnt);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",total);
        item.put("param","");
        result.put("total",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(NOW(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql,status);
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        int monthOrder =Integer.valueOf(cnt);
        item = new HashMap<>(1);
        item.put("value",monthOrder);
        item.put("param","");
        result.put("monthOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and not exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(NOW(), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql,status);
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("monthNoOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store a where a.status = ? and not exists(select 1 from `ruoyi-vue`.baoli_biz_order b  where b.status >='04' and  b.store_id = a.id and b.create_time >=str_to_date(DATE_FORMAT(date_sub(NOW(), interval 3 month), '%Y-%m-01'),'%Y-%m-%d %H:%i:%s')) and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql,status);
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("threeMonthNoOrder",item);
        countSql ="SELECT count(1) cnt FROM `ruoyi-vue`.baoli_biz_store where status = ? and "+regionParam;
        countList = jdbcTemplate.queryForList(countSql,"03");
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("cnt").toString();
        }
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("except",item);

        Float valid = 0F;
        if(total!=0){
            valid = (float)(monthOrder / total);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(valid);
        valid = Float.parseFloat(formattedNumber);
        item = new HashMap<>(1);
        item.put("value",valid);
        item.put("param","");
        result.put("valid",item);

        countSql ="select ifnull((SELECT count(1) FROM `ruoyi-vue`.baoli_biz_store where "+regionParam+" and (status = '01' or exception_reason ='01')) / (SELECT count(1) FROM `ruoyi-vue`.baoli_biz_store where "+regionParam+"),0)  expand from dual";
        countList = jdbcTemplate.queryForList(countSql);
        if(countList.size() >0){
            countResult = countList.get(0);
            cnt = countResult.get("expand").toString();
        }
        item = new HashMap<>(1);
        item.put("value",cnt);
        item.put("param","");
        result.put("expand",item);
        return result;
    }
}
