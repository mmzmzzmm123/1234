package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.jjpt.business.domain.Repo;
import com.jjpt.business.service.IRepoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 题库信息Controller
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/business/repo")
public class RepoController extends BaseController
{
    @Autowired
    private IRepoService repoService;

    /**
     * 查询题库信息列表
     */
    /*@PreAuthorize("@ss.hasPermi('bussiness:repo:list')")*/
    @GetMapping("/list")
    public TableDataInfo list(Repo repo)
    {
        startPage();
        List<Repo> list = repoService.selectRepoList(repo);
        return getDataTable(list);
    }

    /**
     * 导出题库信息列表
     */
    /*@PreAuthorize("@ss.hasPermi('bussiness:repo:export')")*/
    @Log(title = "题库信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Repo repo)
    {
        List<Repo> list = repoService.selectRepoList(repo);
        ExcelUtil<Repo> util = new ExcelUtil<Repo>(Repo.class);
        util.exportExcel(response, list, "题库信息数据");
    }

    /**
     * 获取题库信息详细信息
     */
   /* @PreAuthorize("@ss.hasPermi('bussiness:repo:query')")*/
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(repoService.selectRepoById(id));
    }

    /**
     * 新增题库信息
     */
   /* @PreAuthorize("@ss.hasPermi('bussiness:repo:add')")*/
    @Log(title = "题库信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Repo repo)
    {
        return toAjax(repoService.insertRepo(repo));
    }

    /**
     * 修改题库信息
     */
   /* @PreAuthorize("@ss.hasPermi('bussiness:repo:edit')")*/
    @Log(title = "题库信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Repo repo)
    {
        return toAjax(repoService.updateRepo(repo));
    }

    /**
     * 删除题库信息
     */
   /* @PreAuthorize("@ss.hasPermi('bussiness:repo:remove')")*/
    @Log(title = "题库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(repoService.deleteRepoByIds(ids));
    }
}
