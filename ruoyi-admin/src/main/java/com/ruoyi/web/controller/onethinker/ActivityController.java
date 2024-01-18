package com.ruoyi.web.controller.onethinker;

import com.onethinker.onethinker.domain.Activity;
import com.onethinker.onethinker.dto.ActivityReqDTO;
import com.onethinker.onethinker.service.IActivityService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动Controller
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/onethinker/activity")
public class ActivityController extends BaseController {
    @Resource
    private IActivityService activityService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(Activity activity) {
        startPage();
        List<Activity> list = activityService.selectActivityList(activity);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:export')")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Activity activity) {
        List<Activity> list = activityService.selectActivityList(activity);
        ExcelUtil<Activity> util = new ExcelUtil<Activity>(Activity.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 获取活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(activityService.selectActivityById(id));
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:add')")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody ActivityReqDTO activity) {
        return toAjax(activityService.insertActivity(activity));
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:edit')")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update")
    public AjaxResult edit(@RequestBody Activity activity) {
        return toAjax(activityService.updateActivity(activity));
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('onethinker:activity:remove')")
    @Log(title = "活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(activityService.deleteActivityByIds(ids));
    }

    /**
     * 获取活动详细信息
     */
    @GetMapping(value = "/myBatisTest")
    public AjaxResult myBatisTest(@RequestParam("id") Long id) {
        return success(activityService.queryActivityMyBatisPuls(id));
    }
}
