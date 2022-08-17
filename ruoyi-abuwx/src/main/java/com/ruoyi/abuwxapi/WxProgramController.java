package com.ruoyi.abuwxapi;

import com.ruoyi.abuwx.domain.AbucoderPrograms;
import com.ruoyi.abuwx.service.IAbucoderProgramsService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author：AbuCoder QQ:932696181
 * 微信小程序获取推荐项目接口
 * Gitee:https://gitee.com/rahman
 */
@RestController
@RequestMapping("/wxapi")
public class WxProgramController {

    @Autowired
    IAbucoderProgramsService iAbucoderProgramsService;
    @GetMapping("/loadProgramms")
    public AjaxResult loadProgramms(AbucoderPrograms abucoderPrograms){
        List<AbucoderPrograms> programmslist = iAbucoderProgramsService.selectAbucoderProgramsList(abucoderPrograms);
        return AjaxResult.success(programmslist);
    }
}
