package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByTrainVideoScore;
import com.ruoyi.project.benyi.service.IByTrainVideoScoreService;
import com.ruoyi.project.common.CommonController;
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
public class ByTrainVideoController extends BaseController {
    @Autowired
    private IByTrainVideoService byTrainVideoService;
    @Autowired
    private CommonController commonController;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByTrainVideoScoreService byTrainVideoScoreService;


    /**
     * 查询培训列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTrainVideo byTrainVideo) {

        //判断type是否为空
        if (!schoolCommon.isStringEmpty(byTrainVideo.getType())) {
            byTrainVideo.setType(byTrainVideo.getType() + ",");
        }
        startPage();
        List<ByTrainVideo> list = byTrainVideoService.selectByTrainVideoList(byTrainVideo);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setVideourl(commonController.privateDownloadUrl(list.get(i).getVideourl()));
            }
        }

        return getDataTable(list);
    }

    /**
     * 查询培训评分和评价列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:list')")
    @GetMapping("/listscoreandfree/{id}")
    public TableDataInfo listscoreandfree(@PathVariable("id") Long id) {
        startPage();
        ByTrainVideoScore byTrainVideoScore=new ByTrainVideoScore();
        byTrainVideoScore.setVideoid(id);
        List<ByTrainVideoScore> list = byTrainVideoScoreService.selectByTrainVideoScoreList(byTrainVideoScore);
        return getDataTable(list);
    }

    /**
     * 导出培训列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:export')")
    @Log(title = "培训", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTrainVideo byTrainVideo) {
        List<ByTrainVideo> list = byTrainVideoService.selectByTrainVideoList(byTrainVideo);
        ExcelUtil<ByTrainVideo> util = new ExcelUtil<ByTrainVideo>(ByTrainVideo.class);
        return util.exportExcel(list, "video");
    }

    /**
     * 获取培训详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ByTrainVideo byTrainVideo = byTrainVideoService.selectByTrainVideoById(id);
        byTrainVideo.setVideourl(commonController.privateDownloadUrl(byTrainVideo.getVideourl()));
        byTrainVideo.setType(byTrainVideo.getType().substring(0, byTrainVideo.getType().length() - 1));//去掉后缀的,号
        return AjaxResult.success(byTrainVideo);
    }

    /**
     * 新增培训
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:add')")
    @Log(title = "培训", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTrainVideo byTrainVideo) {
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
    public AjaxResult edit(@RequestBody ByTrainVideo byTrainVideo) {
        return toAjax(byTrainVideoService.updateByTrainVideo(byTrainVideo));
    }

    /**
     * 删除培训
     */
    @PreAuthorize("@ss.hasPermi('benyi:video:remove')")
    @Log(title = "培训", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byTrainVideoService.deleteByTrainVideoByIds(ids));
    }
}
