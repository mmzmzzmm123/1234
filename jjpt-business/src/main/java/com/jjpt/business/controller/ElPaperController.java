package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jjpt.business.domain.ElPaperQu;
import com.jjpt.business.domain.dto.PaperQuDetailDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jjpt.business.domain.ElPaper;
import com.jjpt.business.service.IElPaperService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试记录Controller
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
@RestController
@RequestMapping("/exammanage/paper")
public class ElPaperController extends BaseController
{
    @Autowired
    private IElPaperService elPaperService;

    /**
     * 查询考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElPaper elPaper)
    {
        startPage();
        List<ElPaper> list = elPaperService.selectElPaperList(elPaper);
        return getDataTable(list);
    }

    /**
     * 导出考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:export')")
    @Log(title = "考试记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ElPaper elPaper)
    {
        List<ElPaper> list = elPaperService.selectElPaperList(elPaper);
        ExcelUtil<ElPaper> util = new ExcelUtil<ElPaper>(ElPaper.class);
        util.exportExcel(response, list, "考试记录数据");
    }

    /**
     * 获取考试记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elPaperService.selectElPaperById(id));
    }



    @GetMapping(value = "/qu-detail")
    public AjaxResult quDetail(@RequestParam("paperId")String paperId,
                               @RequestParam("quId")String quId) {
        PaperQuDetailDTO respDTO = elPaperService.findQuDetail(paperId,quId);
        return success(respDTO);
    }






    /**
     * 新增考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:add')")
    @Log(title = "考试记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElPaper elPaper)
    {
        return success((Object)elPaperService.insertElPaper(elPaper));
    }

    /**
     * 修改考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:edit')")
    @Log(title = "考试记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElPaper elPaper)
    {
        return toAjax(elPaperService.updateElPaper(elPaper));
    }

    /**
     * 删除考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:paper:remove')")
    @Log(title = "考试记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elPaperService.deleteElPaperByIds(ids));
    }
}
