package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.QzHappy;
import com.ruoyi.system.service.IQzHappyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 快乐+1Controller
 * 
 * @author zlx
 * @date 2021-08-10
 */
@RestController
@RequestMapping("/system/happy")
public class QzHappyController extends BaseController
{
    @Autowired
    private IQzHappyService qzHappyService;

    /**
     * 查询快乐+1列表
     */
    @PreAuthorize("@ss.hasPermi('system:happy:list')")
    @GetMapping("/list")
    public TableDataInfo list(QzHappy qzHappy)
    {
        startPage();
        List<QzHappy> list = qzHappyService.selectQzHappyList(qzHappy);
        return getDataTable(list);
    }

    /**
     * 导出快乐+1列表
     */
    @PreAuthorize("@ss.hasPermi('system:happy:export')")
    @Log(title = "快乐+1", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QzHappy qzHappy)
    {
        List<QzHappy> list = qzHappyService.selectQzHappyList(qzHappy);
        ExcelUtil<QzHappy> util = new ExcelUtil<QzHappy>(QzHappy.class);
        return util.exportExcel(list, "快乐+1数据");
    }

    /**
     * 获取快乐+1详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:happy:query')")
    @GetMapping(value = "/{happyId}")
    public AjaxResult getInfo(@PathVariable("happyId") Long happyId)
    {
        return AjaxResult.success(qzHappyService.selectQzHappyByHappyId(happyId));
    }

    /**
     * 新增快乐+1
     */
    @PreAuthorize("@ss.hasPermi('system:happy:add')")
    @Log(title = "快乐+1", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QzHappy qzHappy)
    {
        return toAjax(qzHappyService.insertQzHappy(qzHappy));
    }

    /**
     * 修改快乐+1
     */
    @PreAuthorize("@ss.hasPermi('system:happy:edit')")
    @Log(title = "快乐+1", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QzHappy qzHappy)
    {
        return toAjax(qzHappyService.updateQzHappy(qzHappy));
    }

    /**
     * 删除快乐+1
     */
    @PreAuthorize("@ss.hasPermi('system:happy:remove')")
    @Log(title = "快乐+1", businessType = BusinessType.DELETE)
	@DeleteMapping("/{happyIds}")
    public AjaxResult remove(@PathVariable Long[] happyIds)
    {
        return toAjax(qzHappyService.deleteQzHappyByHappyIds(happyIds));
    }
}
