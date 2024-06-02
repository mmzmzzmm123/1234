package com.baoli.sysmanage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.baoli.sysmanage.domain.BlBizAgentInfo;
import com.baoli.sysmanage.service.IBlBizAgentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 经销商档案Controller
 * 
 * @author niujs
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/sysmanage/agentInfo")
public class BlBizAgentInfoController extends BaseController
{
    @Autowired
    private IBlBizAgentInfoService blBizAgentInfoService;

    /**
     * 查询经销商档案列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlBizAgentInfo blBizAgentInfo)
    {
        startPage();
        List<BlBizAgentInfo> list = blBizAgentInfoService.selectBlBizAgentInfoList(blBizAgentInfo);
        return getDataTable(list);
    }

    /**
     * 导出经销商档案列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:export')")
    @Log(title = "经销商档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlBizAgentInfo blBizAgentInfo)
    {
        List<BlBizAgentInfo> list = blBizAgentInfoService.selectBlBizAgentInfoList(blBizAgentInfo);
        ExcelUtil<BlBizAgentInfo> util = new ExcelUtil<BlBizAgentInfo>(BlBizAgentInfo.class);
        util.exportExcel(response, list, "经销商档案数据");
    }

    /**
     * 获取经销商档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blBizAgentInfoService.selectBlBizAgentInfoById(id));
    }

    /**
     * 新增经销商档案
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:add')")
    @Log(title = "经销商档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlBizAgentInfo blBizAgentInfo)
    {
        return toAjax(blBizAgentInfoService.insertBlBizAgentInfo(blBizAgentInfo));
    }

    /**
     * 修改经销商档案
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:edit')")
    @Log(title = "经销商档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlBizAgentInfo blBizAgentInfo)
    {
        return toAjax(blBizAgentInfoService.updateBlBizAgentInfo(blBizAgentInfo));
    }

    /**
     * 删除经销商档案
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:agentInfo:remove')")
    @Log(title = "经销商档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blBizAgentInfoService.deleteBlBizAgentInfoByIds(ids));
    }
}
