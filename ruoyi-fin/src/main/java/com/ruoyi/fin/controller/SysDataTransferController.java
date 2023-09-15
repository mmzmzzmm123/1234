package com.ruoyi.fin.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fin.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
