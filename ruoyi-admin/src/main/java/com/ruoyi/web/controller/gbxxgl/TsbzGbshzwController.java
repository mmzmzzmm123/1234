package com.ruoyi.web.controller.gbxxgl;

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
import com.ruoyi.gbxxgl.domain.TsbzGbshzw;
import com.ruoyi.gbxxgl.service.ITsbzGbshzwService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部任职情况-社会职务Controller
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/gbshzw")
public class TsbzGbshzwController extends BaseController
{
    @Autowired
    private ITsbzGbshzwService tsbzGbshzwService;

    /**
     * 查询干部任职情况-社会职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbshzw tsbzGbshzw)
    {
        startPage();
        List<TsbzGbshzw> list = tsbzGbshzwService.selectTsbzGbshzwList(tsbzGbshzw);
        return getDataTable(list);
    }

    /**
     * 导出干部任职情况-社会职务列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:export')")
    @Log(title = "干部任职情况-社会职务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbshzw tsbzGbshzw)
    {
        List<TsbzGbshzw> list = tsbzGbshzwService.selectTsbzGbshzwList(tsbzGbshzw);
        ExcelUtil<TsbzGbshzw> util = new ExcelUtil<TsbzGbshzw>(TsbzGbshzw.class);
        return util.exportExcel(list, "gbshzw");
    }

    /**
     * 获取干部任职情况-社会职务详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbshzwService.selectTsbzGbshzwById(id));
    }

    /**
     * 新增干部任职情况-社会职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:add')")
    @Log(title = "干部任职情况-社会职务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbshzw tsbzGbshzw)
    {
        //先判断是否已经创建当前任职年月的干部信息
        TsbzGbshzw tsbzGbshzwNew = new TsbzGbshzw();
        tsbzGbshzwNew.setGbid(tsbzGbshzw.getGbid());
        tsbzGbshzwNew.setRzqsny(tsbzGbshzw.getRzqsny());
        tsbzGbshzwNew.setRzzzny(tsbzGbshzw.getRzzzny());
        List<TsbzGbshzw> selectList = tsbzGbshzwService.selectTsbzGbshzwList(tsbzGbshzwNew);
        if (selectList != null && selectList.size() > 0) {
            return AjaxResult.error("当前干部任职年月信息已创建,无法重复创建");
        }

        tsbzGbshzw.setCreatetime(new Date());
        tsbzGbshzw.setCreateuser(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbshzwService.insertTsbzGbshzw(tsbzGbshzw));
    }

    /**
     * 修改干部任职情况-社会职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:edit')")
    @Log(title = "干部任职情况-社会职务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbshzw tsbzGbshzw)
    {
        return toAjax(tsbzGbshzwService.updateTsbzGbshzw(tsbzGbshzw));
    }

    /**
     * 删除干部任职情况-社会职务
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbshzw:remove')")
    @Log(title = "干部任职情况-社会职务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbshzwService.deleteTsbzGbshzwByIds(ids));
    }
}
