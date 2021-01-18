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
import com.ruoyi.gbxxgl.domain.TsbzGbkykt;
import com.ruoyi.gbxxgl.service.ITsbzGbkyktService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 科研课题(干部管理)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbkykt")
public class TsbzGbkyktController extends BaseController {
    @Autowired
    private ITsbzGbkyktService tsbzGbkyktService;

    /**
     * 查询科研课题(干部管理)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbkykt tsbzGbkykt) {
        startPage();
        List<TsbzGbkykt> list = tsbzGbkyktService.selectTsbzGbkyktList(tsbzGbkykt);
        return getDataTable(list);
    }

    /**
     * 导出科研课题(干部管理)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:export')")
    @Log(title = "科研课题(干部管理)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbkykt tsbzGbkykt) {
        List<TsbzGbkykt> list = tsbzGbkyktService.selectTsbzGbkyktList(tsbzGbkykt);
        ExcelUtil<TsbzGbkykt> util = new ExcelUtil<TsbzGbkykt>(TsbzGbkykt.class);
        return util.exportExcel(list, "gbkykt");
    }

    /**
     * 获取科研课题(干部管理)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbkyktService.selectTsbzGbkyktById(id));
    }

    /**
     * 新增科研课题(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:add')")
    @Log(title = "科研课题(干部管理)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbkykt tsbzGbkykt) {
        tsbzGbkykt.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbkyktService.insertTsbzGbkykt(tsbzGbkykt));
    }

    /**
     * 修改科研课题(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:edit')")
    @Log(title = "科研课题(干部管理)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbkykt tsbzGbkykt) {
        return toAjax(tsbzGbkyktService.updateTsbzGbkykt(tsbzGbkykt));
    }

    /**
     * 删除科研课题(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbkykt:remove')")
    @Log(title = "科研课题(干部管理)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbkyktService.deleteTsbzGbkyktByIds(ids));
    }
}
