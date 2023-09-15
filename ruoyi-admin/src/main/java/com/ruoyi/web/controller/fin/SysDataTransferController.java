package com.ruoyi.web.controller.fin;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fin.service.ITransferService;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
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
@RequestMapping("/tool/transfer")
public class SysDataTransferController extends BaseController
{
    @Autowired
    private ITransferService transferService;

    @Log(title = "开始转换数据", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('tool:transfer')")
    @GetMapping(value = "/exec/{tabName}")
    public AjaxResult exec(@PathVariable String tabName)
    {
        transferService.execDataTransfer(tabName);
        return success();
    }

}
