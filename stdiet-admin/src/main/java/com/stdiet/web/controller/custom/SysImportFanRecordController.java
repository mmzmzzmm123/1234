package com.stdiet.web.controller.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysImportFanWxAccount;
import com.stdiet.custom.domain.SysWxSaleAccount;
import com.stdiet.custom.service.ISysImportFanWxAccountService;
import com.stdiet.custom.service.ISysWxSaleAccountService;
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
import com.stdiet.custom.domain.SysImportFanRecord;
import com.stdiet.custom.service.ISysImportFanRecordService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 导粉管理Controller
 *
 * @author xzj
 * @date 2021-05-17
 */
@RestController
@RequestMapping("/custom/importFanRecord")
public class SysImportFanRecordController extends BaseController
{
    @Autowired
    private ISysImportFanRecordService sysImportFanRecordService;

    @Autowired
    private ISysWxSaleAccountService sysWxSaleAccountService;

    @Autowired
    private ISysImportFanWxAccountService sysImportFanWxAccountService;

    /**
     * 查询导粉管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysImportFanRecord sysImportFanRecord)
    {
        startPage();
        List<SysImportFanRecord> list = sysImportFanRecordService.selectSysImportFanRecordList(sysImportFanRecord);
        SysImportFanWxAccount sysImportFanWxAccount = new SysImportFanWxAccount();
        if(list != null && list.size() > 0){
            //总导粉量
            int totalNum = sysImportFanRecordService.selectTotalSysImportFanNum(sysImportFanRecord);
            list.get(0).setTotalFanNum(totalNum);
        }
        return getDataTable(list);
    }

    /**
     * 导出导粉管理列表
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:export')")
    @Log(title = "导粉管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysImportFanRecord sysImportFanRecord)
    {
        List<SysImportFanRecord> list = sysImportFanRecordService.selectSysImportFanRecordList(sysImportFanRecord);
        ExcelUtil<SysImportFanRecord> util = new ExcelUtil<SysImportFanRecord>(SysImportFanRecord.class);
        return util.exportExcel(list, "importFanRecord");
    }

    /**
     * 获取导粉管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysImportFanRecordService.selectSysImportFanRecordById(id));
    }

    /**
     * 新增导粉管理
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:add')")
    @Log(title = "导粉管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysImportFanRecord sysImportFanRecord)
    {
        if(sysImportFanRecord.getImportFanDate() == null){
            return AjaxResult.error("导入日期不存在");
        }
        int row = 0;
        //批量保存
        if(StringUtils.isNotEmpty(sysImportFanRecord.getImportFanChannels() )){
            String[] importFanChannelArray = sysImportFanRecord.getImportFanChannels().split(",");
            String[] importFanLiveArray = sysImportFanRecord.getImportFanLives().split(",");
            String[] wxAccountIdArray = sysImportFanRecord.getWxAccountIds().split(",");
            String[] fanNumArray = sysImportFanRecord.getFanNums().split(",");
            int index = -1;
            for (String importFanChannel : importFanChannelArray) {
                index++;
                if(StringUtils.isEmpty(importFanChannel)){
                    continue;
                }
                sysImportFanRecord.setImportFanChannel(Long.parseLong(importFanChannel));
                sysImportFanRecord.setImportFanLive(StringUtils.isEmpty(importFanLiveArray[index]) ? 0L : Long.parseLong(importFanLiveArray[index]));
                sysImportFanRecord.setWxAccountId(Long.parseLong(wxAccountIdArray[index]));
                sysImportFanRecord.setFanNum(Long.parseLong(fanNumArray[index]));
                row = sysImportFanRecordService.insertSysImportFanRecord(sysImportFanRecord);
            }
        }else{
            if(sysImportFanRecord.getImportFanChannel() != null){
                row = sysImportFanRecordService.insertSysImportFanRecord(sysImportFanRecord);
            }
        }
        return toAjax(row);
    }

    /**
     * 修改导粉管理
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:edit')")
    @Log(title = "导粉管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysImportFanRecord sysImportFanRecord)
    {
        return toAjax(sysImportFanRecordService.updateSysImportFanRecord(sysImportFanRecord));
    }

    /**
     * 删除导粉管理
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:remove')")
    @Log(title = "导粉管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysImportFanRecordService.deleteSysImportFanRecordByIds(ids));
    }

    /**
     * 获取可接粉的微信号以及对应销售
     * @return
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:add')")
    @GetMapping(value = "/getWxAccountAndSale")
    public AjaxResult getWxAccountAndSale(SysWxSaleAccount sysWxSaleAccount){
        Map<String, Object> result = new HashMap<>();
        List<SysWxSaleAccount> list = sysWxSaleAccountService.getWxAccountAndSale(sysWxSaleAccount);
        //List<Map<String,Object>> fanNumList = sysImportFanWxAccountService.getTotalImportFanNum(sysImportFanWxAccount);
        result.put("wxSaleAccountList", list);
        //result.put("fanNumList", fanNumList);
        return AjaxResult.success(result);
    }

    /**
     * 删除导粉记录中对应微信记录
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:remove')")
    @Log(title = "删除导粉记录中对应微信记录", businessType = BusinessType.DELETE)
    @GetMapping("/removeFanWxAccount/{ids}")
    public AjaxResult removeFanWxAccount(@PathVariable Long[] ids)
    {
        return toAjax(sysImportFanWxAccountService.deleteSysImportFanWxAccountByIds(ids));
    }

    /**
     * 删除导粉记录中对应微信记录
     */
    @PreAuthorize("@ss.hasPermi('custom:importFanRecord:edit')")
    @GetMapping("/saveWxAccountFanNum")
    public AjaxResult saveWxAccountFanNum(SysImportFanWxAccount sysImportFanWxAccount)
    {
        return toAjax(sysImportFanWxAccountService.updateSysImportFanWxAccount(sysImportFanWxAccount));
    }

}
