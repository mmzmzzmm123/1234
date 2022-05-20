package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.PasswordConfig;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 参数配置 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/password")
public class PasswordConfigController extends BaseController
{
    @Autowired
    private ISysPasswordService sysPasswordService;

    /**
     * 获取参数配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:password:list')")
    @GetMapping("/list")
    public AjaxResult list()
    {
        PasswordConfig passwordConfig = sysPasswordService.selectConfig();
        return AjaxResult.success(passwordConfig);
    }



    /**
     * 修改参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PasswordConfig config)
    {
        return toAjax(sysPasswordService.saveConfig(config));
    }

    /**
     * 刷新参数缓存
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        sysPasswordService.resetConfigCache();
        return AjaxResult.success();
    }
}
