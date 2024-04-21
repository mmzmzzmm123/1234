package com.baoli.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistic/homePage")
public class HomePageStatisticController {
    @Autowired
    private JdbcTemplate JdbcTemplate;

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
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",10);
        item.put("param","");
        result.put("notComplete",item);
        item = new HashMap<>(1);
        item.put("value",10);
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
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",0.85);
        item.put("param","");
        result.put("clear",item);
        item = new HashMap<>(1);
        item.put("value",0.05);
        item.put("param","");
        result.put("bad",item);
        item = new HashMap<>(1);
        item.put("value",0.6);
        item.put("param","");
        result.put("mortgage",item);
        return result;
    }

    /**
     * 业务数据
     * @return
     */
    private Map<String,Object> bizData(){
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        // 年出单
        result.put("yearOrderCount01",item);
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("yearOrderSum01",item);
        // 年放款
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("yearOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("yearOrderSum02",item);
        // 月出单
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("monthOrderCount01",item);
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("monthOrderSum01",item);
        // 月放款
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("monthOrderCount02",item);
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("monthOrderSum02",item);
        // 审批通过率
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("approvePassed",item);
        // 月任务完成率
        item = new HashMap<>(1);
        item.put("value",20);
        item.put("param","");
        result.put("monthTaskCompleted",item);
        return result;
    }

    // 商户数据
    private Map<String,Object> storeData(){
        Map<String,Object> result = new HashMap<>(1);
        Map<String,Object> item = null;
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("total",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("monthOrder",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("monthNoOrder",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("threeMonthNoOrder",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("except",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("valid",item);
        item = new HashMap<>(1);
        item.put("value",1500);
        item.put("param","");
        result.put("expand",item);
        return result;
    }
}
