package com.stdiet.web.controller.custom;

import java.time.temporal.ChronoUnit;
import java.util.List;

import com.stdiet.common.core.domain.entity.SysUser;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.dto.request.AddMuchReplaceRecordRequest;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysUserService sysUserService;

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

    /**
     * 批量更新营养师、售后、助理
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:muchAdd')")
    @Log(title = "批量添加更换订单营养师、售后、助理更换记录", businessType = BusinessType.INSERT)
    @PostMapping("/addMuchReplaceRecord")
    public AjaxResult addMuchReplaceRecord(MultipartFile file)
    {
        ExcelUtil<AddMuchReplaceRecordRequest> util = new ExcelUtil<>(AddMuchReplaceRecordRequest.class);
        try{
            List<AddMuchReplaceRecordRequest> recordList = util.importExcel(file.getInputStream());
            if(recordList == null || recordList.size() == 0){
                return AjaxResult.error("数据验证失败，数据为空");
            }
            //检查数据格式是否正确：客户手机号以及姓名不能都为空而且能查到对应客户ID；营养师、售后、助理不能都为空，而且都能查到对应用户ID；开始时间不能为空
            int index = 0;
            for (AddMuchReplaceRecordRequest record : recordList) {
                index++;
                if(StringUtils.isAllEmpty(record.getCustomerName(), record.getPhone(), record.getNutritionist(), record.getAfterSale(), record.getNutriAssis()) && record.getStartTime() == null){
                    continue;
                }
                if(StringUtils.isAllEmpty(record.getCustomerName(), record.getPhone()) || StringUtils.isAllEmpty(record.getNutritionist(), record.getAfterSale(), record.getNutriAssis())
                    || record.getStartTime() == null){
                    return AjaxResult.error("数据验证失败，第"+ index + "条数据格式错误");
                }
                SysCustomer sysCustomer = null;
                //查询客户ID
                if(StringUtils.isNotEmpty(record.getPhone())){
                    sysCustomer =  sysCustomerService.getCustomerByPhone(record.getPhone());
                }else{
                    //根据客户姓名查询必须确定只能查询到唯一用户
                    List<SysCustomer> cusList =  sysCustomerService.getCustomerByName(record.getCustomerName());
                    if(cusList != null && cusList.size() == 1){
                        sysCustomer = cusList.get(0);
                    }
                }
                if(sysCustomer == null){
                    return AjaxResult.error("数据验证失败，第"+ index + "条数据无法找到唯一客户");
                }
                record.setCustomerId(sysCustomer.getId());
                //查询营养师
                if(StringUtils.isNotEmpty(record.getNutritionist())){
                    List<SysUser> nutritionistUserList = sysUserService.getUserByNickName(record.getNutritionist());
                    if(nutritionistUserList == null || nutritionistUserList.size() != 1){
                        return AjaxResult.error("数据验证失败，第"+ index + "条数据无法找到唯一营养师");
                    }
                    record.setNutritionistId(nutritionistUserList.get(0).getUserId());
                }
                //查询助理
                if(StringUtils.isNotEmpty(record.getNutriAssis())){
                    List<SysUser> nutriAssisUserList = sysUserService.getUserByNickName(record.getNutriAssis());
                    if(nutriAssisUserList == null || nutriAssisUserList.size() != 1){
                        return AjaxResult.error("数据验证失败，第"+ index + "条数据无法找到唯一助理");
                    }
                    record.setNutriAssisId(nutriAssisUserList.get(0).getUserId());
                }
                //查询售后
                if(StringUtils.isNotEmpty(record.getAfterSale())){
                    List<SysUser> afterUserList = sysUserService.getUserByNickName(record.getAfterSale());
                    if(afterUserList == null || afterUserList.size() != 1){
                        return AjaxResult.error("数据验证失败，第"+ index + "条数据无法找到唯一售后");
                    }
                    record.setAfterSaleId(afterUserList.get(0).getUserId());
                }
            }
            return sysOrderNutritionistReplaceRecordService.addMuchReplaceRecord(recordList);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("批量添加出现异常，请检查文件数据格式");
        }
    }

    /**
     * 导出模板
     */
    @PreAuthorize("@ss.hasPermi('custom:orderNutritionistReplaceRecord:muchAdd')")
    @GetMapping("/downAddMuchReplaceRecordTemplate")
    public AjaxResult downAddMuchReplaceRecordTemplate()
    {
        ExcelUtil<AddMuchReplaceRecordRequest> util = new ExcelUtil<>(AddMuchReplaceRecordRequest.class);
        return util.importTemplateExcel("批量更新营养师售后助理模板");
    }
}