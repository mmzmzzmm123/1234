package com.geek.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.geek.common.annotation.Log;
import com.geek.common.core.controller.BaseController;
import com.geek.common.core.domain.AjaxResult;
import com.geek.common.enums.BusinessType;
import com.geek.system.domain.SysOss;
import com.geek.system.service.ISysOssService;
import com.geek.common.utils.poi.ExcelUtil;
import com.geek.common.core.page.TableDataInfo;

/**
 * 文件上传Controller
 * 
 * @author xuek
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/system/oss")
public class SysOssController extends BaseController{

    @Autowired
    private ISysOssService sysOssService;

    /**
     * 查询文件上传列表
     */
    @PreAuthorize("@ss.hasPermi('system:oss:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOss sysOss){

        startPage();
        List<SysOss> list = sysOssService.selectSysOssList(sysOss);
        return getDataTable(list);
    }

    /**
     * 导出文件上传列表
     */
    @PreAuthorize("@ss.hasPermi('system:oss:export')")
    @Log(title = "文件上传", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOss sysOss){

        List<SysOss> list = sysOssService.selectSysOssList(sysOss);
        ExcelUtil<SysOss> util = new ExcelUtil<SysOss>(SysOss.class);
        util.exportExcel(response, list, "文件上传数据");
    }

    /**
     * 获取文件上传详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:oss:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id){

        return success(sysOssService.selectSysOssById(id));
    }

    /**
     * 新增文件上传
     */
    @PreAuthorize("@ss.hasPermi('system:oss:add')")
    @Log(title = "文件上传", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOss sysOss){

        return toAjax(sysOssService.insertSysOss(sysOss));
    }

    /**
     * 修改文件上传
     */
    @PreAuthorize("@ss.hasPermi('system:oss:edit')")
    @Log(title = "文件上传", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysOss sysOss){

        return toAjax(sysOssService.updateSysOss(sysOss));
    }

    /**
     * 删除文件上传
     */
    @PreAuthorize("@ss.hasPermi('system:oss:remove')")
    @Log(title = "文件上传", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){

        return toAjax(sysOssService.deleteSysOssByIds(ids));
    }
}
