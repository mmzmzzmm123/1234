package com.stdiet.web.controller.custom;

import java.util.ArrayList;
import java.util.List;

import com.aliyun.vod20170321.models.CreateUploadVideoResponse;
import com.aliyun.vod20170321.models.GetVideoListResponseBody;
import com.stdiet.common.utils.AliyunVideoUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.service.ISysNutritionalVideoService;

/**
 * 营养视频Controller
 *
 * @author xzj
 * @date 2021-04-29
 */
@RestController
@RequestMapping("/custom/nutritionalVideo")
public class SysNutritionalVideoController extends BaseController
{
    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;

    /**
     * 查询营养视频列表
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:list')")
    @GetMapping("/list")
    public AjaxResult list(SysNutritionalVideo sysNutritionalVideo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5")int pageSize)
    {
        AjaxResult result = AjaxResult.success();
        int total = 0;
        List<SysNutritionalVideo> nutritionalVideoList = new ArrayList<>();
        try{
            GetVideoListResponseBody videoListResponseBody = AliyunVideoUtils.getVideoListByPage(null, "Normal,Blocked", pageNum, pageSize);
            if(videoListResponseBody != null){
                total = videoListResponseBody.total;
                for (GetVideoListResponseBody.GetVideoListResponseBodyVideoListVideo video : videoListResponseBody.videoList.video) {
                    SysNutritionalVideo nutritionalVideo = new SysNutritionalVideo();
                    nutritionalVideo.setCoverUrl(video.getCoverURL());
                    nutritionalVideo.setTitle(video.getTitle());
                    nutritionalVideo.setVideoId(video.getVideoId());
                    nutritionalVideo.setDescription(video.getDescription());
                    nutritionalVideo.setTags(video.getTags());
                    nutritionalVideoList.add(nutritionalVideo);
                    nutritionalVideo.setShowFlag("Normal".equals(video.getStatus()) ? 1 : 0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("total",total);
        result.put("rows", nutritionalVideoList);
        return result;
    }

    /**
     * 获取营养视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysNutritionalVideoService.selectSysNutritionalVideoById(id));
    }

    /**
     * 新增营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:add')")
    @Log(title = "营养视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNutritionalVideo sysNutritionalVideo)
    {
        return toAjax(sysNutritionalVideoService.insertSysNutritionalVideo(sysNutritionalVideo));
    }

    /**
     * 获取视频上传凭证
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:add')")
    @Log(title = "获取视频上传凭证", businessType = BusinessType.INSERT)
    @PostMapping("/getUploadVideoAuth")
    public AjaxResult getUploadVideoAuth(@RequestBody SysNutritionalVideo sysNutritionalVideo)
    {
        AjaxResult result = AjaxResult.error();
        try {
            CreateUploadVideoResponse response = AliyunVideoUtils.createUploadVideoRequest(sysNutritionalVideo.getCateId(), sysNutritionalVideo.getFileName(), sysNutritionalVideo.getTitle(), sysNutritionalVideo.getCoverUrl(), sysNutritionalVideo.getTags(), sysNutritionalVideo.getDescription());
            if(response != null){
                result = AjaxResult.success();
                result.put("uploadAuth", response.body);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:edit')")
    @Log(title = "营养视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNutritionalVideo sysNutritionalVideo)
    {
        return toAjax(sysNutritionalVideoService.updateSysNutritionalVideo(sysNutritionalVideo));
    }

    /**
     * 删除营养视频
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:remove')")
    @Log(title = "营养视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysNutritionalVideoService.deleteSysNutritionalVideoByIds(ids));
    }
}