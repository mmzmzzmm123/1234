package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.office.domain.TRoomPrice;
import com.ruoyi.office.service.ITRoomPriceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间价格Controller
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/office/roomprice")
public class TRoomPriceController extends BaseController {
    @Autowired
    private ITRoomPriceService tRoomPriceService;

    /**
     * 查询房间价格列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomPrice tRoomPrice) {
        startPage();
        List<TRoomPrice> list = tRoomPriceService.selectTRoomPriceList(tRoomPrice);
        return getDataTable(list);
    }

    /**
     * 导出房间价格列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:export')")
    @Log(title = "房间价格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomPrice tRoomPrice) {
        List<TRoomPrice> list = tRoomPriceService.selectTRoomPriceList(tRoomPrice);
        ExcelUtil<TRoomPrice> util = new ExcelUtil<TRoomPrice>(TRoomPrice.class);
        util.exportExcel(response, list, "房间价格数据");
    }

    /**
     * 获取房间价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tRoomPriceService.selectTRoomPriceById(id));
    }

    /**
     * 新增房间价格
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:add')")
    @Log(title = "房间价格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomPrice tRoomPrice) {
        tRoomPrice.setCreateBy(SecurityUtils.getUserId() + "");
        return toAjax(tRoomPriceService.insertTRoomPrice(tRoomPrice));
    }

    /**
     * 修改房间价格
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:edit')")
    @Log(title = "房间价格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomPrice tRoomPrice) {
        tRoomPrice.setUpdateBy(SecurityUtils.getUserId() + "");
        return toAjax(tRoomPriceService.updateTRoomPrice(tRoomPrice));
    }

    /**
     * 删除房间价格
     */
    @PreAuthorize("@ss.hasPermi('office:roomprice:remove')")
    @Log(title = "房间价格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRoomPriceService.deleteTRoomPriceByIds(ids));
    }
}
