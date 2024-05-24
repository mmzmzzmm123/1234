package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jjpt.business.domain.dto.ElRepoDto;
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
import com.jjpt.business.domain.ElRepo;
import com.jjpt.business.service.IElRepoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 题库管理Controller
 * 
 * @author ruoyi
 * @date 2024-05-16
 */
@RestController
@RequestMapping("/exammanage/repo")
public class ElRepoController extends BaseController
{
    @Autowired
    private IElRepoService elRepoService;

    /**
     * 查询题库管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElRepo elRepo)
    {
        startPage();
        List<ElRepoDto> list = elRepoService.selectElRepoList(elRepo);
        return getDataTable(list);
    }

    /**
     * 查询题库管理列表All
     */

    @GetMapping("/listAll")
    public AjaxResult listAll(ElRepo elRepo)
    {
        return success(elRepoService.selectElRepoList(elRepo));
    }

    /**
     * 导出题库管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:export')")
    @Log(title = "题库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ElRepo elRepo)
    {
        List<ElRepoDto> list = elRepoService.selectElRepoList(elRepo);
        ExcelUtil<ElRepoDto> util = new ExcelUtil<ElRepoDto>(ElRepoDto.class);
        util.exportExcel(response, list, "题库管理数据");
    }

    /**
     * 获取题库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elRepoService.selectElRepoById(id));
    }

    /**
     * 新增题库管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:add')")
    @Log(title = "题库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElRepo elRepo)
    {
        return toAjax(elRepoService.insertElRepo(elRepo));
    }

    /**
     * 修改题库管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:edit')")
    @Log(title = "题库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElRepo elRepo)
    {
        return toAjax(elRepoService.updateElRepo(elRepo));
    }

    /**
     * 删除题库管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:repo:remove')")
    @Log(title = "题库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elRepoService.deleteElRepoByIds(ids));
    }
}
