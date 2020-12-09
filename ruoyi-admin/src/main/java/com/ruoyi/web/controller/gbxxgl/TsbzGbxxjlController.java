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
import com.ruoyi.gbxxgl.domain.TsbzGbxxjl;
import com.ruoyi.gbxxgl.service.ITsbzGbxxjlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部学习经历Controller
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/gbxxjl")
public class TsbzGbxxjlController extends BaseController
{
    @Autowired
    private ITsbzGbxxjlService tsbzGbxxjlService;

    /**
     * 查询干部学习经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbxxjl tsbzGbxxjl)
    {
        startPage();
        List<TsbzGbxxjl> list = tsbzGbxxjlService.selectTsbzGbxxjlList(tsbzGbxxjl);
        return getDataTable(list);
    }

    /**
     * 导出干部学习经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:export')")
    @Log(title = "干部学习经历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbxxjl tsbzGbxxjl)
    {
        List<TsbzGbxxjl> list = tsbzGbxxjlService.selectTsbzGbxxjlList(tsbzGbxxjl);
        ExcelUtil<TsbzGbxxjl> util = new ExcelUtil<TsbzGbxxjl>(TsbzGbxxjl.class);
        return util.exportExcel(list, "gbxxjl");
    }

    /**
     * 获取干部学习经历详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbxxjlService.selectTsbzGbxxjlById(id));
    }

    /**
     * 新增干部学习经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:add')")
    @Log(title = "干部学习经历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbxxjl tsbzGbxxjl)
    {
        //先判断是否已经创建当前任职年月的干部信息
        TsbzGbxxjl tsbzGbxxjlNew = new TsbzGbxxjl();
        tsbzGbxxjlNew.setGbid(tsbzGbxxjl.getGbid());
        tsbzGbxxjlNew.setQsny(tsbzGbxxjl.getQsny());
        tsbzGbxxjlNew.setZzny(tsbzGbxxjl.getZzny());
        List<TsbzGbxxjl> selectList = tsbzGbxxjlService.selectTsbzGbxxjlList(tsbzGbxxjlNew);
        if (selectList != null && selectList.size() > 0) {
            return AjaxResult.error("当前干部学习年月信息已创建,无法重复创建");
        }


        tsbzGbxxjl.setCreateTime(new Date());
        tsbzGbxxjl.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbxxjlService.insertTsbzGbxxjl(tsbzGbxxjl));
    }

    /**
     * 修改干部学习经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:edit')")
    @Log(title = "干部学习经历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbxxjl tsbzGbxxjl)
    {
        return toAjax(tsbzGbxxjlService.updateTsbzGbxxjl(tsbzGbxxjl));
    }

    /**
     * 删除干部学习经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbxxjl:remove')")
    @Log(title = "干部学习经历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbxxjlService.deleteTsbzGbxxjlByIds(ids));
    }
}
