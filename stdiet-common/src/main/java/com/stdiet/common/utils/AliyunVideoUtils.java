package com.stdiet.common.utils;

import com.aliyun.vod20170321.models.*;
import com.aliyun.teaopenapi.models.*;
import com.stdiet.common.config.AliyunOSSConfig;

public class AliyunVideoUtils {

    public static com.aliyun.vod20170321.Client videoClient = null;

    public static final String default_definition = "FD,LD,SD,HD";

    public static final String default_stream_type = "video";

    public static final String default_output_type = "oss";

    public static final String default_formats = "cdn";

    //播放地址日期，30天
    public static final Long default_authTimeout = 2592000L;

    public static final String search_field = "VideoId,Title,CoverURL,CateName,Tags,Status,Description,CreationTime";

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
                .setDescription(description)
                .setCoverURL(coverURL)
                .setFileName(fileName)
                .setTitle(title)
                .setCateId(cateId)
                .setTags(tags);
        return client.createUploadVideo(createUploadVideoRequest);
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







}
