package com.onethinker.web.controller.bx;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.onethinker.common.annotation.Log;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.enums.BusinessType;
import com.onethinker.common.constant.ServicePathConstant;

import com.onethinker.bx.domain.BxCustomerInfo;
import com.onethinker.bx.service.IBxCustomerInfoService;
import com.onethinker.common.utils.poi.ExcelUtil;
import com.onethinker.common.core.page.TableDataInfo;

/**
 * 佰兴-客户信息表
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
@RestController
@RequestMapping("/bx/customer")
public class BxCustomerInfoController extends BaseController {
    @Autowired
    private IBxCustomerInfoService bxCustomerInfoService;

    /**
     * 查询佰兴-客户信息表列表
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:list')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/list")
    public TableDataInfo list(BxCustomerInfo bxCustomerInfo) {
        startPage();
        List<BxCustomerInfo> list = bxCustomerInfoService.selectBxCustomerInfoList(bxCustomerInfo);
        return getDataTable(list);
    }

    /**
     * 导出佰兴-客户信息表列表
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:export')")
    @Log(title = "佰兴-客户信息表", businessType = BusinessType.EXPORT)
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, BxCustomerInfo bxCustomerInfo) {
        List<BxCustomerInfo> list = bxCustomerInfoService.selectBxCustomerInfoList(bxCustomerInfo);
        ExcelUtil<BxCustomerInfo> util = new ExcelUtil<BxCustomerInfo>(BxCustomerInfo.class);
        util.exportExcel(response, list, "佰兴-客户信息表数据");
    }

    /**
     * 获取佰兴-客户信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:query')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bxCustomerInfoService.selectBxCustomerInfoById(id));
    }

    /**
     * 新增佰兴-客户信息表
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:add')")
    @Log(title = "佰兴-客户信息表", businessType = BusinessType.INSERT)
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH)
    public AjaxResult add(@RequestBody BxCustomerInfo bxCustomerInfo) {
        return toAjax(bxCustomerInfoService.insertBxCustomerInfo(bxCustomerInfo));
    }

    /**
     * 修改佰兴-客户信息表
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:edit')")
    @Log(title = "佰兴-客户信息表", businessType = BusinessType.UPDATE)
    @PutMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH)
    public AjaxResult edit(@RequestBody BxCustomerInfo bxCustomerInfo) {
        return toAjax(bxCustomerInfoService.updateBxCustomerInfo(bxCustomerInfo));
    }

    /**
     * 删除佰兴-客户信息表
     */
    @PreAuthorize("@ss.hasPermi('bx:customer:remove')")
    @Log(title = "佰兴-客户信息表", businessType = BusinessType.DELETE)
    @DeleteMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bxCustomerInfoService.deleteBxCustomerInfoByIds(ids));
    }
}
