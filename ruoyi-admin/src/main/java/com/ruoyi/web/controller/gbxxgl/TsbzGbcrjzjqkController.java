package com.ruoyi.web.controller.gbxxgl;

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
import com.ruoyi.gbxxgl.domain.TsbzGbcrjzjqk;
import com.ruoyi.gbxxgl.service.ITsbzGbcrjzjqkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部出入境证件情况Controller
 * 
 * @author ruoyi
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbcrjzjqk")
public class TsbzGbcrjzjqkController extends BaseController
{
    @Autowired
    private ITsbzGbcrjzjqkService tsbzGbcrjzjqkService;

    /**
     * 查询干部出入境证件情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        startPage();
        List<TsbzGbcrjzjqk> list = tsbzGbcrjzjqkService.selectTsbzGbcrjzjqkList(tsbzGbcrjzjqk);
        return getDataTable(list);
    }

    /**
     * 导出干部出入境证件情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:export')")
    @Log(title = "干部出入境证件情况", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        List<TsbzGbcrjzjqk> list = tsbzGbcrjzjqkService.selectTsbzGbcrjzjqkList(tsbzGbcrjzjqk);
        ExcelUtil<TsbzGbcrjzjqk> util = new ExcelUtil<TsbzGbcrjzjqk>(TsbzGbcrjzjqk.class);
        return util.exportExcel(list, "gbcrjzjqk");
    }

    /**
     * 获取干部出入境证件情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbcrjzjqkService.selectTsbzGbcrjzjqkById(id));
    }

    /**
     * 新增干部出入境证件情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:add')")
    @Log(title = "干部出入境证件情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        tsbzGbcrjzjqk.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzGbcrjzjqk.setIsdel("0");
        return toAjax(tsbzGbcrjzjqkService.insertTsbzGbcrjzjqk(tsbzGbcrjzjqk));
    }

    /**
     * 修改干部出入境证件情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:edit')")
    @Log(title = "干部出入境证件情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        return toAjax(tsbzGbcrjzjqkService.updateTsbzGbcrjzjqk(tsbzGbcrjzjqk));
    }

    /**
     * 删除干部出入境证件情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjzjqk:remove')")
    @Log(title = "干部出入境证件情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbcrjzjqkService.deleteTsbzGbcrjzjqkByIds(ids));
    }
}
