package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.SysRole;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByFiles;
import com.ruoyi.project.benyi.service.IByFilesService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 文件管理Controller
 *
 * @author tsbz
 * @date 2020-08-12
 */
@RestController
@RequestMapping("/benyi/files")
public class ByFilesController extends BaseController {
    @Autowired
    private IByFilesService byFilesService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByFiles byFiles) {
        startPage();
        List<ByFiles> list = byFilesService.selectByFilesList(byFiles);
        return getDataTable(list);
    }

    /**
     * 查询文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:list')")
    @Log(title = "查询文件", businessType = BusinessType.QUERY)
    @GetMapping("/listbyrole")
    public TableDataInfo listbyrole(ByFiles byFiles) {

        String strRoles = "";
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        System.out.println(roles.size());
        if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    strRoles = strRoles + roles.get(i).getRoleId() + ";";
                }
        }
        byFiles.setRoles(strRoles);
        startPage();
        List<ByFiles> list = byFilesService.selectByFilesList(byFiles);
        return getDataTable(list);
    }

    /**
     * 导出文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:export')")
    @Log(title = "文件管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByFiles byFiles) {
        List<ByFiles> list = byFilesService.selectByFilesList(byFiles);
        ExcelUtil<ByFiles> util = new ExcelUtil<ByFiles>(ByFiles.class);
        return util.exportExcel(list, "files");
    }

    /**
     * 获取文件管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ByFiles byFiles = byFilesService.selectByFilesById(id);
        ajax.put(AjaxResult.DATA_TAG, byFiles);
        if (!schoolCommon.isStringEmpty(byFiles.getRoles())) {
            ajax.put("roles", byFiles.getRoles().split(";"));
        } else {
            ajax.put("roles", null);
        }
        return ajax;
    }

    /**
     * 新增文件管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:add')")
    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByFiles byFiles) {

        byFiles.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

        return toAjax(byFilesService.insertByFiles(byFiles));
    }

    /**
     * 修改文件管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:edit')")
    @Log(title = "文件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByFiles byFiles) {
        return toAjax(byFilesService.updateByFiles(byFiles));
    }

    /**
     * 删除文件管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:files:remove')")
    @Log(title = "文件管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byFilesService.deleteByFilesByIds(ids));
    }
}
