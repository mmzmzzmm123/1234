package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.vo.PsyConsultServeVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询服务Controller
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/psychology/serve")
public class PsyConsultServeController extends BaseController
{
    @Resource
    private IPsyConsultServeService psyConsultServeService;

    /**
     * 查询咨询服务列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:serve:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultServeVO req)
    {
        startPage();
        List<PsyConsultServe> list = psyConsultServeService.getList(req);
        return getDataTable(list);
    }

    @GetMapping("/getAll")
    public AjaxResult getAll(PsyConsultServeVO req)
    {
        if (req.getConsultId() == null) {
            return error("咨询师不能为空");
        }
        return AjaxResult.success(psyConsultServeService.getList(req));
    }

    /**
     * 导出咨询服务列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:serve:export')")
    @Log(title = "咨询服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultServeVO req)
    {
        List<PsyConsultServe> list = psyConsultServeService.getList(req);
        ExcelUtil<PsyConsultServe> util = new ExcelUtil<PsyConsultServe>(PsyConsultServe.class);
        util.exportExcel(response, list, "咨询服务数据");
    }

    /**
     * 获取咨询服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:serve:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultServeService.getOne(id));
    }

    /**
     * 新增咨询服务
     */
    @PreAuthorize("@ss.hasPermi('psychology:serve:add')")
    @Log(title = "咨询服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultServeVO req)
    {
        return toAjax(psyConsultServeService.add(req));
    }

    /**
     * 修改咨询服务
     */
//    @PreAuthorize("@ss.hasPermi('psychology:serve:edit')")
    @Log(title = "咨询服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody List<PsyConsultServe> serveList)
    {
        return toAjax(psyConsultServeService.updateBatch(serveList));
    }

    /**
     * 删除咨询服务
     */
    @PreAuthorize("@ss.hasPermi('psychology:serve:remove')")
    @Log(title = "咨询服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultServeService.deleteAll(ids));
    }
}
