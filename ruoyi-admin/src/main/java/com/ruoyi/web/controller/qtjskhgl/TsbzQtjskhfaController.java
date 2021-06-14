package com.ruoyi.web.controller.qtjskhgl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhfa;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhfaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核方案Controller
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhfa")
public class TsbzQtjskhfaController extends BaseController
{
    @Autowired
    private ITsbzQtjskhfaService tsbzQtjskhfaService;
    @Autowired
    private SchoolCommonController schoolCommonController;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询群体教师考核方案列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhfa tsbzQtjskhfa)
    {
        //获取当前登录账号的身份类型
        SysUser sysUser= userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId());
        if(!schoolCommonController.isStringEmpty(sysUser.getJssf())){
            tsbzQtjskhfa.setKhlx(sysUser.getJssf());
        }

        startPage();
        List<TsbzQtjskhfa> list = tsbzQtjskhfaService.selectTsbzQtjskhfaList(tsbzQtjskhfa);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核方案列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:export')")
    @Log(title = "群体教师考核方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhfa tsbzQtjskhfa)
    {
        List<TsbzQtjskhfa> list = tsbzQtjskhfaService.selectTsbzQtjskhfaList(tsbzQtjskhfa);
        ExcelUtil<TsbzQtjskhfa> util = new ExcelUtil<TsbzQtjskhfa>(TsbzQtjskhfa.class);
        return util.exportExcel(list, "qtjskhfa");
    }

    /**
     * 获取群体教师考核方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzQtjskhfaService.selectTsbzQtjskhfaById(id));
    }

    /**
     * 新增群体教师考核方案
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:add')")
    @Log(title = "群体教师考核方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhfa tsbzQtjskhfa)
    {
        tsbzQtjskhfa.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzQtjskhfaService.insertTsbzQtjskhfa(tsbzQtjskhfa));
    }

    /**
     * 修改群体教师考核方案
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:edit')")
    @Log(title = "群体教师考核方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhfa tsbzQtjskhfa)
    {
        return toAjax(tsbzQtjskhfaService.updateTsbzQtjskhfa(tsbzQtjskhfa));
    }

    /**
     * 删除群体教师考核方案
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhfa:remove')")
    @Log(title = "群体教师考核方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzQtjskhfaService.deleteTsbzQtjskhfaByIds(ids));
    }
}
