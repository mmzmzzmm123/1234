package com.ruoyi.project.data.basis.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.data.basis.domain.LianJiaCommunityDict;
import com.ruoyi.project.data.basis.domain.UvClosingCase;
import com.ruoyi.project.data.basis.domain.UvTradingCase;
import com.ruoyi.project.data.basis.service.ILianJiaCommunityDictService;
import com.ruoyi.project.data.basis.service.IUvClosingCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 联城案例Controller
 *
 * @author lihe
 */
@RestController
@RequestMapping("/basic/cases")
public class UvCaseController extends BaseController {

    @Autowired
    private IUvClosingCaseService uvClosingCaseService;


    @Autowired
    private ILianJiaCommunityDictService lianJiaCommunityDictService;

    /**
     * 列表页面
     *
     * @param closingCase
     * @return
     */
    @PreAuthorize("@ss.hasPermi('cases:uvClosingCase:list')")
    @GetMapping("/closing/list")
    public TableDataInfo closingCaseList(UvClosingCase closingCase) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        closingCase.setOffset(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        closingCase.setLimit(pageSize);

        int total = uvClosingCaseService.pageCount(closingCase);
        List<UvClosingCase> list = uvClosingCaseService.pageList(closingCase);
        return getDataTable(list, total);
    }

    /**
     * es的模糊查找
     *
     * @param text
     * @return
     */
    @PreAuthorize("@ss.hasPermi('cases:uvClosingCase:list')")
    @GetMapping("/closing/query")
    public TableDataInfo closingCaseInESList(@RequestParam("text") String text) {
        List<UvTradingCase> list = uvClosingCaseService.findByText(text);
        return getDataTable(list, list.size());
    }
}
