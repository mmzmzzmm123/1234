package com.onethinker.h5.controller;

import com.onethinker.activity.dto.ActivityResDTO;
import com.onethinker.activity.platform.ActivityStorage;
import com.onethinker.activity.service.ActivityStorageService;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangyouqi
 * @date 2024/5/31
 * H5活动API接口
 */
@RestController("webActivityController")
@RequestMapping("/onethinker/"+ ServicePathConstant.PREFIX_WEB_API_PATH +"/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityStorageService activityStorageService;


    /**
     * 查询当前活动信息
     */
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/list")
    public TableDataInfo queryActivityList() {
        startPage();
        List<ActivityResDTO> list = activityStorageService.queryActivityList();
        return getDataTable(list);
    }

    /**
     * 查询当前活动明细数据
     */
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/query")
    public AjaxResult queryActivity(@RequestParam Long activityId) {
        return success(activityStorageService.queryActivityBaseInfo(activityId));
    }




}
