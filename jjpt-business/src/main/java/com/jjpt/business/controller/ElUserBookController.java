package com.jjpt.business.controller;

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
import com.jjpt.business.domain.ElUserBook;
import com.jjpt.business.service.IElUserBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 错题本Controller
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
@RestController
@RequestMapping("/exammanage/userbook")
public class ElUserBookController extends BaseController
{
    @Autowired
    private IElUserBookService elUserBookService;

    /**
     * 查询错题本列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElUserBook elUserBook)
    {
        startPage();
        List<ElUserBook> list = elUserBookService.selectElUserBookList(elUserBook);
        return getDataTable(list);
    }

    /**
     * 导出错题本列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:export')")
    @Log(title = "错题本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ElUserBook elUserBook)
    {
        List<ElUserBook> list = elUserBookService.selectElUserBookList(elUserBook);
        ExcelUtil<ElUserBook> util = new ExcelUtil<ElUserBook>(ElUserBook.class);
        util.exportExcel(response, list, "错题本数据");
    }

    /**
     * 获取错题本详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elUserBookService.selectElUserBookById(id));
    }

    /**
     * 新增错题本
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:add')")
    @Log(title = "错题本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElUserBook elUserBook)
    {
        return toAjax(elUserBookService.insertElUserBook(elUserBook));
    }

    /**
     * 修改错题本
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:edit')")
    @Log(title = "错题本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElUserBook elUserBook)
    {
        return toAjax(elUserBookService.updateElUserBook(elUserBook));
    }

    /**
     * 删除错题本
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userbook:remove')")
    @Log(title = "错题本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elUserBookService.deleteElUserBookByIds(ids));
    }
}
