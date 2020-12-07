package com.ruoyi.web.controller.gbxxgl;

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
import com.ruoyi.gbxxgl.domain.TsbzGbxrzw;
import com.ruoyi.gbxxgl.service.ITsbzGbxrzwService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部任职情况-现任职务Controller
 * 
 * @author ruoyi
 * @date 2020-12-07
 */
@RestController
@RequestMapping("/gbxxgl/gbxrzw")
public class TsbzGbxrzwController extends BaseController
{
    @Autowired
    private ITsbzGbxrzwService tsbzGbxrzwService;

    /**
     * 查询干部任职情况-现任职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbxrzw tsbzGbxrzw)
    {
        startPage();
        List<TsbzGbxrzw> list = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzw);
        return getDataTable(list);
    }

    /**
     * 导出干部任职情况-现任职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:export')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbxrzw tsbzGbxrzw)
    {
        List<TsbzGbxrzw> list = tsbzGbxrzwService.selectTsbzGbxrzwList(tsbzGbxrzw);
        ExcelUtil<TsbzGbxrzw> util = new ExcelUtil<TsbzGbxrzw>(TsbzGbxrzw.class);
        return util.exportExcel(list, "gbxrzw");
    }

    /**
     * 获取干部任职情况-现任职务详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbxrzwService.selectTsbzGbxrzwById(id));
    }

    /**
     * 新增干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:add')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbxrzw tsbzGbxrzw)
    {
        return toAjax(tsbzGbxrzwService.insertTsbzGbxrzw(tsbzGbxrzw));
    }

    /**
     * 修改干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:edit')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbxrzw tsbzGbxrzw)
    {
        return toAjax(tsbzGbxrzwService.updateTsbzGbxrzw(tsbzGbxrzw));
    }

    /**
     * 删除干部任职情况-现任职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxrzw:remove')")
    @Log(title = "干部任职情况-现任职务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbxrzwService.deleteTsbzGbxrzwByIds(ids));
    }
}
