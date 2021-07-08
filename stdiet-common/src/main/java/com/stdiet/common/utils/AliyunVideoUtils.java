package com.stdiet.common.utils;

import com.aliyun.vod20170321.models.*;
import com.aliyun.teaopenapi.models.*;
import com.stdiet.common.config.AliyunOSSConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliyunVideoUtils {

    public static com.aliyun.vod20170321.Client videoClient = null;

    public static final String default_definition = "FD,LD,SD,HD";

    public static final String default_stream_type = "video";

    public static final String default_output_type = "oss";

    public static final String default_formats = "cdn";

    //播放地址日期，30天
    public static final Long default_authTimeout = 2592000L;

    //阿里云回收站分类ID
    public static final Long default_delete_cateId = 1860L;

    public static final String search_field = "VideoId,Title,CoverURL,CateName,Tags,Status,Description,CreationTime";

    //默认截图模板
    public static final String defaultSnapshotTemplateId = "f8ccc3b5113ca8ea356ef9adf8c573b2";

    /**
     * 初始化视频点播Client
     * @return
     * @throws Exception
     */
    public static com.aliyun.vod20170321.Client createClient() throws Exception {
        if(videoClient == null){
            synchronized (com.aliyun.vod20170321.Client.class){
                if(videoClient == null){
                    Config config = new Config()
                            // 您的AccessKey ID
                            .setAccessKeyId(AliyunOSSConfig.AccessKeyID)
                            // 您的AccessKey Secret
                            .setAccessKeySecret(AliyunOSSConfig.AccessKeySecret);
                    // 访问的域名
                    config.endpoint = "vod.cn-shenzhen.aliyuncs.com";
                    videoClient = new com.aliyun.vod20170321.Client(config);
                }
            }
        }
        return videoClient;
    }

    /**
     * 分页获取视频列表（限于5000条，不能关键词搜索）
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return
     * @throws Exception
     */
    public static GetVideoListResponseBody getVideoListByPage(Long cateId, String status, Integer pageNo, Integer pageSize) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        GetVideoListRequest getVideoListRequest = new GetVideoListRequest()
                .setCateId(cateId == null ? null : cateId)
                .setStatus(status == null ? "Normal": status)
                .setPageNo(pageNo == null ? 1 : pageNo)
                .setPageSize(pageSize == null ? 10 : pageSize);
        GetVideoListResponse videoListResponse = client.getVideoList(getVideoListRequest);
        if(videoListResponse != null){
            return videoListResponse.getBody();
        }
        return null;
    }

    /**
     * 根据videoID获取视频访问地址信息
     * @param videoId
     * @throws Exception
     */
    public static GetPlayInfoResponseBody getVideoVisitDetail(String videoId) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        GetPlayInfoRequest getPlayInfoRequest = new GetPlayInfoRequest()
                .setVideoId(videoId)
                .setAuthTimeout(default_authTimeout)
                .setStreamType(default_stream_type)
                .setOutputType(default_output_type)
                .setFormats(default_formats);
        GetPlayInfoResponse getPlayInfoResponse = client.getPlayInfo(getPlayInfoRequest);
        if(getPlayInfoResponse != null){
            return getPlayInfoResponse.getBody();
        }
        return null;
    }

    /**
     * 根据videoID获取视频信息
     * @param videoId
     * @throws Exception
     */
    public static GetVideoInfoResponseBody getVideoById (String videoId) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        GetVideoInfoRequest getVideoInfoRequest = new GetVideoInfoRequest()
                .setVideoId(videoId);
        GetVideoInfoResponse response = client.getVideoInfo(getVideoInfoRequest);
        if(response != null){
            return response.body;
        }
        return null;
    }

    /**
     * 根据视频消息获取上传凭证
     * @param cateId
     * @param fileName
     * @param title
     * @param coverURL
     * @param tags
     * @param description
     */
    public static CreateUploadVideoResponse createUploadVideoRequest(Long cateId, String fileName, String title, String coverURL, String tags, String description) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        CreateUploadVideoRequest createUploadVideoRequest = new CreateUploadVideoRequest()
                .setDescription(description).setFileName(fileName).setTitle(title);
        if(StringUtils.isNotEmpty(coverURL)){
            createUploadVideoRequest.setCoverURL(coverURL);
        }
        if(cateId != null){
            createUploadVideoRequest.setCateId(cateId);
        }
        if(StringUtils.isNotEmpty(tags)){
            createUploadVideoRequest.setTags(tags);
        }
        return client.createUploadVideo(createUploadVideoRequest);
    }

    /**
     * 根据视频ID获取对应播放凭证
     * @param videoId
     * @return
     * @throws Exception
     */
    public  static String getVideoPlayAuth(String videoId) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        GetVideoPlayAuthRequest getVideoPlayAuthRequest = new GetVideoPlayAuthRequest()
                .setVideoId(videoId);
        GetVideoPlayAuthResponse response = client.getVideoPlayAuth(getVideoPlayAuthRequest);
        if(response != null){
            GetVideoPlayAuthResponseBody body = response.body;
            if(body != null && StringUtils.isNotEmpty(body.playAuth)){
                return body.playAuth;
            }
        }
        return null;
    }




    /**
     *
     * @param key
     * @param status
     * @param pageNo
     * @param pageSize
     * @throws Exception
     */
    public static SearchMediaResponse searchVideo(String key, String status, Integer pageNo, Integer pageSize, String scrollToken) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        SearchMediaRequest searchMediaRequest = new SearchMediaRequest()
                .setSearchType("video")
                .setFields(search_field)
                .setPageNo(pageNo == null ? 1 : pageNo)
                .setPageSize(pageSize == null ? 10 : pageSize)
                .setSortBy("CateId:Asc,CreationTime:Desc")
                .setScrollToken(scrollToken);
        if(StringUtils.isNotEmpty(key) || StringUtils.isNotEmpty(status)){
            String matchString = null;
            if(StringUtils.isNotEmpty(key)){
                matchString += StringUtils.format("(Title = '%s' or  Description = '%s')", key, key);
            }
            if(StringUtils.isNotEmpty(status)) {
                matchString += matchString == null ? "" : " and ";
                matchString += StringUtils.format("(Status = '%s')", status);
            }
            System.out.println(matchString);
            searchMediaRequest.setMatch(matchString);
        }
        return client.searchMedia(searchMediaRequest);
    }

    /**
     * 更新视频消息
     * @param videoId 视频ID必须
     * @param title
     * @param tags
     * @param description
     * @param cateId
     * @return
     * @throws Exception
     */
    public static String updateVideo(String videoId, String title, String tags, String description, Long cateId, String coverUrl) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        if(StringUtils.isEmpty(videoId)){
            return null;
        }
        UpdateVideoInfoRequest updateVideoInfoRequest = new UpdateVideoInfoRequest().setVideoId(videoId);
        if(StringUtils.isNotEmpty(title)){
            updateVideoInfoRequest.setTitle(title);
        }
        if(StringUtils.isNotEmpty(tags)){
            updateVideoInfoRequest.setTags(tags);
        }
        if(StringUtils.isNotEmpty(description)){
            updateVideoInfoRequest.setDescription(description);
        }
        if(cateId != null && cateId.longValue() > 0){
            updateVideoInfoRequest.setCateId(cateId);
        }
        if(StringUtils.isNotEmpty(coverUrl)){
            updateVideoInfoRequest.setCoverURL(coverUrl);
        }
        UpdateVideoInfoResponse updateVideoInfoResponse = client.updateVideoInfo(updateVideoInfoRequest);
        if(updateVideoInfoResponse != null){
            return updateVideoInfoResponse.body.requestId;
        }
        return null;
    }

    /**
     * 更新视频封面
     * @param videoId 视频ID必须
     * @param coverUrl 封面
     * @return
     * @throws Exception
     */
    public static String updateVideoCoverUrl(String videoId, String coverUrl) throws Exception{
        com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
        if(StringUtils.isEmpty(videoId) || StringUtils.isEmpty(coverUrl)){
            return null;
        }
        UpdateVideoInfoRequest updateVideoInfoRequest = new UpdateVideoInfoRequest().setVideoId(videoId);
        updateVideoInfoRequest.setCoverURL(coverUrl);
        UpdateVideoInfoResponse updateVideoInfoResponse = client.updateVideoInfo(updateVideoInfoRequest);
        if(updateVideoInfoResponse != null){
            return updateVideoInfoResponse.body.requestId;
        }
        return null;
    }

    /**
     * 将视频分类到回收站中
     * @param videoId
     * @return
     * @throws Exception
     */
    public static String delVideo(String videoId) throws Exception{
        return updateVideo(videoId, null,null,null, default_delete_cateId, null);
    }

    /**
     * 根据VideoId获取封面
     * @param videoId
     * @return
     */
    public static List<String> getVideoCoverUrl(List<String> videoId){
        List<String> result = new ArrayList<>();
        try{
            com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
            GetVideoInfosRequest getVideoInfosRequest = new GetVideoInfosRequest()
                    .setVideoIds(StringUtils.join(videoId.toArray(), ","));
            GetVideoInfosResponse getVideoInfosResponse = client.getVideoInfos(getVideoInfosRequest);
            if(getVideoInfosResponse != null){
                GetVideoInfosResponseBody body = getVideoInfosResponse.body;
                List<GetVideoInfosResponseBody.GetVideoInfosResponseBodyVideoList> videoList = body.videoList;
                if(videoList != null && videoList.size() > 0){
                    for (GetVideoInfosResponseBody.GetVideoInfosResponseBodyVideoList video : videoList) {
                        result.add(video.getCoverURL());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据VideoId获取封面
     * @param videoId
     * @return
     */
    public static String getVideoCoverUrl(String videoId){
        String coverUrl = null;
        try{
            com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
            GetVideoInfoRequest getVideoInfoRequest = new GetVideoInfoRequest()
                    .setVideoId(videoId);
            GetVideoInfoResponse response = client.getVideoInfo(getVideoInfoRequest);
            if(response != null){
                GetVideoInfoResponseBody body = response.body;
                GetVideoInfoResponseBody.GetVideoInfoResponseBodyVideo video = body.video;
                coverUrl = video.coverURL;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return coverUrl;
    }

    /**
     * 根据VideoId提交截图请求
     * @param videoId
     * @return
     */
    public static boolean submitVideoSnapshot(String videoId){
        try{
            com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
            SubmitSnapshotJobRequest submitSnapshotJobRequest = new SubmitSnapshotJobRequest()
                    .setSnapshotTemplateId(defaultSnapshotTemplateId)
                    .setVideoId(videoId);
            SubmitSnapshotJobResponse response = client.submitSnapshotJob(submitSnapshotJobRequest);

            return response != null && response.body != null && response.body.snapshotJob != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据VideoId获取普通截图信息
     * @param videoId
     * @return
     */
    public static List<String> getVideoSnapshot(String videoId){
        List<String> snapshotList = new ArrayList<>();
        try{
            com.aliyun.vod20170321.Client client = AliyunVideoUtils.createClient();
            ListSnapshotsRequest listSnapshotsRequest = new ListSnapshotsRequest()
                    .setVideoId(videoId)
                    .setSnapshotType("NormalSnapshot");
            ListSnapshotsResponse response = client.listSnapshots(listSnapshotsRequest);
            if(response != null && response.body != null){
                List<ListSnapshotsResponseBody.ListSnapshotsResponseBodyMediaSnapshotSnapshotsSnapshot>  listSnapshots = response.body.mediaSnapshot.snapshots.snapshot;
                if(listSnapshots != null && listSnapshots.size() > 0){
                    for (ListSnapshotsResponseBody.ListSnapshotsResponseBodyMediaSnapshotSnapshotsSnapshot snapshot : listSnapshots) {
                        snapshotList.add(snapshot.url);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return snapshotList;
    }


}
