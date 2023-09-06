package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.psychology.service.IPsyConsultColumnService;
import com.ruoyi.psychology.vo.PsyConsultColumnVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询小组Controller
 *
 * @author ruoyi
 * @date 2023-09-04
 */
@RestController
@RequestMapping("/psychology/team")
public class PsyConsultTeamController extends BaseController
{

    @Resource
    private IPsyConsultColumnService service;

    /**
     * 查询咨询小组列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:team:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultColumnVO req)
    {
        startPage();
        req.setCat("1");
        List<PsyConsultColumnVO> list = service.getList(req);
        return getDataTable(list);
    }

    /**
     * 获取咨询小组详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:team:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(service.getOne(id));
    }

    /**
     * 新增咨询小组
     */
    @PreAuthorize("@ss.hasPermi('psychology:team:add')")
    @Log(title = "咨询小组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultColumnVO req)
    {
        req.setCat("1");
        return toAjax(service.add(req));
    }

    /**
     * 修改咨询小组
     */
    @PreAuthorize("@ss.hasPermi('psychology:team:edit')")
    @Log(title = "咨询小组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultColumnVO req)
    {
        req.setCat("1");
        return toAjax(service.update(req));
    }

    /**
     * 删除咨询小组
     */
    @PreAuthorize("@ss.hasPermi('psychology:team:remove')")
    @Log(title = "咨询小组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(service.delete(id));
    }


}
