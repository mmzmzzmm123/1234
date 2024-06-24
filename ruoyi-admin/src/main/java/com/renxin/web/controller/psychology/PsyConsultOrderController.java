package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.psychology.constant.ConsultConstant;
import com.renxin.psychology.domain.PsyConsultOrder;
import com.renxin.psychology.request.PsyAdminOrderReq;
import com.renxin.psychology.request.PsyHxOrderReq;
import com.renxin.psychology.request.PsyRefOrderReq;
import com.renxin.psychology.service.IPsyConsultOrderService;
import com.renxin.psychology.vo.PsyConsultOrderVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询订单Controller
 * 
 * @author renxin
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/psychology/order")
public class PsyConsultOrderController extends BaseController
{
    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @PreAuthorize("@ss.hasPermi('psychology:order:query')")
    @GetMapping(value = "/getDetail/{id}")
    public AjaxResult getDetail(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.getOrderDetail(id));
    }

    @GetMapping(value = "/getLogs/{id}")
    public AjaxResult getLogs(@PathVariable("id") String orderNo)
    {
        return AjaxResult.success(psyConsultOrderService.getLogs(orderNo));
    }

    /**
     * 查询咨询订单列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:query')")
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
     * 订单核销
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:edit')")
    @Log(title = "咨询订单", businessType = BusinessType.HX)
    @PostMapping("/hx")
    public AjaxResult hx(@RequestBody PsyHxOrderReq req)
    {
        String hx = psyConsultOrderService.hx(req);
        return "ok".equals(hx) ? AjaxResult.success() : AjaxResult.error(hx);
    }

    /**
     * 订单改价
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:price')")
    @Log(title = "咨询订单", businessType = BusinessType.PRICE)
    @PostMapping("/modifyPrice")
    public AjaxResult modifyPrice(@RequestBody PsyConsultOrderVO req)
    {
        String msg = psyConsultOrderService.modifyPrice(req);
        return "ok".equals(msg) ? AjaxResult.success() : AjaxResult.error(msg);
    }

    /**
     * 订单转介
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:referral')")
    @Log(title = "咨询订单", businessType = BusinessType.REF)
    @PostMapping("/modifyRef")
    public AjaxResult modifyRef(@RequestBody PsyRefOrderReq req)
    {
        String msg = psyConsultOrderService.modifyRef(req);
        return "ok".equals(msg) ? AjaxResult.success() : AjaxResult.error(msg);
    }

    /**
     * 新增咨询订单
     */
    @PreAuthorize("@ss.hasPermi('psychology:order:add')")
    @Log(title = "咨询订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultOrderVO req)
    {
        req.setSource("5");
        // 已支付订单,取前端传递金额
        if (ConsultConstant.CONSULT_ORDER_STATUE_PENDING.equals(req.getStatus()) && req.getPay() == null) {
            return AjaxResult.error("支付金额不能为空");
        }
        return toAjax(psyConsultOrderService.add(req));
    }

    @PreAuthorize("@ss.hasPermi('psychology:order:remove')")
    @PostMapping(value = "/close/{id}")
    public AjaxResult close(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultOrderService.close(id));
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

    @PreAuthorize("@ss.hasPermi('psychology:order:edit')")
    @Log(title = "咨询订单", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/remark")
    public AjaxResult remark(@RequestBody PsyConsultOrderVO req)
    {
        return toAjax(psyConsultOrderService.remark(req));
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