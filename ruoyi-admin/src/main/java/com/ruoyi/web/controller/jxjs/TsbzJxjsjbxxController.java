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
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.service.ITsbzJxjsjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习教师基本信息Controller
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jxjsjbxx")
public class TsbzJxjsjbxxController extends BaseController {
    @Autowired
    private ITsbzJxjsjbxxService tsbzJxjsjbxxService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询见习教师基本信息列表，没有基地校的教师列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:list')")
    @GetMapping("/listnotjdx")
    public TableDataInfo listnotjdx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        startPage();
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxListnotjdx(tsbzJxjsjbxx);
        return getDataTable(list);
    }

    /**
     * 查询见习教师基本信息列表，没有参加基地初选的名单
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:list')")
    @GetMapping("/listnotjdcx")
    public TableDataInfo listnotjdcx(TsbzJxjsjbxx tsbzJxjsjbxx) {

        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxjsjbxx.setJdxid(jdxId);
        }

        //年份同时设置为见习教师录取年份
        tsbzJxjsjbxx.setLqnf(tsbzJxjsjbxx.getNf());

        startPage();
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxListnotjdcx(tsbzJxjsjbxx);
        return getDataTable(list);
    }

    /**
     * 查询见习教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxjsjbxx tsbzJxjsjbxx) {
        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxjsjbxx.setJdxid(jdxId);
        }

        startPage();
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
        return getDataTable(list);
    }

    /**
     * 导出见习教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:export')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxjsjbxx tsbzJxjsjbxx) {
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
        ExcelUtil<TsbzJxjsjbxx> util = new ExcelUtil<TsbzJxjsjbxx>(TsbzJxjsjbxx.class);
        return util.exportExcel(list, "jxjsjbxx");
    }

    /**
     * 获取见习教师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxjsjbxxService.selectTsbzJxjsjbxxById(id));
    }

    /**
     * 新增见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:add')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxjsjbxx tsbzJxjsjbxx) {
        return toAjax(tsbzJxjsjbxxService.insertTsbzJxjsjbxx(tsbzJxjsjbxx));
    }

    /**
     * 修改见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:edit')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxjsjbxx tsbzJxjsjbxx) {
        return toAjax(tsbzJxjsjbxxService.updateTsbzJxjsjbxx(tsbzJxjsjbxx));
    }

    /**
     * 修改见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:edit')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updatejsjdx/{ids}/{jdxid}")
    public AjaxResult updateJsJdx(@PathVariable Long[] ids, @PathVariable String jdxid) {
        int iCount = 0;
        System.out.println("jsid=" + ids[0]);
        System.out.println("jdxid=" + jdxid);
        if (ids == null || ids.length == 0) {
            return AjaxResult.error("请选择要分配的见习教师");
        }
        TsbzJxjsjbxx tsbzJxjsjbxx = null;
        for (int i = 0; i < ids.length; i++) {

            tsbzJxjsjbxx = new TsbzJxjsjbxx();
            tsbzJxjsjbxx.setId(ids[i]);
            tsbzJxjsjbxx.setJdxid(jdxid);
            iCount = iCount + (tsbzJxjsjbxxService.updateTsbzJxjsjbxx(tsbzJxjsjbxx));
        }
        return toAjax(iCount);
    }

    /**
     * 删除见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:remove')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxjsjbxxService.deleteTsbzJxjsjbxxByIds(ids));
    }

    /**
     * 删除见习教师基地校id
     */
    @Log(title = "见习教师基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/clearjdx/{id}")
    public AjaxResult clearjdx(@PathVariable Long id) {
        return toAjax(tsbzJxjsjbxxService.clearTsbzJxjsjdx(id));
    }
}
