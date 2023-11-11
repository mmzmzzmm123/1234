package com.ruoyi.user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.service.IUserLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户等级记录Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户等级记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userLevel")
public class UserLevelController extends BaseController {

    private final IUserLevelService userLevelService;


    @ApiOperation("查询用户等级记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserLevel userLevel) {
        startPage();
        List<UserLevel> list = userLevelService.selectUserLevelList(userLevel);
        return getDataTable(list);
    }


    @ApiOperation("导出用户等级记录列表")
    @PreAuthorize("@ss.hasPermi('user:userLevel:export')")
    @Log(title = "用户等级记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLevel userLevel) {
        List<UserLevel> list = userLevelService.selectUserLevelList(userLevel);
        ExcelUtil<UserLevel> util = new ExcelUtil<UserLevel>(UserLevel.class);
        util.exportExcel(response, list, "用户等级记录数据");
    }


    @ApiOperation("获取用户等级记录详细信息")
    @PreAuthorize("@ss.hasPermi('user:userLevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userLevelService.selectUserLevelById(id));
    }


    @ApiOperation("新增用户等级记录")
    @PreAuthorize("@ss.hasPermi('user:userLevel:add')")
    @Log(title = "用户等级记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLevel userLevel) {
        return toAjax(userLevelService.insertUserLevel(userLevel));
    }


    @ApiOperation("修改用户等级记录")
    @PreAuthorize("@ss.hasPermi('user:userLevel:edit')")
    @Log(title = "用户等级记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLevel userLevel) {
        return toAjax(userLevelService.updateUserLevel(userLevel));
    }


    @ApiOperation("删除用户等级记录")
    @PreAuthorize("@ss.hasPermi('user:userLevel:remove')")
    @Log(title = "用户等级记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userLevelService.deleteUserLevelByIds(ids));
    }
}
