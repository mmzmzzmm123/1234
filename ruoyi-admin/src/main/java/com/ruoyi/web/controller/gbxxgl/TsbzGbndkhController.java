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
import com.ruoyi.gbxxgl.domain.TsbzGbndkh;
import com.ruoyi.gbxxgl.service.ITsbzGbndkhService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年度考核(干部管理-评价与考核)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbndkh")
public class TsbzGbndkhController extends BaseController {
    @Autowired
    private ITsbzGbndkhService tsbzGbndkhService;

    /**
     * 查询年度考核(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbndkh tsbzGbndkh) {
        startPage();
        List<TsbzGbndkh> list = tsbzGbndkhService.selectTsbzGbndkhList(tsbzGbndkh);
        return getDataTable(list);
    }

    /**
     * 导出年度考核(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:export')")
    @Log(title = "年度考核(干部管理-评价与考核)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbndkh tsbzGbndkh) {
        List<TsbzGbndkh> list = tsbzGbndkhService.selectTsbzGbndkhList(tsbzGbndkh);
        ExcelUtil<TsbzGbndkh> util = new ExcelUtil<TsbzGbndkh>(TsbzGbndkh.class);
        return util.exportExcel(list, "gbndkh");
    }

    /**
     * 获取年度考核(干部管理-评价与考核)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbndkhService.selectTsbzGbndkhById(id));
    }

    /**
     * 新增年度考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:add')")
    @Log(title = "年度考核(干部管理-评价与考核)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbndkh tsbzGbndkh) {
        tsbzGbndkh.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbndkhService.insertTsbzGbndkh(tsbzGbndkh));
    }

    /**
     * 修改年度考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:edit')")
    @Log(title = "年度考核(干部管理-评价与考核)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbndkh tsbzGbndkh) {
        return toAjax(tsbzGbndkhService.updateTsbzGbndkh(tsbzGbndkh));
    }

    /**
     * 删除年度考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbndkh:remove')")
    @Log(title = "年度考核(干部管理-评价与考核)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbndkhService.deleteTsbzGbndkhByIds(ids));
    }
}
