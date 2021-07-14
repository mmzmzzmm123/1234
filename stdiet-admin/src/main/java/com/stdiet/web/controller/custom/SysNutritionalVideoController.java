package com.stdiet.web.controller.custom;

import java.util.List;

import com.aliyun.vod20170321.models.CreateUploadVideoResponse;
import com.aliyun.vod20170321.models.GetPlayInfoResponseBody;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import org.aspectj.weaver.loadtime.Aj;
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
    public TableDataInfo list(SysNutritionalVideo sysNutritionalVideo)
    {
        startPage();
        sysNutritionalVideo.setSortType(1);
        List<SysNutritionalVideo> list = sysNutritionalVideoService.selectSysNutritionalVideoList(sysNutritionalVideo, true);
        return getDataTable(list);
    }

    /**
     * 获取营养视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SysNutritionalVideo sysNutritionalVideos = sysNutritionalVideoService.selectSysNutritionalVideoById(id);
        if(sysNutritionalVideos != null && StringUtils.isNotEmpty(sysNutritionalVideos.getCoverUrl())){
            sysNutritionalVideos.setPreviewUrl(AliyunOSSUtils.generatePresignedUrl(sysNutritionalVideos.getCoverUrl()));
        }else{
            sysNutritionalVideos.setPreviewUrl(AliyunVideoUtils.getVideoCoverUrl(sysNutritionalVideos.getVideoId()));
        }
        return AjaxResult.success(sysNutritionalVideos);
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
            CreateUploadVideoResponse response = AliyunVideoUtils.createUploadVideoRequest(null, sysNutritionalVideo.getFileName(), sysNutritionalVideo.getTitle(), null, sysNutritionalVideo.getTags(), sysNutritionalVideo.getDescription());
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

    /**
     * 修改营养小知识是否微信展示状态
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:edit')")
    @Log(title = "微信展示状态修改", businessType = BusinessType.UPDATE)
    @GetMapping("/updateWxShow")
    public AjaxResult updateWxShow(@RequestParam("id")String id, @RequestParam("showFlag")Integer showFlag)
    {
        if(StringUtils.isEmpty(id) || showFlag == null){
            return AjaxResult.error();
        }
        Long[] ids = {Long.parseLong(id)};
        return toAjax(sysNutritionalVideoService.updateWxshowByIds(showFlag, ids));
    }

    /**
     * 获取营养视频播放地址
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:query')")
    @GetMapping(value = "/getVideoPlayUrlById/{id}")
    public AjaxResult getVideoPlayUrlById(@PathVariable("id") Long id)
    {
        SysNutritionalVideo sysNutritionalVideos = sysNutritionalVideoService.selectSysNutritionalVideoById(id);
        if(sysNutritionalVideos != null && StringUtils.isNotEmpty(sysNutritionalVideos.getVideoId())){
            try{
                GetPlayInfoResponseBody playInfoResponseBody = AliyunVideoUtils.getVideoVisitDetail(sysNutritionalVideos.getVideoId());
                List<GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo> playList = playInfoResponseBody.playInfoList.playInfo;
                if(playList != null && playList.size() > 0){
                    sysNutritionalVideos.setPlayUrl(playList.get(0).getPlayURL());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return AjaxResult.success(sysNutritionalVideos);
    }

    /**
     * 获取营养视频播放凭证
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:query')")
    @GetMapping(value = "/getVideoPlayAuth/{id}")
    public AjaxResult getVideoPlayAuth(@PathVariable("id") Long id)
    {
        SysNutritionalVideo sysNutritionalVideos = sysNutritionalVideoService.selectSysNutritionalVideoById(id);
        if(sysNutritionalVideos != null && StringUtils.isNotEmpty(sysNutritionalVideos.getVideoId())){
            try{
                String playAuth = AliyunVideoUtils.getVideoPlayAuth(sysNutritionalVideos.getVideoId());
                sysNutritionalVideos.setPlayAuth(playAuth);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return AjaxResult.success(sysNutritionalVideos);
    }



    /**
     * 根据视频videoId提交视频截图请求
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:add')")
    @GetMapping("/submitVideoSnapshot")
    public AjaxResult submitVideoSnapshot(@RequestParam("videoId")String videoId)
    {
        if(StringUtils.isEmpty(videoId)){
            return AjaxResult.error("视频资源不存在");
        }
        AjaxResult result = AjaxResult.error("截图请求失败");
        try {
            if(AliyunVideoUtils.submitVideoSnapshot(videoId)){
                result = AjaxResult.success();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据视频videoId获取视频截图
     */
    @PreAuthorize("@ss.hasPermi('custom:nutritionalVideo:add')")
    @GetMapping("/getVideoSnapshot")
    public AjaxResult getVideoSnapshot(@RequestParam("videoId")String videoId)
    {
        if(StringUtils.isEmpty(videoId)){
            return AjaxResult.error("视频资源不存在");
        }
        AjaxResult result = AjaxResult.error("截图不存在");
        try {
            List<String> videoSnapshotList = AliyunVideoUtils.getVideoSnapshot(videoId);
            if(videoSnapshotList != null){
                result = AjaxResult.success(videoSnapshotList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}