package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.vod20170321.models.SearchMediaResponse;
import com.aliyun.vod20170321.models.SearchMediaResponseBody;
import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysNutritionalVideoMapper;
import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.service.ISysNutritionalVideoService;

/**
 * 营养视频Service业务层处理
 *
 * @author xzj
 * @date 2021-04-29
 */
@Service
public class SysNutritionalVideoServiceImpl implements ISysNutritionalVideoService
{
    @Autowired
    private SysNutritionalVideoMapper sysNutritionalVideoMapper;

    /**
     * 查询营养视频
     *
     * @param id 营养视频ID
     * @return 营养视频
     */
    @Override
    public SysNutritionalVideo selectSysNutritionalVideoById(Long id)
    {
        return sysNutritionalVideoMapper.selectSysNutritionalVideoById(id);
    }

    /**
     * 查询营养视频列表
     *
     * @param sysNutritionalVideo 营养视频
     * @return 营养视频
     */
    @Override
    public List<SysNutritionalVideo> selectSysNutritionalVideoList(SysNutritionalVideo sysNutritionalVideo, boolean flag)
    {
        List<SysNutritionalVideo> list = sysNutritionalVideoMapper.selectSysNutritionalVideoList(sysNutritionalVideo);
        if(flag && list != null && list.size() > 0){
            List<String> fileUrl = new ArrayList<>();
            for (SysNutritionalVideo video : list) {
                fileUrl.add(video.getCoverUrl());
            }
            List<String> downUrlList = AliyunOSSUtils.generatePresignedUrl(fileUrl);
            if(downUrlList != null && downUrlList.size() > 0){
                int index = 0;
                for (String downUrl : downUrlList) {
                    list.get(index).setCoverUrl(downUrl);
                    index++;
                }
            }
        }
        return list;
    }

    /**
     * 新增营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    @Override
    public int insertSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo)
    {
        sysNutritionalVideo.setCreateTime(DateUtils.getNowDate());
        return sysNutritionalVideoMapper.insertSysNutritionalVideo(sysNutritionalVideo);
    }

    /**
     * 修改营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    @Override
    public int updateSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo)
    {
        sysNutritionalVideo.setUpdateTime(DateUtils.getNowDate());
        return sysNutritionalVideoMapper.updateSysNutritionalVideo(sysNutritionalVideo);
    }

    /**
     * 批量删除营养视频
     *
     * @param ids 需要删除的营养视频ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionalVideoByIds(Long[] ids)
    {
        return sysNutritionalVideoMapper.deleteSysNutritionalVideoByIds(ids);
    }

    /**
     * 删除营养视频信息
     *
     * @param id 营养视频ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionalVideoById(Long id)
    {
        return sysNutritionalVideoMapper.deleteSysNutritionalVideoById(id);
    }

    /**
     * 获取视频
     * @param videoId
     * @return
     */
    public SysNutritionalVideo selectSysNutritionalVideByVideoId(String videoId){
        return sysNutritionalVideoMapper.selectSysNutritionalVideByVideoId(videoId);
    }

    /**
     * 阿里云视频查询检索
     * @return
     */
    public Map<String,Object> searchVideo(String key, Integer showFlag, Integer pageNo, Integer pageSize, String scrollToken){
        pageSize = pageSize.intValue() > 100 ? 10 : pageSize;
        long total = 0;
        String newScrollToken = null;
        List<SysNutritionalVideo> nutritionalVideoList = new ArrayList<>();
        try {
            SearchMediaResponse response = AliyunVideoUtils.searchVideo(key, getStatusString(showFlag), pageNo, pageSize, scrollToken);
            if(response != null){
                SearchMediaResponseBody body  = response.body;
                total = body.total;
                newScrollToken = body.scrollToken;
                List<SearchMediaResponseBody.SearchMediaResponseBodyMediaList> mediaList = body.mediaList;
                if(mediaList != null && mediaList.size() > 0){
                    for (SearchMediaResponseBody.SearchMediaResponseBodyMediaList media : mediaList) {
                        SysNutritionalVideo sysNutritionalVideo = new SysNutritionalVideo();
                        sysNutritionalVideo.setTitle(media.video.title);
                        sysNutritionalVideo.setCoverUrl(media.video.coverURL);
                        sysNutritionalVideo.setShowFlag(getStatus(media.video.getStatus()));
                        sysNutritionalVideo.setTags(media.video.tags);
                        //String createTime = media.video.creationTime;
                        sysNutritionalVideo.setDescription(media.video.description);
                        sysNutritionalVideo.setVideoId(media.video.videoId);
                        nutritionalVideoList.add(sysNutritionalVideo);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("newScrollToken", newScrollToken);
        result.put("nutritionalVideoList", nutritionalVideoList);
        return result;
    }

    private String getStatusString(Integer status){
        if(status == null){
            return "Normal,Blocked";
        }
        return status.intValue() == 1 ? "Normal" : "Blocked";
    }

    private Integer getStatus(String status){
        if(status == null){
            return 1;
        }
        return "Normal".equals(status) ? 1 :  0;
    }

}