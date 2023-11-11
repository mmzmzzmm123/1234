package com.ruoyi.platform.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.platform.domain.PlatformTextContent;
import com.ruoyi.platform.service.IPlatformTextContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文本内容管理Controller
 *
 * @author Lam
 * @date 2023-09-13
 */
@Api(tags = "admin-文本内容管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/platformTextContent")
public class PlatformTextContentController extends BaseController {

    private final IPlatformTextContentService platformTextContentService;


    @ApiOperation("查询文本内容管理列表")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformTextContent platformTextContent) {
        startPage();
        List<PlatformTextContent> list = platformTextContentService.selectPlatformTextContentList(platformTextContent);
        return getDataTable(list);
    }


    @ApiOperation("导出文本内容管理列表")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:export')")
    @Log(title = "文本内容管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformTextContent platformTextContent) {
        List<PlatformTextContent> list = platformTextContentService.selectPlatformTextContentList(platformTextContent);
        ExcelUtil<PlatformTextContent> util = new ExcelUtil<PlatformTextContent>(PlatformTextContent.class);
        util.exportExcel(response, list, "文本内容管理数据");
    }


    @ApiOperation("获取文本内容管理详细信息")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(platformTextContentService.selectPlatformTextContentById(id));
    }


    @ApiOperation("新增文本内容管理")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:add')")
    @Log(title = "文本内容管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformTextContent platformTextContent) {
        return toAjax(platformTextContentService.insertPlatformTextContent(platformTextContent));
    }


    @ApiOperation("修改文本内容管理")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:edit')")
    @Log(title = "文本内容管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformTextContent platformTextContent) {
        return toAjax(platformTextContentService.updatePlatformTextContent(platformTextContent));
    }


    @ApiOperation("删除文本内容管理")
    @PreAuthorize("@ss.hasPermi('platform:platformTextContent:remove')")
    @Log(title = "文本内容管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(platformTextContentService.deletePlatformTextContentByIds(ids));
    }
}
