package com.ruoyi.web.controller.zcrpsgl;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.zcrpsgl.domain.TsbzZcrpsfa;
import com.ruoyi.zcrpsgl.service.ITsbzZcrpsfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评审方案(主持人评审管理-评审方案)Controller
 * 
 * @author tsbz
 * @date 2021-04-14
 */
@RestController
@RequestMapping("/zcrpsgl/zcrpsfa")
public class TsbzZcrpsfaController extends BaseController
{
    @Autowired
    private ITsbzZcrpsfaService tsbzZcrpsfaService;

    /**
     * 查询评审方案(主持人评审管理-评审方案)列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzZcrpsfa tsbzZcrpsfa)
    {
        startPage();
        List<TsbzZcrpsfa> list = tsbzZcrpsfaService.selectTsbzZcrpsfaList(tsbzZcrpsfa);
        return getDataTable(list);
    }

    /**
     * 导出评审方案(主持人评审管理-评审方案)列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:export')")
    @Log(title = "评审方案(主持人评审管理-评审方案)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzZcrpsfa tsbzZcrpsfa)
    {
        List<TsbzZcrpsfa> list = tsbzZcrpsfaService.selectTsbzZcrpsfaList(tsbzZcrpsfa);
        ExcelUtil<TsbzZcrpsfa> util = new ExcelUtil<TsbzZcrpsfa>(TsbzZcrpsfa.class);
        return util.exportExcel(list, "zcrpsfa");
    }

    /**
     * 获取评审方案(主持人评审管理-评审方案)详细信息
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzZcrpsfaService.selectTsbzZcrpsfaById(id));
    }

    /**
     * 新增评审方案(主持人评审管理-评审方案)
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:add')")
    @Log(title = "评审方案(主持人评审管理-评审方案)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzZcrpsfa tsbzZcrpsfa)
    {
        return toAjax(tsbzZcrpsfaService.insertTsbzZcrpsfa(tsbzZcrpsfa));
    }

    /**
     * 修改评审方案(主持人评审管理-评审方案)
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:edit')")
    @Log(title = "评审方案(主持人评审管理-评审方案)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzZcrpsfa tsbzZcrpsfa)
    {
        return toAjax(tsbzZcrpsfaService.updateTsbzZcrpsfa(tsbzZcrpsfa));
    }

    /**
     * 删除评审方案(主持人评审管理-评审方案)
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:remove')")
    @Log(title = "评审方案(主持人评审管理-评审方案)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzZcrpsfaService.deleteTsbzZcrpsfaByIds(ids));
    }


    /**
     * 启用评审方案(主持人评审管理-评审方案)
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:openstatus')")
    @PostMapping(value = "/openstatus")
    public AjaxResult openstatus(Long id)
    {
        return toAjax(tsbzZcrpsfaService.openstatus(id));
    }


    /**
     * 停止评审方案(主持人评审管理-评审方案)
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrpsfa:stopstatus')")
    @PostMapping(value = "/stopstatus")
    public AjaxResult stopstatus(Long id)
    {
        return toAjax(tsbzZcrpsfaService.stopstatus(id));
    }
}
