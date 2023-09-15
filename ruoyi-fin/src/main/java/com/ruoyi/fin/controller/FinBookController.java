package com.ruoyi.fin.controller;

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
import com.ruoyi.fin.domain.FinBook;
import com.ruoyi.fin.service.IFinBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 记账账本Controller
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
@RestController
@RequestMapping("/fin/book")
public class FinBookController extends BaseController
{
    @Autowired
    private IFinBookService finBookService;

    /**
     * 查询记账账本列表
     */
    @PreAuthorize("@ss.hasPermi('fin:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinBook finBook)
    {
        startPage();
        List<FinBook> list = finBookService.selectFinBookList(finBook);
        return getDataTable(list);
    }

    /**
     * 导出记账账本列表
     */
    @PreAuthorize("@ss.hasPermi('fin:book:export')")
    @Log(title = "记账账本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinBook finBook)
    {
        List<FinBook> list = finBookService.selectFinBookList(finBook);
        ExcelUtil<FinBook> util = new ExcelUtil<FinBook>(FinBook.class);
        util.exportExcel(response, list, "记账账本数据");
    }

    /**
     * 获取记账账本详细信息
     */
    @PreAuthorize("@ss.hasPermi('fin:book:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") Long bookId)
    {
        return success(finBookService.selectFinBookByBookId(bookId));
    }

    /**
     * 新增记账账本
     */
    @PreAuthorize("@ss.hasPermi('fin:book:add')")
    @Log(title = "记账账本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinBook finBook)
    {
        return toAjax(finBookService.insertFinBook(finBook));
    }

    /**
     * 修改记账账本
     */
    @PreAuthorize("@ss.hasPermi('fin:book:edit')")
    @Log(title = "记账账本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinBook finBook)
    {
        return toAjax(finBookService.updateFinBook(finBook));
    }

    /**
     * 删除记账账本
     */
    @PreAuthorize("@ss.hasPermi('fin:book:remove')")
    @Log(title = "记账账本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable Long[] bookIds)
    {
        return toAjax(finBookService.deleteFinBookByBookIds(bookIds));
    }
}
