package com.ruoyi.web.controller.jxjs;

import java.util.List;

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
import com.ruoyi.jxjs.domain.TsbzYxjxdstjb;
import com.ruoyi.jxjs.service.ITsbzYxjxdstjbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优秀见习导师推荐Controller
 *
 * @author tsbz
 * @date 2021-07-03
 */
@RestController
@RequestMapping("/jxjs/yxjxdstjb")
public class TsbzYxjxdstjbController extends BaseController {
    @Autowired
    private ITsbzYxjxdstjbService tsbzYxjxdstjbService;

    /**
     * 查询优秀见习导师推荐列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzYxjxdstjb tsbzYxjxdstjb) {
        startPage();
        List<TsbzYxjxdstjb> list = tsbzYxjxdstjbService.selectTsbzYxjxdstjbList(tsbzYxjxdstjb);
        return getDataTable(list);
    }

    /**
     * 导出优秀见习导师推荐列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:export')")
    @Log(title = "优秀见习导师推荐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzYxjxdstjb tsbzYxjxdstjb) {
        List<TsbzYxjxdstjb> list = tsbzYxjxdstjbService.selectTsbzYxjxdstjbList(tsbzYxjxdstjb);
        ExcelUtil<TsbzYxjxdstjb> util = new ExcelUtil<TsbzYxjxdstjb>(TsbzYxjxdstjb.class);
        return util.exportExcel(list, "yxjxdstjb");
    }

    /**
     * 获取优秀见习导师推荐详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzYxjxdstjbService.selectTsbzYxjxdstjbById(id));
    }

    /**
     * 新增优秀见习导师推荐
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:add')")
    @Log(title = "优秀见习导师推荐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzYxjxdstjb tsbzYxjxdstjb) {
        return toAjax(tsbzYxjxdstjbService.insertTsbzYxjxdstjb(tsbzYxjxdstjb));
    }

    /**
     * 修改优秀见习导师推荐
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:edit')")
    @Log(title = "优秀见习导师推荐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzYxjxdstjb tsbzYxjxdstjb) {
        return toAjax(tsbzYxjxdstjbService.updateTsbzYxjxdstjb(tsbzYxjxdstjb));
    }

    /**
     * 删除优秀见习导师推荐
     */
    @PreAuthorize("@ss.hasPermi('jxjs:yxjxdstjb:remove')")
    @Log(title = "优秀见习导师推荐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzYxjxdstjbService.deleteTsbzYxjxdstjbByIds(ids));
    }
}
