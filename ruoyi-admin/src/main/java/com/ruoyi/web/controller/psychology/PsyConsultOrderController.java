package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.request.PsyAdminOrderReq;
import com.ruoyi.psychology.request.PsyHxOrderReq;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询订单Controller
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/psychology/order")
public class PsyConsultOrderController extends BaseController
{
    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @PreAuthorize("@ss.hasPermi('psychology:order:list')")
    @GetMapping(value = "/getDetail/{id}")
    public AjaxResult getDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetail(id));
    }

    /**
     * 查询咨询订单列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyAdminOrderReq req)
    {
        startPage();
        List<PsyConsultOrder> list = psyConsultOrderService.getList(req);
        return getDataTable(list);
    }

    /**
     * 导出咨询订单列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:export')")
    @Log(title = "咨询订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyAdminOrderReq req)
    {
        List<PsyConsultOrder> list = psyConsultOrderService.getList(req);
        ExcelUtil<PsyConsultOrder> util = new ExcelUtil<PsyConsultOrder>(PsyConsultOrder.class);
        util.exportExcel(response, list, "咨询服务数据");
    }

    /**
     * 获取咨询订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOne(id));
    }


    /**
     * 修改咨询订单
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:edit')")
    @Log(title = "咨询订单", businessType = BusinessType.UPDATE)
    @PostMapping("/hx")
    public AjaxResult hx(@RequestBody PsyHxOrderReq req)
    {
        String hx = psyConsultOrderService.hx(req);
        return "ok".equals(hx) ? AjaxResult.success() : AjaxResult.error(hx);
    }

    /**
     * 新增咨询订单
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:add')")
    @Log(title = "咨询订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultOrderVO req)
    {
        return toAjax(psyConsultOrderService.add(req));
    }

    /**
     * 修改咨询订单
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:edit')")
    @Log(title = "咨询订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultOrderVO req)
    {
        return toAjax(psyConsultOrderService.update(req));
    }

    // finish
    @PreAuthorize("@ss.hasPermi('psychology:order:edit')")
    @Log(title = "咨询订单", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/finish/{id}")
    public AjaxResult finish(@PathVariable Long id)
    {
        PsyConsultOrderVO order = psyConsultOrderService.getOne(id);
        order.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_FINISHED);
        return toAjax(psyConsultOrderService.update(order));
    }


    /**
     * 删除咨询订单
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:remove')")
    @Log(title = "咨询订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultOrderService.deleteAll(ids));
    }
}
