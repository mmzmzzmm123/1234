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
import com.ruoyi.gbxxgl.domain.TsbzGbcrjqk;
import com.ruoyi.gbxxgl.service.ITsbzGbcrjqkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部出入境情况Controller
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/gbcrjqk")
public class TsbzGbcrjqkController extends BaseController
{
    @Autowired
    private ITsbzGbcrjqkService tsbzGbcrjqkService;

    /**
     * 查询干部出入境情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbcrjqk tsbzGbcrjqk)
    {
        startPage();
        List<TsbzGbcrjqk> list = tsbzGbcrjqkService.selectTsbzGbcrjqkList(tsbzGbcrjqk);
        return getDataTable(list);
    }

    /**
     * 导出干部出入境情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:export')")
    @Log(title = "干部出入境情况", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbcrjqk tsbzGbcrjqk)
    {
        List<TsbzGbcrjqk> list = tsbzGbcrjqkService.selectTsbzGbcrjqkList(tsbzGbcrjqk);
        ExcelUtil<TsbzGbcrjqk> util = new ExcelUtil<TsbzGbcrjqk>(TsbzGbcrjqk.class);
        return util.exportExcel(list, "gbcrjqk");
    }

    /**
     * 获取干部出入境情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbcrjqkService.selectTsbzGbcrjqkById(id));
    }

    /**
     * 新增干部出入境情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:add')")
    @Log(title = "干部出入境情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbcrjqk tsbzGbcrjqk)
    {
        //先判断是否已经创建当前任职年月的干部信息
        TsbzGbcrjqk tsbzGbcrjqkNew = new TsbzGbcrjqk();
        tsbzGbcrjqkNew.setGbid(tsbzGbcrjqk.getGbid());
        tsbzGbcrjqkNew.setQsrq(tsbzGbcrjqk.getQsrq());
        List<TsbzGbcrjqk> selectList = tsbzGbcrjqkService.selectTsbzGbcrjqkList(tsbzGbcrjqkNew);
        if (selectList != null && selectList.size() > 0) {
            return AjaxResult.error("当前干部出入境信息已创建,无法重复创建");
        }
        return toAjax(tsbzGbcrjqkService.insertTsbzGbcrjqk(tsbzGbcrjqk));
    }

    /**
     * 修改干部出入境情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:edit')")
    @Log(title = "干部出入境情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbcrjqk tsbzGbcrjqk)
    {
        return toAjax(tsbzGbcrjqkService.updateTsbzGbcrjqk(tsbzGbcrjqk));
    }

    /**
     * 删除干部出入境情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbcrjqk:remove')")
    @Log(title = "干部出入境情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbcrjqkService.deleteTsbzGbcrjqkByIds(ids));
    }
}
