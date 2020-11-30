package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.domain.ByTrainVideo;
import com.ruoyi.project.benyi.service.IByChildService;
import com.ruoyi.project.benyi.service.IByTrainVideoService;
import com.ruoyi.project.common.SchoolCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 班级信息Controller
 *
 * @author tsbz
 * @date 2020-11-30
 */
@RestController
@RequestMapping("/system/home")
public class HomeController extends BaseController {

    @Autowired
    private IByChildService byChildService;
    @Autowired
    private IByTrainVideoService byTrainVideoService;
    @Autowired
    private SchoolCommon schoolCommon;

    @GetMapping("/info")
    public AjaxResult getInfo() {
        AjaxResult ajaxResult = AjaxResult.success();

        ByChild byChild = new ByChild();
        byChild.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        //判断是否为班主任
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            byChild.setClassid(classId);
        }
        List<ByChild> listchild = byChildService.selectByChildList(byChild);
        ajaxResult.put("childcount", listchild.size());//获取当前用户的幼儿总数

        ByTrainVideo byTrainVideo=new ByTrainVideo();
        List<ByTrainVideo> listvideo = byTrainVideoService.selectByTrainVideoList(byTrainVideo);
        ajaxResult.put("videocount", listvideo.size());//获取视频总数


        return ajaxResult;
    }

}
