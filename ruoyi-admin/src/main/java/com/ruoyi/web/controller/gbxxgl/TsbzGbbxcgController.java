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
import com.ruoyi.gbxxgl.domain.TsbzGbbxcg;
import com.ruoyi.gbxxgl.service.ITsbzGbbxcgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 办学成果(干部管理-评价与考核)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbbxcg")
public class TsbzGbbxcgController extends BaseController {
    @Autowired
    private ITsbzGbbxcgService tsbzGbbxcgService;

    /**
     * 查询办学成果(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbbxcg tsbzGbbxcg) {
        startPage();
        List<TsbzGbbxcg> list = tsbzGbbxcgService.selectTsbzGbbxcgList(tsbzGbbxcg);
        return getDataTable(list);
    }

    /**
     * 导出办学成果(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:export')")
    @Log(title = "办学成果(干部管理-评价与考核)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbbxcg tsbzGbbxcg) {
        List<TsbzGbbxcg> list = tsbzGbbxcgService.selectTsbzGbbxcgList(tsbzGbbxcg);
        ExcelUtil<TsbzGbbxcg> util = new ExcelUtil<TsbzGbbxcg>(TsbzGbbxcg.class);
        return util.exportExcel(list, "gbbxcg");
    }

    /**
     * 获取办学成果(干部管理-评价与考核)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbbxcgService.selectTsbzGbbxcgById(id));
    }

    /**
     * 新增办学成果(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:add')")
    @Log(title = "办学成果(干部管理-评价与考核)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbbxcg tsbzGbbxcg) {
        tsbzGbbxcg.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbbxcgService.insertTsbzGbbxcg(tsbzGbbxcg));
    }

    /**
     * 修改办学成果(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:edit')")
    @Log(title = "办学成果(干部管理-评价与考核)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbbxcg tsbzGbbxcg) {
        return toAjax(tsbzGbbxcgService.updateTsbzGbbxcg(tsbzGbbxcg));
    }

    /**
     * 删除办学成果(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbbxcg:remove')")
    @Log(title = "办学成果(干部管理-评价与考核)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbbxcgService.deleteTsbzGbbxcgByIds(ids));
    }
}
