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
import com.ruoyi.jxjs.domain.TsbzJxzxjdpxxxfb;
import com.ruoyi.jxjs.service.ITsbzJxzxjdpxxxfbService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地培训信息发布Controller
 * 
 * @author ruoyi
 * @date 2020-11-09
 */
@RestController
@RequestMapping("/jxjs/jxzxjdpxxxfb")
public class TsbzJxzxjdpxxxfbController extends BaseController
{
    @Autowired
    private ITsbzJxzxjdpxxxfbService tsbzJxzxjdpxxxfbService;

    /**
     * 查询基地培训信息发布列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        startPage();
        List<TsbzJxzxjdpxxxfb> list = tsbzJxzxjdpxxxfbService.selectTsbzJxzxjdpxxxfbList(tsbzJxzxjdpxxxfb);
        return getDataTable(list);
    }

    /**
     * 导出基地培训信息发布列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:export')")
    @Log(title = "基地培训信息发布", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        List<TsbzJxzxjdpxxxfb> list = tsbzJxzxjdpxxxfbService.selectTsbzJxzxjdpxxxfbList(tsbzJxzxjdpxxxfb);
        ExcelUtil<TsbzJxzxjdpxxxfb> util = new ExcelUtil<TsbzJxzxjdpxxxfb>(TsbzJxzxjdpxxxfb.class);
        return util.exportExcel(list, "jxzxjdpxxxfb");
    }

    /**
     * 获取基地培训信息发布详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJxzxjdpxxxfbService.selectTsbzJxzxjdpxxxfbById(id));
    }

    /**
     * 新增基地培训信息发布
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:add')")
    @Log(title = "基地培训信息发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        return toAjax(tsbzJxzxjdpxxxfbService.insertTsbzJxzxjdpxxxfb(tsbzJxzxjdpxxxfb));
    }

    /**
     * 修改基地培训信息发布
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:edit')")
    @Log(title = "基地培训信息发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        return toAjax(tsbzJxzxjdpxxxfbService.updateTsbzJxzxjdpxxxfb(tsbzJxzxjdpxxxfb));
    }

    /**
     * 删除基地培训信息发布
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxjdpxxxfb:remove')")
    @Log(title = "基地培训信息发布", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJxzxjdpxxxfbService.deleteTsbzJxzxjdpxxxfbByIds(ids));
    }
}
