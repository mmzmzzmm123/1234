package com.ruoyi.web.controller.zcrpsgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.zcrpsgl.domain.TsbzZcrzjzmd;
import com.ruoyi.zcrpsgl.service.ITsbzZcrzjzmdService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主持人报名专家组名单Controller
 * 
 * @author tsbz
 * @date 2021-04-21
 */
@RestController
@RequestMapping("/zcrpsgl/zcrzjzmd")
public class TsbzZcrzjzmdController extends BaseController
{
    @Autowired
    private ITsbzZcrzjzmdService tsbzZcrzjzmdService;

    /**
     * 查询主持人报名专家组名单列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        startPage();
        List<TsbzZcrzjzmd> list = tsbzZcrzjzmdService.selectTsbzZcrzjzmdList(tsbzZcrzjzmd);
        return getDataTable(list);
    }

    /**
     * 导出主持人报名专家组名单列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:export')")
    @Log(title = "主持人报名专家组名单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        List<TsbzZcrzjzmd> list = tsbzZcrzjzmdService.selectTsbzZcrzjzmdList(tsbzZcrzjzmd);
        ExcelUtil<TsbzZcrzjzmd> util = new ExcelUtil<TsbzZcrzjzmd>(TsbzZcrzjzmd.class);
        return util.exportExcel(list, "zcrzjzmd");
    }

    /**
     * 获取主持人报名专家组名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzZcrzjzmdService.selectTsbzZcrzjzmdById(id));
    }

    /**
     * 新增主持人报名专家组名单
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:add')")
    @Log(title = "主持人报名专家组名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        tsbzZcrzjzmd.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzZcrzjzmd.setCreateTime(new Date());
        return toAjax(tsbzZcrzjzmdService.insertTsbzZcrzjzmd(tsbzZcrzjzmd));
    }

    /**
     * 修改主持人报名专家组名单
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:edit')")
    @Log(title = "主持人报名专家组名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        return toAjax(tsbzZcrzjzmdService.updateTsbzZcrzjzmd(tsbzZcrzjzmd));
    }

    /**
     * 删除主持人报名专家组名单
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrzjzmd:remove')")
    @Log(title = "主持人报名专家组名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzZcrzjzmdService.deleteTsbzZcrzjzmdByIds(ids));
    }
}
