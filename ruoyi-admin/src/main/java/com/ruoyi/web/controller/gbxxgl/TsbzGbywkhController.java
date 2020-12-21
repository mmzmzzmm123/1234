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
import com.ruoyi.gbxxgl.domain.TsbzGbywkh;
import com.ruoyi.gbxxgl.service.ITsbzGbywkhService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务考核(干部管理-评价与考核)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbywkh")
public class TsbzGbywkhController extends BaseController {
    @Autowired
    private ITsbzGbywkhService tsbzGbywkhService;

    /**
     * 查询业务考核(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbywkh tsbzGbywkh) {
        startPage();
        List<TsbzGbywkh> list = tsbzGbywkhService.selectTsbzGbywkhList(tsbzGbywkh);
        return getDataTable(list);
    }

    /**
     * 导出业务考核(干部管理-评价与考核)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:export')")
    @Log(title = "业务考核(干部管理-评价与考核)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbywkh tsbzGbywkh) {
        List<TsbzGbywkh> list = tsbzGbywkhService.selectTsbzGbywkhList(tsbzGbywkh);
        ExcelUtil<TsbzGbywkh> util = new ExcelUtil<TsbzGbywkh>(TsbzGbywkh.class);
        return util.exportExcel(list, "gbywkh");
    }

    /**
     * 获取业务考核(干部管理-评价与考核)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbywkhService.selectTsbzGbywkhById(id));
    }

    /**
     * 新增业务考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:add')")
    @Log(title = "业务考核(干部管理-评价与考核)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbywkh tsbzGbywkh) {
        tsbzGbywkh.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbywkhService.insertTsbzGbywkh(tsbzGbywkh));
    }

    /**
     * 修改业务考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:edit')")
    @Log(title = "业务考核(干部管理-评价与考核)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbywkh tsbzGbywkh) {
        return toAjax(tsbzGbywkhService.updateTsbzGbywkh(tsbzGbywkh));
    }

    /**
     * 删除业务考核(干部管理-评价与考核)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbywkh:remove')")
    @Log(title = "业务考核(干部管理-评价与考核)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbywkhService.deleteTsbzGbywkhByIds(ids));
    }
}
