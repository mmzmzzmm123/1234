package com.ruoyi.app.controller.gauge;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.gauge.domain.PsyConsultingOrder;
import com.ruoyi.gauge.service.IPsyConsultingOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 咨询订单Controller
 * 
 * @author yangchuang
 * @date 2023-01-29
 */
@RestController
@RequestMapping("/app/consulting/order")
@Api(value = "PsyConsultingOrderController" ,tags = {"咨询订单"})
public class PsyConsultingOrderController extends BaseController
{
    @Autowired
    private IPsyConsultingOrderService psyConsultingOrderService;

    /**
     * 查询咨询订单列表
     */
    //@PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    @ApiOperation("查询咨询师管理列表")
    public TableDataInfo list(PsyConsultingOrder psyConsultingOrder)
    {
        startPage();
        List<PsyConsultingOrder> list = psyConsultingOrderService.selectPsyConsultingOrderList(psyConsultingOrder);
        return getDataTable(list);
    }


    /**
     * 获取咨询订单详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取咨询订单详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultingOrderService.selectPsyConsultingOrderById(id));
    }

    /**
     * 新增咨询订单
     */
    /*@PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "咨询订单", businessType = BusinessType.INSERT)*/
    @PostMapping("/add")
    @ApiOperation("新增咨询订单")
    public AjaxResult add(@RequestBody PsyConsultingOrder psyConsultingOrder)
    {
        return toAjax(psyConsultingOrderService.insertPsyConsultingOrder(psyConsultingOrder));
    }

    /**
     * 修改咨询订单
     */
    //@PreAuthorize("@ss.hasPermi('system:order:edit')")
    @PostMapping("/edit")
    @ApiOperation("修改咨询订单")
    public AjaxResult edit(@RequestBody PsyConsultingOrder psyConsultingOrder)
    {
        return toAjax(psyConsultingOrderService.updatePsyConsultingOrder(psyConsultingOrder));
    }

    /**
     * 删除咨询订单
     */
    /*@PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "咨询订单", businessType = BusinessType.DELETE)*/
	@PostMapping("/remove/{ids}")
    @ApiOperation("删除咨询订单")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyConsultingOrderService.deletePsyConsultingOrderByIds(ids));
    }
}
