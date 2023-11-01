package com.xinyu.idol.controller;

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
import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.controller.BaseController;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.domain.TArtResourceOrigin;
import com.xinyu.idol.service.ITArtResourceOriginService;
import com.xinyu.idol.common.utils.poi.ExcelUtil;
import com.xinyu.idol.common.core.page.TableDataInfo;

/**
 * 美术资源上传原件Controller
 * 
 * @author liyu
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/resourceOrigin/resourceOrigin")
public class TArtResourceOriginController extends BaseController
{
    @Autowired
    private ITArtResourceOriginService tArtResourceOriginService;

    /**
     * 查询美术资源上传原件列表
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:list')")
    @GetMapping("/list")
    public TableDataInfo list(TArtResourceOrigin tArtResourceOrigin)
    {
        startPage();
        List<TArtResourceOrigin> list = tArtResourceOriginService.selectTArtResourceOriginList(tArtResourceOrigin);
        return getDataTable(list);
    }

    /**
     * 导出美术资源上传原件列表
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:export')")
    @Log(title = "美术资源上传原件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TArtResourceOrigin tArtResourceOrigin)
    {
        List<TArtResourceOrigin> list = tArtResourceOriginService.selectTArtResourceOriginList(tArtResourceOrigin);
        ExcelUtil<TArtResourceOrigin> util = new ExcelUtil<TArtResourceOrigin>(TArtResourceOrigin.class);
        util.exportExcel(response, list, "美术资源上传原件数据");
    }

    /**
     * 获取美术资源上传原件详细信息
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tArtResourceOriginService.selectTArtResourceOriginById(id));
    }

    /**
     * 新增美术资源上传原件
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:add')")
    @Log(title = "美术资源上传原件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TArtResourceOrigin tArtResourceOrigin)
    {
        return toAjax(tArtResourceOriginService.insertTArtResourceOrigin(tArtResourceOrigin));
    }

    /**
     * 修改美术资源上传原件
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:edit')")
    @Log(title = "美术资源上传原件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TArtResourceOrigin tArtResourceOrigin)
    {
        return toAjax(tArtResourceOriginService.updateTArtResourceOrigin(tArtResourceOrigin));
    }

    /**
     * 删除美术资源上传原件
     */
    @PreAuthorize("@ss.hasPermi('resourceOrigin:resourceOrigin:remove')")
    @Log(title = "美术资源上传原件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tArtResourceOriginService.deleteTArtResourceOriginByIds(ids));
    }
}
