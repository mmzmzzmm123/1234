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
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.service.IUserLevelConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户等级配置Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-用户等级配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/userLevelConfig")
public class UserLevelConfigController extends BaseController {

    private final IUserLevelConfigService userLevelConfigService;


    @ApiOperation("查询用户等级配置列表")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserLevelConfig userLevelConfig) {
        startPage();
        List<UserLevelConfig> list = userLevelConfigService.selectUserLevelConfigList(userLevelConfig);
        return getDataTable(list);
    }


    @ApiOperation("导出用户等级配置列表")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:export')")
    @Log(title = "用户等级配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserLevelConfig userLevelConfig) {
        List<UserLevelConfig> list = userLevelConfigService.selectUserLevelConfigList(userLevelConfig);
        ExcelUtil<UserLevelConfig> util = new ExcelUtil<UserLevelConfig>(UserLevelConfig.class);
        util.exportExcel(response, list, "用户等级配置数据");
    }


    @ApiOperation("获取用户等级配置详细信息")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userLevelConfigService.selectUserLevelConfigById(id));
    }


    @ApiOperation("新增用户等级配置")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:add')")
    @Log(title = "用户等级配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserLevelConfig userLevelConfig) {
        return toAjax(userLevelConfigService.insertUserLevelConfig(userLevelConfig));
    }


    @ApiOperation("修改用户等级配置")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:edit')")
    @Log(title = "用户等级配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserLevelConfig userLevelConfig) {
        return toAjax(userLevelConfigService.updateUserLevelConfig(userLevelConfig));
    }


    @ApiOperation("删除用户等级配置")
    @PreAuthorize("@ss.hasPermi('user:userLevelConfig:remove')")
    @Log(title = "用户等级配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userLevelConfigService.deleteUserLevelConfigByIds(ids));
    }
}
