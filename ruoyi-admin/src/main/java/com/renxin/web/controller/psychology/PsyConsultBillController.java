package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.StringUtils;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.psychology.domain.PsyConsultBill;
import com.renxin.psychology.domain.PsyConsultBillItem;
import com.renxin.psychology.dto.BillItemDTO;
import com.renxin.psychology.request.PsyAdminBillReq;
import com.renxin.psychology.service.IPsyConsultBillItemService;
import com.renxin.psychology.service.IPsyConsultBillService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理咨询账单Controller
 * 
 * @author renxin
 * @date 2023-11-07
 */
@RestController
@RequestMapping("/psychology/bill")
public class PsyConsultBillController extends BaseController
{

    @Resource
    private IPsyConsultBillService service;

    @Resource
    private IPsyConsultBillItemService itemService;

    /**
     * 查询咨询师账单
     */
    @PreAuthorize("@ss.hasPermi('psychology:bill:list')")
    @GetMapping("/list")
    public TableDataInfo getList(PsyAdminBillReq req)
    {
        startPage();
        List<PsyConsultBill> list = service.getList(req);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('psychology:bill:export')")
    @Log(title = "心理咨询账单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyAdminBillReq req)
    {
        List<PsyConsultBill> list = service.getList(req);
        ExcelUtil<PsyConsultBill> util = new ExcelUtil<PsyConsultBill>(PsyConsultBill.class);
        util.exportExcel(response, list, "心理咨询账单数据");
    }

    @PreAuthorize("@ss.hasPermi('psychology:bill:edit')")
    @Log(title = "心理咨询账单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultBill req)
    {
        if (StringUtils.isBlank(req.getRemark())) {
            return AjaxResult.error("备注不能为空");
        }

        if (!isAdmin()) {
            return AjaxResult.error("没权限");
        }

        service.edit(req);
        return AjaxResult.success("保存成功");
    }


    @PreAuthorize("@ss.hasPermi('psychology:bill:list')")
    @GetMapping("/getItemListForDetail")
    public TableDataInfo getItemListForDetail(PsyAdminBillReq req)
    {
        startPage();
        List<PsyConsultBillItem> list = itemService.getItemListForDetail(req);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('psychology:bill:list')")
    @GetMapping(value = "/getBillItemSum")
    public AjaxResult getBillItemSum(PsyAdminBillReq req)
    {
        return AjaxResult.success(itemService.getBillItemSum(req));
    }

    @PreAuthorize("@ss.hasPermi('psychology:bill:export')")
    @Log(title = "心理咨询账单", businessType = BusinessType.EXPORT)
    @PostMapping("/exportItemsForDetail")
    public void exportItemsForDetail(HttpServletResponse response, PsyAdminBillReq req)
    {
        List<PsyConsultBillItem> list = itemService.getItemListForDetail(req);
        ExcelUtil<PsyConsultBillItem> util = new ExcelUtil<PsyConsultBillItem>(PsyConsultBillItem.class);
        util.exportExcel(response, list, "心理咨询账单数据");
    }

    /**
     * 查询咨询订单核销记录
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:hxQuery')")
    @GetMapping("/getItemList")
    public TableDataInfo getItemList(PsyAdminBillReq req)
    {
        startPage();
        List<BillItemDTO> list = itemService.getItemList(req);
        return getDataTable(list);
    }

    /**
     * 导出咨询订单核销记录
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:hxExport')")
    @Log(title = "核销记录导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exportItems")
    public void exportItems(HttpServletResponse response, PsyAdminBillReq req)
    {
        List<BillItemDTO> list = itemService.getItemList(req);
        ExcelUtil<BillItemDTO> util = new ExcelUtil<BillItemDTO>(BillItemDTO.class);
        util.exportExcel(response, list, "核销记录");
    }

}
