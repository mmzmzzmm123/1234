package com.stdiet.custom.controller;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.service.ISysOrderService;
import org.aspectj.weaver.loadtime.Aj;
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
import com.stdiet.custom.domain.SysOrderNutritionistReplaceRecord;
import com.stdiet.custom.service.ISysOrderNutritionistReplaceRecordService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 订单营养师、售后更换记录Controller
 *
 * @author xzj
 * @date 2021-04-02
 */
@RestController
@RequestMapping("/custom/orderNutritionistReplaceRecord")
public class SysOrderNutritionistReplaceRecordController extends BaseController
{
    @Autowired
    private ISysOrderNutritionistReplaceRecordService sysOrderNutritionistReplaceRecordService;

    @Autowired
    private ISysOrderService sysOrderService;

    /**
     * 查询订单营养师、售后更换记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        startPage();
        List<SysOrderNutritionistReplaceRecord> list = sysOrderNutritionistReplaceRecordService.selectSysOrderNutritionistReplaceRecordList(sysOrderNutritionistReplaceRecord);
        return getDataTable(list);
    }

    /**
     * 导出订单营养师、售后更换记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:export')")
    @Log(title = "订单营养师、售后更换记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        List<SysOrderNutritionistReplaceRecord> list = sysOrderNutritionistReplaceRecordService.selectSysOrderNutritionistReplaceRecordList(sysOrderNutritionistReplaceRecord);
        ExcelUtil<SysOrderNutritionistReplaceRecord> util = new ExcelUtil<SysOrderNutritionistReplaceRecord>(SysOrderNutritionistReplaceRecord.class);
        return util.exportExcel(list, "orderNutritionistReplaceRecord");
    }

    /**
     * 获取订单营养师、售后更换记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysOrderNutritionistReplaceRecordService.selectSysOrderNutritionistReplaceRecordById(id));
    }

    /**
     * 新增订单营养师、售后更换记录
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:add')")
    @Log(title = "订单营养师、售后更换记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        if(sysOrderNutritionistReplaceRecord.getOrderId() == null || sysOrderNutritionistReplaceRecord.getOrderId().longValue() <= 0){
            return AjaxResult.error("订单ID不存在");
        }
        if(sysOrderNutritionistReplaceRecord.getAfterSaleId() == null || sysOrderNutritionistReplaceRecord.getAfterSaleId().longValue() <= 0){
            sysOrderNutritionistReplaceRecord.setAfterSaleId(null);
        }
        if(sysOrderNutritionistReplaceRecord.getNutritionistId() == null || sysOrderNutritionistReplaceRecord.getNutritionistId().longValue() <= 0){
            sysOrderNutritionistReplaceRecord.setNutritionistId(null);
        }
        if((sysOrderNutritionistReplaceRecord.getAfterSaleId() == null || sysOrderNutritionistReplaceRecord.getAfterSaleId().longValue() <= 0)
            && (sysOrderNutritionistReplaceRecord.getNutritionistId() == null || sysOrderNutritionistReplaceRecord.getNutritionistId().longValue() <= 0)){
            return AjaxResult.error("营养师、售后不能都为空");
        }
        SysOrder sysOrder = sysOrderService.selectSysOrderById(sysOrderNutritionistReplaceRecord.getOrderId());
        if(sysOrder == null){
            return AjaxResult.error("订单不存在");
        }
        if(sysOrderNutritionistReplaceRecord.getStartTime() == null){
            return AjaxResult.error("开始时间不能为空");
        }
        //更换的开始时间不能小于订单开始时间
        if(ChronoUnit.DAYS.between(DateUtils.dateToLocalDate(sysOrderNutritionistReplaceRecord.getStartTime()),DateUtils.dateToLocalDate(sysOrder.getCommissStartTime())) > 0){
            return AjaxResult.error("开始时间不能小于"+ DateUtils.dateTime(sysOrder.getCommissStartTime()));
        }
        //更换的售后或营养师不能与原订单一致
//        if(sysOrderNutritionistReplaceRecord.getNutritionistId() != null && sysOrder.getNutritionistId().longValue() == sysOrderNutritionistReplaceRecord.getNutritionistId().longValue()){
//            return AjaxResult.error("更换的营养师与原订单营养师不能相同");
//        }

//        if(sysOrderNutritionistReplaceRecord.getAfterSaleId() != null && sysOrder.getAfterSaleId().longValue() == sysOrderNutritionistReplaceRecord.getAfterSaleId().longValue()){
//            return AjaxResult.error("更换的售后与原订单售后不能相同");
//        }

        //体验单、二开售后提成单都不支持更换营养师和售后
        if(sysOrder.getAfterSaleCommissOrder().intValue() == 1 || "2".equals(sysOrder.getOrderType())){
            return AjaxResult.error("二开提成单、体验单不支持更换营养师、售后");
        }
        return toAjax(sysOrderNutritionistReplaceRecordService.insertSysOrderNutritionistReplaceRecord(sysOrderNutritionistReplaceRecord));
    }

    /**
     * 删除订单营养师、售后更换记录
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:remove')")
    @Log(title = "订单营养师、售后更换记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysOrderNutritionistReplaceRecordService.deleteSysOrderNutritionistReplaceRecordByIds(ids));
    }
}