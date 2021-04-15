package com.ruoyi.web.controller.zcrpsgl;

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
import com.ruoyi.zcrpsgl.domain.TsbzZcrbmsq;
import com.ruoyi.zcrpsgl.service.ITsbzZcrbmsqService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主持人报名申请Controller
 * 
 * @author tsbz
 * @date 2021-04-15
 */
@RestController
@RequestMapping("/zcrpsgl/zcrbmsq")
public class TsbzZcrbmsqController extends BaseController
{
    @Autowired
    private ITsbzZcrbmsqService tsbzZcrbmsqService;

    /**
     * 查询主持人报名申请列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzZcrbmsq tsbzZcrbmsq)
    {
        startPage();
        List<TsbzZcrbmsq> list = tsbzZcrbmsqService.selectTsbzZcrbmsqList(tsbzZcrbmsq);
        return getDataTable(list);
    }

    /**
     * 导出主持人报名申请列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:export')")
    @Log(title = "主持人报名申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzZcrbmsq tsbzZcrbmsq)
    {
        List<TsbzZcrbmsq> list = tsbzZcrbmsqService.selectTsbzZcrbmsqList(tsbzZcrbmsq);
        ExcelUtil<TsbzZcrbmsq> util = new ExcelUtil<TsbzZcrbmsq>(TsbzZcrbmsq.class);
        return util.exportExcel(list, "zcrbmsq");
    }

    /**
     * 获取主持人报名申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzZcrbmsqService.selectTsbzZcrbmsqById(id));
    }

    /**
     * 新增主持人报名申请
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:add')")
    @Log(title = "主持人报名申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzZcrbmsq tsbzZcrbmsq)
    {
        return toAjax(tsbzZcrbmsqService.insertTsbzZcrbmsq(tsbzZcrbmsq));
    }

    /**
     * 修改主持人报名申请
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:edit')")
    @Log(title = "主持人报名申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzZcrbmsq tsbzZcrbmsq)
    {
        return toAjax(tsbzZcrbmsqService.updateTsbzZcrbmsq(tsbzZcrbmsq));
    }

    /**
     * 删除主持人报名申请
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrbmsq:remove')")
    @Log(title = "主持人报名申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzZcrbmsqService.deleteTsbzZcrbmsqByIds(ids));
    }
}
