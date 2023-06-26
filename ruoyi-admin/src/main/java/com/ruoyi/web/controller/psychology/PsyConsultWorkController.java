package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import org.apache.commons.collections4.CollectionUtils;
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
@RequestMapping("/psychology/work")
public class PsyConsultWorkController extends BaseController
{
    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    /**
     * 查询咨询服务列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultWorkVO req)
    {
        startPage();
        List<PsyConsultWork> list = psyConsultWorkService.getList(req);
        return getDataTable(list);
    }

    /**
     * 导出咨询服务列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:export')")
    @Log(title = "咨询服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultWorkVO req)
    {
        List<PsyConsultWork> list = psyConsultWorkService.getList(req);
        ExcelUtil<PsyConsultWork> util = new ExcelUtil<PsyConsultWork>(PsyConsultWork.class);
        util.exportExcel(response, list, "咨询服务数据");
    }

    /**
     * 获取咨询服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultWorkService.getOne(id));
    }

    /**
     * 新增咨询服务
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:add')")
    @Log(title = "咨询服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultWorkVO req)
    {
        List<PsyConsultWork> works = psyConsultWorkService.checkDataUnique(req);
        if (!CollectionUtils.isEmpty(works)) {
            return error("所选时段已存在排班，请修改时段后重试");
        }
        return toAjax(psyConsultWorkService.add(req));
    }

    /**
     * 修改咨询服务
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:edit')")
    @Log(title = "咨询服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultWorkVO req)
    {
        List<PsyConsultWork> works = psyConsultWorkService.checkDataUnique(req);
        if (!CollectionUtils.isEmpty(works)) {
            return error("所选时段已存在排班，请修改时段后重试");
        }
        return toAjax(psyConsultWorkService.update(req));
    }

    /**
     * 删除咨询服务
     */
    @PreAuthorize("@ss.hasPermi('psychology:work:remove')")
    @Log(title = "咨询服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultWorkService.deleteAll(ids));
    }
}
