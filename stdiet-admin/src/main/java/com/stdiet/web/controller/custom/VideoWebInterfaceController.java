package com.stdiet.web.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.vod20170321.models.GetPlayInfoResponseBody;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.dto.response.NutritionalVideoResponse;
import com.stdiet.custom.dto.response.VideoClassifyResponse;
import com.stdiet.custom.service.ISysNutritionalVideoService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysVideoClassifyService;
import com.stdiet.custom.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/web/video")
public class VideoWebInterfaceController extends BaseController {

    @Autowired
    private ISysVideoClassifyService sysVideoClassifyService;

    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;

    @Autowired
    private ISysOrderService iSysOrderService;

    /**
     * 查询视频分类目录列表
     */
    @GetMapping("/getAllClassify")
    public TableDataInfo getClassifyAndVideo(SysVideoClassify sysVideoClassify, HttpServletRequest request) {

        JSONObject result = CookieUtils.checkCookieValida(request, "token");
        if (result.getInteger("code") != 200) {
            TableDataInfo errInfo = new TableDataInfo();
            errInfo.setCode(result.getInteger("code"));
            errInfo.setMsg(result.getString("msg"));
            return errInfo;
        }

        // 检查是否已消费
        SysOrder order = new SysOrder();
        order.setPhone(result.getString("phone"));
        List<SysOrder> orders = iSysOrderService.selectSysOrderList(order);

        //不分页则不需要传pageNum、pageSize参数
        startPage();
        //父级分类ID 0表示主分类
        sysVideoClassify.setParentId(sysVideoClassify.getParentId() == null ? 0L : sysVideoClassify.getParentId());
        //客户类型 0未付费客户-playLevel根据上传的标记  1已付费客户-playLevel全为0
        sysVideoClassify.setUserType(orders.size() > 0 ? 1 : 0);
        List<VideoClassifyResponse> list = sysVideoClassifyService.getAllClassifyAndVideo(sysVideoClassify);
        return getDataTable(list);
    }


    /**
     * 根据视频videoId获取视频详情以及播放地址
     *
     * @param videoId 阿里云中对应videoId
     * @return
     */
    @GetMapping("/getVideoDetail/{videoId}")
    public AjaxResult getVideoDetail(@PathVariable("videoId") String videoId, HttpServletRequest request) {
        JSONObject result = CookieUtils.checkCookieValida(request, "token");
        if (result.getInteger("code") != 200) {
            return AjaxResult.error(result.getInteger("code"), result.getString("msg"));
        }

        if (StringUtils.isEmpty(videoId)) {
            return AjaxResult.error("视频加载失败");
        }
        SysNutritionalVideo sysNutritionalVideo = sysNutritionalVideoService.selectSysNutritionalVideByVideoId(videoId);
        if (sysNutritionalVideo == null) {
            return AjaxResult.error("视频加载失败");
        }
        NutritionalVideoResponse nutritionalVideoResponse = new NutritionalVideoResponse();
        try {
            GetPlayInfoResponseBody playInfoResponseBody = AliyunVideoUtils.getVideoVisitDetail(videoId);
            List<GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo> playList = playInfoResponseBody.playInfoList.playInfo;
            if (playList != null && playList.size() > 0) {
                //播放地址
                nutritionalVideoResponse.setPlayUrl(playList.get(0).getPlayURL());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("视频加载失败");
        }
        //视频封面URL
        if (StringUtils.isNotEmpty(sysNutritionalVideo.getCoverUrl())) {
            nutritionalVideoResponse.setCoverUrl(AliyunOSSUtils.generatePresignedUrl(sysNutritionalVideo.getCoverUrl()));
        } else {
            nutritionalVideoResponse.setCoverUrl(AliyunVideoUtils.getVideoCoverUrl(videoId));
        }
        //视频描述
        nutritionalVideoResponse.setDescription(sysNutritionalVideo.getDescription());
        //视频标题
        nutritionalVideoResponse.setTitle(sysNutritionalVideo.getTitle());
        //视频播放量
        nutritionalVideoResponse.setPlayNum(sysNutritionalVideo.getPlayNum());

        return AjaxResult.success(nutritionalVideoResponse);
    }

    /**
     * 更新视频播放次数
     *
     * @param videoId 阿里云中对应videoId
     * @return
     */
    @GetMapping(value = "/updateVideoPlayNum")
    public AjaxResult updateVideoPlayNum(@RequestParam(value = "videoId") String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return AjaxResult.error("更新失败");
        }
        return toAjax(sysNutritionalVideoService.updateVideoPlayNum(videoId));
    }
}
