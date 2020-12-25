package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.BySchoolNews;
import com.ruoyi.project.benyi.service.IBySchoolNewsService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 新闻中心Controller
 *
 * @author tsbz
 * @date 2020-12-25
 */
@RestController
@RequestMapping("/benyi/news")
public class BySchoolNewsController extends BaseController {
    @Autowired
    private IBySchoolNewsService bySchoolNewsService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询新闻中心列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchoolNews bySchoolNews) {
        startPage();
        List<BySchoolNews> list = bySchoolNewsService.selectBySchoolNewsList(bySchoolNews);
        return getDataTable(list);
    }

    /**
     * 导出新闻中心列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:export')")
    @Log(title = "新闻中心", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchoolNews bySchoolNews) {
        List<BySchoolNews> list = bySchoolNewsService.selectBySchoolNewsList(bySchoolNews);
        ExcelUtil<BySchoolNews> util = new ExcelUtil<BySchoolNews>(BySchoolNews.class);
        return util.exportExcel(list, "news");
    }

    /**
     * 获取新闻中心详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bySchoolNewsService.selectBySchoolNewsById(id));
    }

    /**
     * 新增新闻中心
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:add')")
    @Log(title = "新闻中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchoolNews bySchoolNews) {
        bySchoolNews.setCreateTime(new Date());
        bySchoolNews.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        bySchoolNews.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        bySchoolNews.setType("1");
        return toAjax(bySchoolNewsService.insertBySchoolNews(bySchoolNews));
    }

    /**
     * 修改新闻中心
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:edit')")
    @Log(title = "新闻中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchoolNews bySchoolNews) {
        return toAjax(bySchoolNewsService.updateBySchoolNews(bySchoolNews));
    }

    /**
     * 删除新闻中心
     */
    @PreAuthorize("@ss.hasPermi('benyi:news:remove')")
    @Log(title = "新闻中心", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bySchoolNewsService.deleteBySchoolNewsByIds(ids));
    }
}
