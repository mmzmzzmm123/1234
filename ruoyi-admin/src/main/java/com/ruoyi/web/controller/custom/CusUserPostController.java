package com.ruoyi.web.controller.custom;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.custom.domain.CusUserPost;
import com.ruoyi.custom.service.ICusUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售订单Controller
 *
 * @author wonder
 * @date 2020-09-24
 */
@RestController
@RequestMapping("/custom/post")
public class CusUserPostController extends BaseController {
    @Autowired
    private ICusUserPostService cusUserPostService;

    @GetMapping("/options")
    public AjaxResult getOptions() {
        AjaxResult ajax = AjaxResult.success();
        List<CusUserPost> userPosts = cusUserPostService.selectAllCusUserPost();

        ajax.put(AjaxResult.DATA_TAG, userPosts);
        return ajax;
    }

}