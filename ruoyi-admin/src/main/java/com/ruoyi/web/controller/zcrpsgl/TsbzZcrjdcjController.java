package com.ruoyi.web.controller.zcrpsgl;

import java.util.Date;
import java.util.List;

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
import com.ruoyi.zcrpsgl.domain.TsbzZcrjdcj;
import com.ruoyi.zcrpsgl.service.ITsbzZcrjdcjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主持人基地Controller
 * 
 * @author tsbz
 * @date 2021-04-23
 */
@RestController
@RequestMapping("/zcrpsgl/zcrjdcj")
public class TsbzZcrjdcjController extends BaseController
{
    @Autowired
    private ITsbzZcrjdcjService tsbzZcrjdcjService;

    /**
     * 查询主持人基地列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:list')" + "||@ss.hasPermi('xypsgl:xybmsq:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzZcrjdcj tsbzZcrjdcj)
    {
        startPage();
        List<TsbzZcrjdcj> list = tsbzZcrjdcjService.selectTsbzZcrjdcjList(tsbzZcrjdcj);
        return getDataTable(list);
    }

    /**
     * 导出主持人基地列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:export')")
    @Log(title = "主持人基地", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzZcrjdcj tsbzZcrjdcj)
    {
        List<TsbzZcrjdcj> list = tsbzZcrjdcjService.selectTsbzZcrjdcjList(tsbzZcrjdcj);
        ExcelUtil<TsbzZcrjdcj> util = new ExcelUtil<TsbzZcrjdcj>(TsbzZcrjdcj.class);
        return util.exportExcel(list, "zcrjdcj");
    }

    /**
     * 获取主持人基地详细信息
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzZcrjdcjService.selectTsbzZcrjdcjById(id));
    }

    /**
     * 新增主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:add')")
    @Log(title = "主持人基地", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzZcrjdcj tsbzZcrjdcj)
    {
        tsbzZcrjdcj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzZcrjdcj.setCreateTime(new Date());
        return toAjax(tsbzZcrjdcjService.insertTsbzZcrjdcj(tsbzZcrjdcj));
    }

    /**
     * 修改主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:edit')")
    @Log(title = "主持人基地", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzZcrjdcj tsbzZcrjdcj)
    {
        return toAjax(tsbzZcrjdcjService.updateTsbzZcrjdcj(tsbzZcrjdcj));
    }

    /**
     * 删除主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:remove')")
    @Log(title = "主持人基地", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzZcrjdcjService.deleteTsbzZcrjdcjByIds(ids));
    }
}
