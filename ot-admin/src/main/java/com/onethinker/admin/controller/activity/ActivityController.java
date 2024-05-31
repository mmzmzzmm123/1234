package com.onethinker.admin.controller.activity;

import com.onethinker.activity.domain.Activity;
import com.onethinker.activity.dto.ActivityReqDTO;
import com.onethinker.activity.dto.ActivityResDTO;
import com.onethinker.activity.service.ActivityStorageService;
import com.onethinker.common.annotation.Log;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.BusinessType;
import com.onethinker.common.utils.bean.BeanUtils;
import com.onethinker.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台活动
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/onethinker/activity")
public class ActivityController extends BaseController {
    @Resource
    private ActivityStorageService activityService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/list")
    public TableDataInfo list(Activity activity) {
        startPage();
        List<ActivityResDTO> list = activityService.queryActivityBaseInfoList(activity);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:export')")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @PostMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, Activity activity) {
        List<ActivityResDTO> listRes = activityService.queryActivityBaseInfoList(activity);
        List<Activity> list = listRes.parallelStream().map(e -> {
            Activity temp = new Activity();
            BeanUtils.copyProperties(e, temp);
            return temp;
        }).collect(Collectors.toList());
        ExcelUtil<Activity> util = new ExcelUtil<Activity>(Activity.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 获取活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:query')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(activityService.queryActivityBaseInfo(id));
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:add')")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/add")
    public AjaxResult add(@RequestBody ActivityReqDTO activity) {
        activityService.insertActivity(activity);
        return success();
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:edit')")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/update")
    public AjaxResult edit(@RequestBody ActivityReqDTO activity) {
        activityService.updateActivity(activity);
        return success();
    }
}
