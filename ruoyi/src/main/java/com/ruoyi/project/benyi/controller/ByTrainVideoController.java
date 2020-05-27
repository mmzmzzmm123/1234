package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import com.qiniu.util.Auth;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.config.FileConfig;
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
import com.ruoyi.project.benyi.domain.ByTrainVideo;
import com.ruoyi.project.benyi.service.IByTrainVideoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 培训Controller
 * 
 * @author tsbz
 * @date 2020-05-25
 */
@RestController
@RequestMapping("/benyi/video")
public class ByTrainVideoController extends BaseController
{
    @Autowired
    private IByTrainVideoService byTrainVideoService;


    /**
     * 查询培训列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTrainVideo byTrainVideo)
    {
        startPage();
        List<ByTrainVideo> list = byTrainVideoService.selectByTrainVideoList(byTrainVideo);
        return getDataTable(list);
    }

    /**
     * 导出培训列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:export')")
    @Log(title = "培训", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTrainVideo byTrainVideo)
    {
        List<ByTrainVideo> list = byTrainVideoService.selectByTrainVideoList(byTrainVideo);
        ExcelUtil<ByTrainVideo> util = new ExcelUtil<ByTrainVideo>(ByTrainVideo.class);
        return util.exportExcel(list, "video");
    }

    /**
     * 获取培训详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byTrainVideoService.selectByTrainVideoById(id));
    }

    /**
     * 新增培训
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:add')")
    @Log(title = "培训", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTrainVideo byTrainVideo)
    {
        byTrainVideo.setCreatetime(new Date());
        byTrainVideo.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byTrainVideoService.insertByTrainVideo(byTrainVideo));
    }

    /**
     * 修改培训
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:edit')")
    @Log(title = "培训", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTrainVideo byTrainVideo)
    {
        return toAjax(byTrainVideoService.updateByTrainVideo(byTrainVideo));
    }

    /**
     * 删除培训
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:remove')")
    @Log(title = "培训", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byTrainVideoService.deleteByTrainVideoByIds(ids));
    }
}
