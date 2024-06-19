package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.psychology.service.IPsyConsultColumnService;
import com.renxin.psychology.vo.PsyConsultColumnVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询专栏Controller
 *
 * @author renxin
 * @date 2023-09-04
 */
@RestController
@RequestMapping("/psychology/column")
public class PsyConsultColumnController extends BaseController
{

    @Resource
    private IPsyConsultColumnService service;

    /**
     * 查询咨询专栏列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:column:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultColumnVO req)
    {
        startPage();
        req.setCat("0");
        List<PsyConsultColumnVO> list = service.getList(req);
        return getDataTable(list);
    }

    /**
     * 获取咨询专栏详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:column:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(service.getOne(id));
    }

    /**
     * 新增咨询专栏
     */
    @PreAuthorize("@ss.hasPermi('psychology:column:add')")
    @Log(title = "咨询专栏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultColumnVO req)
    {
        req.setCat("0");
        return toAjax(service.add(req));
    }

    /**
     * 修改咨询专栏
     */
    @PreAuthorize("@ss.hasPermi('psychology:column:edit')")
    @Log(title = "咨询专栏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultColumnVO req)
    {
        req.setCat("0");
        return toAjax(service.update(req));
    }

    /**
     * 删除咨询专栏
     */
    @PreAuthorize("@ss.hasPermi('psychology:column:remove')")
    @Log(title = "咨询专栏", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(service.delete(id));
    }


}
