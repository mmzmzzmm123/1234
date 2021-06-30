package com.stdiet.web.controller.common;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysPhysicalSigns;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;
import com.stdiet.custom.dto.request.FoodHeatCalculatorRequest;
import com.stdiet.custom.service.*;
import com.stdiet.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户相关信息调查Controller
 *
 * @author xzj
 * @date 2020-12-31
 */
@RestController
@RequestMapping("/investigate")
public class InvestigateController extends BaseController {

    @Autowired
    private ISysCustomerPhysicalSignsService sysCustomerPhysicalSignsService;

    @Autowired
    private ISysPhysicalSignsService iSysPhysicalSignsService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private ISysCustomerHealthyService sysCustomerHealthyService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysFoodHeatStatisticsService sysFoodHeatStatisticsService;

    @Autowired
    private ISysOrderService sysOrderService;

    /**
     * 建立客户信息档案
     */
    @PostMapping("/customerInvestigate")
    public AjaxResult customerInvestigate(@RequestBody CustomerInvestigateRequest customerInvestigateRequest) throws Exception
    {
        return AjaxResult.error("请填写新版健康评估表");
        //customerInvestigateRequest.setId(null); //只能添加，无法修改
        //return sysCustomerPhysicalSignsService.addOrupdateCustomerAndSign(customerInvestigateRequest);
    }

    /**
     * 获取体征列表
     */
    @GetMapping("/physicalSignsList")
    public TableDataInfo physicalSignsList() throws Exception
    {
        List<SysPhysicalSigns>  physicalSignsList =  iSysPhysicalSignsService.selectSysPhysicalSignsList(new SysPhysicalSigns());
        //System.out.println(physicalSignsList.size());
        return getDataTable(physicalSignsList);
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        return AjaxResult.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 根据加密ID获取客户基本信息
     * @param enc_id
     * @return
     */
    @GetMapping("/getCustomerBaseMessage/{id}")
    public AjaxResult getCustomerBaseMessage(@PathVariable(value = "id") String enc_id){
        String id = StringUtils.isEmpty(enc_id) ? "" : AesUtils.decrypt(enc_id, null);
        if(StringUtils.isNotEmpty(id)){
            SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(Long.parseLong(id));
            if(sysCustomer != null){
                Map<String, Object> result = new HashMap<>();
                result.put("name", sysCustomer.getName());
                result.put("phone", sysCustomer.getPhone());
                //查询下单对应调理项目
                SysOrder order = sysOrderService.getLastOrderByCusId(Long.parseLong(id));
                result.put("projectId", (order != null && order.getConditioningProjectId() != null) ? order.getConditioningProjectId() : null);
                return AjaxResult.success(result);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 新增客户健康
     */
    @PostMapping("/addCustomerHealthy")
    public AjaxResult addCustomerHealthy(@RequestBody SysCustomerHealthy sysCustomerHealthy)
    {
        return sysCustomerHealthyService.insertSysCustomerHealthy(sysCustomerHealthy);
    }

    /**
     * 新增客户外食计算统计
     */
    @PostMapping("/addFoodHeatStatistics")
    public AjaxResult addFoodHeatStatistics(@RequestBody FoodHeatCalculatorRequest foodHeatCalculatorRequest)
    {
        return toAjax(sysFoodHeatStatisticsService.addMuchFoodHeat(foodHeatCalculatorRequest));
    }

}
