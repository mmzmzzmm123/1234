package com.stdiet.web.controller.custom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCommissionDayDetail;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderCommisionDayDetail;
import com.stdiet.custom.dto.request.SysOrderCommision;
import com.stdiet.custom.service.ISysCommissionDayService;
import com.stdiet.custom.service.ISysNutritionalVideoService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.impl.SysCommissionDayServiceImpl;
import com.stdiet.framework.web.domain.server.Sys;
import com.stdiet.system.domain.CusSalesman;
import com.stdiet.system.service.ISysUserService;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.service.ISysCommisionService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 业务提成比例Controller
 *
 * @author wonder
 * @date 2020-09-24
 */
@RestController
@RequestMapping("/custom/commision")
public class SysCommisionController extends BaseController {

    @Autowired
    private ISysCommisionService sysCommisionService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysCommissionDayService sysCommissionDayService;

    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;

    /**
     * 查询业务提成比例列表
     */
    @PreAuthorize("@ss.hasPermi('commision:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCommision sysCommision) {
        startPage();
        List<SysCommision> list = sysCommisionService.selectSysCommisionList(sysCommision);
        return getDataTable(list);
    }

    /**
     * 导出业务提成比例列表
     */
    @PreAuthorize("@ss.hasPermi('commision:config:export')")
    @Log(title = "业务提成比例", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCommision sysCommision) {
//        List<SysCommision> list = sysCommisionService.selectSysCommisionList(sysCommision);
        ExcelUtil<SysCommision> util = new ExcelUtil<SysCommision>(SysCommision.class);
        List<SysCommision> list = sysCommisionService.selectSysCommisionDetail(sysCommision);
        for (SysCommision detail : list) {
            detail.setRate(0F);
            SysCommision tmpQueryCom = new SysCommision();
            tmpQueryCom.setUserId(detail.getUserId());
            tmpQueryCom.setPostId(detail.getPostId());
            List<SysCommision> tmpComList = sysCommisionService.selectSysCommisionList(tmpQueryCom);

            float dAmount = detail.getAmount().floatValue();
            for (int i = 0; i < tmpComList.size(); i++) {
                SysCommision com = tmpComList.get(i);
                float cAmount = com.getAmount().floatValue();
                if (dAmount <= cAmount && i == 0) {
                    // 第一条规则
                    detail.setRate(com.getRate());
                    break;
                } else if (i == tmpComList.size() - 1 && dAmount > cAmount) {
                    // 最后一条规则
                    detail.setRate(com.getRate());
                    break;
                } else if (cAmount < dAmount && dAmount <= tmpComList.get(i + 1).getAmount().floatValue()) {
                    // 中间规则
                    detail.setRate(tmpComList.get(i + 1).getRate());
                    break;
                }
            }

            float amount = detail.getAmount().floatValue();
            amount = amount * detail.getRate() / 100F;
            detail.setCommision(new BigDecimal(amount));
        }

        return util.exportExcel(list, "commision");
    }

    /**
     * 获取业务提成比例详细信息
     */
    @PreAuthorize("@ss.hasPermi('commision:config:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId, SysCommision sysCommision) {
        sysCommision.setRuleId(ruleId);
//        System.out.println(sysCommision);
        return AjaxResult.success(sysCommisionService.selectSysCommisionById(sysCommision));
    }

    /**
     * 新增业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('commision:config:add')")
    @Log(title = "业务提成比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCommision sysCommision) {
        return toAjax(sysCommisionService.insertSysCommision(sysCommision));
    }

    /**
     * 修改业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('commision:config:edit')")
    @Log(title = "业务提成比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCommision sysCommision) {
        return toAjax(sysCommisionService.updateSysCommision(sysCommision));
    }

    /**
     * 删除业务提成比例
     */
    @PreAuthorize("@ss.hasPermi('commision:config:remove')")
    @Log(title = "业务提成比例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds) {
        return toAjax(sysCommisionService.deleteSysCommisionByIds(ruleIds));
    }

    @GetMapping("/salesman")
    public AjaxResult getSalesman() {
        AjaxResult ajax = AjaxResult.success();
        List<CusSalesman> list = userService.selectSalesman();
        ajax.put(AjaxResult.DATA_TAG, list);
        return ajax;
    }

    @PreAuthorize("@ss.hasPermi('commision:detail:list')")
    @GetMapping("/detail")
    public TableDataInfo getDetail(SysCommision sysCommision) {
        startPage();
        if(sysCommision.getPostId() != null && sysCommision.getPostId().intValue() > 0){
            String postCodeId = "";
            switch (sysCommision.getPostId().intValue()){
                case 5:
                    postCodeId = "pre_sale_id";
                    break;
                case 18:
                    postCodeId = "push_pre_sale_id";
                    break;
                case 16:
                    postCodeId = "on_sale_id";
                    break;
                case 6:
                    postCodeId = "after_sale_id";
                    break;
                case 9:
                    postCodeId = "nutritionist_id";
                    break;
                case 10:
                    postCodeId = "nutri_assis_id";
                    break;
                case 11:
                    postCodeId = "operator_id";
                    break;
                case 13:
                    postCodeId = "operator_assis_id";
                    break;
                case 7:
                    postCodeId = "planner_id";
                    break;
                case 8:
                    postCodeId = "planner_assis_id";
                    break;
                default:
            }
            if(StringUtils.isEmpty(postCodeId)){
                sysCommision.setPostId(null);
            }else{
                sysCommision.setPostCode(postCodeId);
            }
        }
        List<SysCommision> list = sysCommisionService.selectSysCommisionDetail(sysCommision);
        for (SysCommision detail : list) {
            detail.setRate(0F);
            SysCommision tmpQueryCom = new SysCommision();
            tmpQueryCom.setUserId(detail.getUserId());
            tmpQueryCom.setPostId(detail.getPostId());
            List<SysCommision> tmpComList = sysCommisionService.selectSysCommisionList(tmpQueryCom);
            //按比例开始时间分类
            Map<String, List<SysCommision>> rateYearMonthMap = sysCommissionDayService.getRateMapByStartTime(tmpComList);
            String yearMonth = DateUtils.stringToLocalDate(sysCommision.getBeginTime(),"yyyy-MM-dd").getYear() + "" + DateUtils.stringToLocalDate(sysCommision.getBeginTime(),"yyyy-MM-dd").getMonth().getValue();
            List<SysCommision> yearMonthRateList = new ArrayList<>();
            for (String rateMonth : rateYearMonthMap.keySet()) {
                if(Long.parseLong(yearMonth) >= Long.parseLong(rateMonth)){
                    yearMonthRateList = rateYearMonthMap.get(rateMonth);
                }else{
                    break;
                }
            }
            float dAmount = detail.getAmount().floatValue();
            for (int i = 0; i < yearMonthRateList.size(); i++) {
                SysCommision com = yearMonthRateList.get(i);
                float cAmount = com.getAmount().floatValue();
                Long rateStartYearMonth = null;
                if(com.getStartTime() != null){
                    rateStartYearMonth = Long.parseLong(DateUtils.dateToLocalDate(com.getStartTime()).getYear() + "" + DateUtils.dateToLocalDate(com.getStartTime()).getMonth().getValue());
                }else{
                    rateStartYearMonth = 19001L;
                }
                if (dAmount <= cAmount && i == 0 && Long.parseLong(yearMonth) >= rateStartYearMonth) {
                    // 第一条规则
                    detail.setRate(com.getRate());
                    break;
                } else if (i == yearMonthRateList.size() - 1 && dAmount > cAmount && Long.parseLong(yearMonth) >= rateStartYearMonth) {
                    // 最后一条规则
                    detail.setRate(com.getRate());
                    break;
                } else if (cAmount < dAmount && dAmount <= yearMonthRateList.get(i + 1).getAmount().floatValue() && Long.parseLong(yearMonth) >= rateStartYearMonth) {
                    // 中间规则
                    detail.setRate(yearMonthRateList.get(i + 1).getRate());
                    break;
                }
            }

            float amount = detail.getAmount().floatValue();
            amount = amount * detail.getRate() / 100F;
            detail.setCommision(new BigDecimal(amount));

        }
        return getDataTable(list);
    }

    /**
     * 按天计算提成详细列表
     * */
    @PreAuthorize("@ss.hasPermi('commisionDay:detail:list')")
    @GetMapping("/detailDay")
    public TableDataInfo getDetailDay(SysCommision sysCommision) {
        int count = sysCommissionDayService.getAfterSaleAndNutriCount(sysCommision);
        List<SysCommissionDayDetail> list = null;
        if(count > 0){
            startPage();
            list = sysCommissionDayService.calculateCommissionByDay(sysCommision);
        }else{
            list = new ArrayList<>();
        }
        return new TableDataInfo(list, count);
    }

    /**
     * 导出按天计算提成详细列表
     */
    @PreAuthorize("@ss.hasPermi('commisionDay:detail:export')")
    @Log(title = "导出按天计算提成详细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailDay")
    public AjaxResult exportDetailDay(SysCommision sysCommision) {
        ExcelUtil<SysCommissionDayDetail> util = new ExcelUtil<>(SysCommissionDayDetail.class);
        List<SysCommissionDayDetail> list = sysCommissionDayService.calculateCommissionByDay(sysCommision);
        return util.exportExcel(list, "commision");
    }

    /**
     * 根据用户ID查询该用户按天计算提成的详情
     * */
    @PreAuthorize("@ss.hasPermi('commisionDay:detail:list')")
    @GetMapping("/orderDetailDay")
    public AjaxResult getOrderCommissionDetailDay(SysCommision sysCommision) {
        sysCommision.setReplaceOrderFlag(true);
        startPage();
        return sysCommissionDayService.calculateOrderCommissionDetail(sysCommision);
    }

    /**
     * 导出具体地点按天计算提成详细列表
     */
    @PreAuthorize("@ss.hasPermi('commisionDay:detail:export')")
    @Log(title = "导出具体地点按天计算提成详细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportOrderDetailDay")
    public AjaxResult exportOrderDetailDay(SysCommision sysCommision) {
        ExcelUtil<SysOrderCommisionDayDetail> util = new ExcelUtil<>(SysOrderCommisionDayDetail.class);
        AjaxResult result = sysCommissionDayService.calculateOrderCommissionDetail(sysCommision);
        List<SysOrderCommisionDayDetail> orderDetailList = null;
        if(result != null && (int)result.get("code") == 200){
            orderDetailList = (List<SysOrderCommisionDayDetail>)result.get("list");
        }
        return util.exportExcel(orderDetailList == null ? new ArrayList<>() : orderDetailList, "commision");
    }
}
