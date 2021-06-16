package com.ruoyi.web.controller.qtjskhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcsjService;
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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhsh;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhshService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核审核过程Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhsh")
public class TsbzQtjskhshController extends BaseController {
    @Autowired
    private ITsbzQtjskhshService tsbzQtjskhshService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ITsbzQtjskhgcsjService tsbzQtjskhgcsjService;

    /**
     * 查询群体教师考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:list')"+ "||@ss.hasPermi('qtjskhgl:qtjskhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhsh tsbzQtjskhsh) {
        startPage();
        List<TsbzQtjskhsh> list = tsbzQtjskhshService.selectTsbzQtjskhshList(tsbzQtjskhsh);
        return getDataTable(list);
    }

    @GetMapping("/listbyuser")
    public TableDataInfo listbyuser(TsbzQtjskhsh tsbzQtjskhsh) {
        tsbzQtjskhsh.setCreateuseird(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        startPage();
        List<TsbzQtjskhsh> list = tsbzQtjskhshService.selectTsbzQtjskhshList(tsbzQtjskhsh);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:export')")
    @Log(title = "群体教师考核审核过程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhsh tsbzQtjskhsh) {
        List<TsbzQtjskhsh> list = tsbzQtjskhshService.selectTsbzQtjskhshList(tsbzQtjskhsh);
        ExcelUtil<TsbzQtjskhsh> util = new ExcelUtil<TsbzQtjskhsh>(TsbzQtjskhsh.class);
        return util.exportExcel(list, "qtjskhsh");
    }

    /**
     * 获取群体教师考核审核过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjskhshService.selectTsbzQtjskhshById(id));
    }

    /**
     * 新增群体教师考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:add')" + "||@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核审核过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhsh tsbzQtjskhsh) {

        tsbzQtjskhsh.setJsid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        tsbzQtjskhsh.setStatus("1");
        tsbzQtjskhsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());

        return toAjax(tsbzQtjskhshService.insertTsbzQtjskhsh(tsbzQtjskhsh));
    }

    /**
     * 修改群体教师考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:edit')")
    @Log(title = "群体教师考核审核过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhsh tsbzQtjskhsh) {
        //校级
        if (!schoolCommonController.isStringEmpty(tsbzQtjskhsh.getXjshyj()) && tsbzQtjskhsh.getXjshyj().equals("1")) {
            tsbzQtjskhsh.setXjshr(SecurityUtils.getLoginUser().getUser().getUserId());
            tsbzQtjskhsh.setStatus("2");
        }
        //区级
        if (!schoolCommonController.isStringEmpty(tsbzQtjskhsh.getQjshyj()) && tsbzQtjskhsh.getQjshyj().equals("1")) {
            tsbzQtjskhsh.setQjshr(SecurityUtils.getLoginUser().getUser().getUserId());
            tsbzQtjskhsh.setStatus("9");
        }
        return toAjax(tsbzQtjskhshService.updateTsbzQtjskhsh(tsbzQtjskhsh));
    }

    /**
     * 删除群体教师考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:remove')")
    @Log(title = "群体教师考核审核过程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjskhshService.deleteTsbzQtjskhshByIds(ids));
    }

    /**
     * 回退考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.UPDATE)
    @PostMapping("/back/{id}/{status}")
    public AjaxResult back(@PathVariable Long id, @PathVariable String status) {
        TsbzQtjskhsh tsbzQtjskhsh = new TsbzQtjskhsh();
        tsbzQtjskhsh.setId(id);
        tsbzQtjskhsh.setStatus(status);
        return toAjax(tsbzQtjskhshService.updateTsbzQtjskhsh(tsbzQtjskhsh));
    }

    /**
     * 提交考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhsh:add')" + "||@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.INSERT)
    @PostMapping("/check/{id}")
    public AjaxResult check(@PathVariable Long id) {
        TsbzQtjskhsh tsbzQtjskhsh = new TsbzQtjskhsh();
        tsbzQtjskhsh.setFaid(id);
        tsbzQtjskhsh.setStatus("1");
        Long jsId=schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId());

        //将所有数据改为提交状态
        TsbzQtjskhgcsj tsbzQtjskhgcsj=new TsbzQtjskhgcsj();
        tsbzQtjskhgcsj.setFaid(id);
        tsbzQtjskhgcsj.setSubstatus("1");
        tsbzQtjskhgcsj.setCreateuserid(jsId);
        tsbzQtjskhgcsjService.updateTsbzQtjskhgcsjStatue(tsbzQtjskhgcsj);
        ////////////////////////

        tsbzQtjskhsh.setJsid(jsId);
        tsbzQtjskhsh.setCreateuseird(jsId);
        return toAjax(tsbzQtjskhshService.insertTsbzQtjskhsh(tsbzQtjskhsh));
    }
}
