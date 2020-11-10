package com.ruoyi.web.controller.jxjs;

import java.util.List;

import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.jxjs.domain.TsbzJxzxpxpj;
import com.ruoyi.jxjs.service.ITsbzJxzxpxpjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习之星教师培训评价Controller
 * 
 * @author ruoyi
 * @date 2020-11-09
 */
@RestController
@RequestMapping("/jxjs/jxzxpxpj")
public class TsbzJxzxpxpjController extends BaseController
{
    @Autowired
    private ITsbzJxzxpxpjService tsbzJxzxpxpjService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询见习之星教师培训评价列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxpxpj tsbzJxzxpxpj)
    {
        //首先判断是否为学校用户
        String jdxId = schoolCommonController.deptIdToJdxId();
        if (!schoolCommonController.isStringEmpty(jdxId)) {
            tsbzJxzxpxpj.setJdxid(jdxId);
            System.out.println(jdxId + "11111111111基地校Id");
        }
        startPage();
        List<TsbzJxzxpxpj> list = tsbzJxzxpxpjService.selectTsbzJxzxpxpjList(tsbzJxzxpxpj);
        return getDataTable(list);
    }

    /**
     * 导出见习之星教师培训评价列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:export')")
    @Log(title = "见习之星教师培训评价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxpxpj tsbzJxzxpxpj)
    {
        List<TsbzJxzxpxpj> list = tsbzJxzxpxpjService.selectTsbzJxzxpxpjList(tsbzJxzxpxpj);
        ExcelUtil<TsbzJxzxpxpj> util = new ExcelUtil<TsbzJxzxpxpj>(TsbzJxzxpxpj.class);
        return util.exportExcel(list, "jxzxpxpj");
    }

    /**
     * 获取见习之星教师培训评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJxzxpxpjService.selectTsbzJxzxpxpjById(id));
    }

    /**
     * 新增见习之星教师培训评价
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:add')")
    @Log(title = "见习之星教师培训评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxpxpj tsbzJxzxpxpj)
    {
        return toAjax(tsbzJxzxpxpjService.insertTsbzJxzxpxpj(tsbzJxzxpxpj));
    }

    /**
     * 修改见习之星教师培训评价
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:edit')")
    @Log(title = "见习之星教师培训评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxpxpj tsbzJxzxpxpj)
    {
        return toAjax(tsbzJxzxpxpjService.updateTsbzJxzxpxpj(tsbzJxzxpxpj));
    }

    /**
     * 删除见习之星教师培训评价
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxpj:remove')")
    @Log(title = "见习之星教师培训评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJxzxpxpjService.deleteTsbzJxzxpxpjByIds(ids));
    }
}
