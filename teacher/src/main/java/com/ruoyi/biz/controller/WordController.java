package com.ruoyi.biz.controller;

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
import com.ruoyi.biz.domain.Word;
import com.ruoyi.biz.service.IWordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作业安排Controller
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
@RestController
@RequestMapping("/work/word")
public class WordController extends BaseController
{
    @Autowired
    private IWordService wordService;

    /**
     * 查询作业安排列表
     */
    @PreAuthorize("@ss.hasPermi('work:word:list')")
    @GetMapping("/list")
    public TableDataInfo list(Word word)
    {
        startPage();
        List<Word> list = wordService.selectWordList(word);
        return getDataTable(list);
    }

    /**
     * 导出作业安排列表
     */
    @PreAuthorize("@ss.hasPermi('work:word:export')")
    @Log(title = "作业安排", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Word word)
    {
        List<Word> list = wordService.selectWordList(word);
        ExcelUtil<Word> util = new ExcelUtil<Word>(Word.class);
        util.exportExcel(response, list, "作业安排数据");
    }

    /**
     * 获取作业安排详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:word:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wordService.selectWordById(id));
    }

    /**
     * 新增作业安排
     */
    @PreAuthorize("@ss.hasPermi('work:word:add')")
    @Log(title = "作业安排", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Word word)
    {
        return toAjax(wordService.insertWord(word));
    }

    /**
     * 修改作业安排
     */
    @PreAuthorize("@ss.hasPermi('work:word:edit')")
    @Log(title = "作业安排", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Word word)
    {
        return toAjax(wordService.updateWord(word));
    }

    /**
     * 删除作业安排
     */
    @PreAuthorize("@ss.hasPermi('work:word:remove')")
    @Log(title = "作业安排", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wordService.deleteWordByIds(ids));
    }
}
