package com.stdiet.web.controller.custom;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.dto.response.VideoClassifyResponse;
import com.stdiet.custom.service.ISysVideoClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/web/video")
public class VideoWebInterfaceController {

    @Autowired
    private ISysVideoClassifyService sysVideoClassifyService;

    /**
     * 查询视频分类目录列表
     */
    @GetMapping("/getAllClassify")
    public AjaxResult getClassifyAndVideo(SysVideoClassify sysVideoClassify)
    {
        List<SysVideoClassify> list = sysVideoClassifyService.getAllClassifyAndVideo(0L);
        return AjaxResult.success(list);
    }
}
