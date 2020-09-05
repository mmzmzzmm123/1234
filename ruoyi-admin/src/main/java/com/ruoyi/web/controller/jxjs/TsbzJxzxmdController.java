package com.ruoyi.web.controller.jxjs;

import java.util.List;

import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.jxjs.domain.TsbzJxzxmd;
import com.ruoyi.jxjs.service.ITsbzJxzxmdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习之星名单Controller
 *
 * @author ruoyi
 * @date 2020-08-23
 */
@RestController
@RequestMapping("/jxjs/jxzxmd")
public class TsbzJxzxmdController extends BaseController {
    @Autowired
    private ITsbzJxzxmdService tsbzJxzxmdService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询见习之星名单列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxmd tsbzJxzxmd) {
        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxzxmd.setJdxid(jdxId);
        }
        startPage();
        List<TsbzJxzxmd> list = tsbzJxzxmdService.selectTsbzJxzxmdList(tsbzJxzxmd);
        return getDataTable(list);
    }

    /**
     * 导出见习之星名单列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:export')")
    @Log(title = "见习之星名单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxmd tsbzJxzxmd) {
        List<TsbzJxzxmd> list = tsbzJxzxmdService.selectTsbzJxzxmdList(tsbzJxzxmd);
        ExcelUtil<TsbzJxzxmd> util = new ExcelUtil<TsbzJxzxmd>(TsbzJxzxmd.class);
        return util.exportExcel(list, "jxzxmd");
    }

    /**
     * 获取见习之星名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxmdService.selectTsbzJxzxmdById(id));
    }

    /**
     * 新增见习之星名单
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:add')")
    @Log(title = "见习之星名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxmd tsbzJxzxmd) {
        return toAjax(tsbzJxzxmdService.insertTsbzJxzxmd(tsbzJxzxmd));
    }

    /**
     * 修改见习之星名单
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:edit')")
    @Log(title = "见习之星名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxmd tsbzJxzxmd) {
        return toAjax(tsbzJxzxmdService.updateTsbzJxzxmd(tsbzJxzxmd));
    }

    /**
     * 删除见习之星名单
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxmd:remove')")
    @Log(title = "见习之星名单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxzxmdService.deleteTsbzJxzxmdByIds(ids));
    }
}
