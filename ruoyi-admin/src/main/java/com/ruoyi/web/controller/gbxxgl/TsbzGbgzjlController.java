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
import com.ruoyi.gbxxgl.domain.TsbzGbgzjl;
import com.ruoyi.gbxxgl.service.ITsbzGbgzjlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部工作经历Controller
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@RestController
@RequestMapping("/gbxxgl/gbgzjl")
public class TsbzGbgzjlController extends BaseController {
    @Autowired
    private ITsbzGbgzjlService tsbzGbgzjlService;

    /**
     * 查询干部工作经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbgzjl tsbzGbgzjl) {
        startPage();
        List<TsbzGbgzjl> list = tsbzGbgzjlService.selectTsbzGbgzjlList(tsbzGbgzjl);
        return getDataTable(list);
    }

    /**
     * 导出干部工作经历列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:export')")
    @Log(title = "干部工作经历", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbgzjl tsbzGbgzjl) {
        List<TsbzGbgzjl> list = tsbzGbgzjlService.selectTsbzGbgzjlList(tsbzGbgzjl);
        ExcelUtil<TsbzGbgzjl> util = new ExcelUtil<TsbzGbgzjl>(TsbzGbgzjl.class);
        return util.exportExcel(list, "gbgzjl");
    }

    /**
     * 获取干部工作经历详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbgzjlService.selectTsbzGbgzjlById(id));
    }

    /**
     * 新增干部工作经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:add')")
    @Log(title = "干部工作经历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbgzjl tsbzGbgzjl) {
        //先判断是否已经创建当前任职年月的干部信息
        TsbzGbgzjl tsbzGbgzjlNew = new TsbzGbgzjl();
        tsbzGbgzjlNew.setGbid(tsbzGbgzjl.getGbid());
        tsbzGbgzjlNew.setQsny(tsbzGbgzjl.getQsny());
        List<TsbzGbgzjl> selectList = tsbzGbgzjlService.selectTsbzGbgzjlList(tsbzGbgzjlNew);
        if (selectList != null && selectList.size() > 0) {
            return AjaxResult.error("当前干部任职年月工作经历已创建,无法重复创建");
        }
        return toAjax(tsbzGbgzjlService.insertTsbzGbgzjl(tsbzGbgzjl));
    }

    /**
     * 修改干部工作经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:edit')")
    @Log(title = "干部工作经历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbgzjl tsbzGbgzjl) {
        return toAjax(tsbzGbgzjlService.updateTsbzGbgzjl(tsbzGbgzjl));
    }

    /**
     * 删除干部工作经历
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgzjl:remove')")
    @Log(title = "干部工作经历", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbgzjlService.deleteTsbzGbgzjlByIds(ids));
    }
}
