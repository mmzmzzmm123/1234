package com.ruoyi.project.benyi.controller;

import java.util.List;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByMath;
import com.ruoyi.project.benyi.service.IByMathService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 游戏数学Controller
 *
 * @author tsbz
 * @date 2020-07-20
 */
@RestController
@RequestMapping("/benyi/math")
public class ByMathController extends BaseController
{
    @Autowired
    private IByMathService byMathService;

/**
 * 查询游戏数学列表
 */
@PreAuthorize("@ss.hasPermi('benyi:math:list')")
@GetMapping("/list")
        public TableDataInfo list(ByMath byMath)
    {
        startPage();
        List<ByMath> list = byMathService.selectByMathList(byMath);
        return getDataTable(list);
    }
    
    /**
     * 导出游戏数学列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:math:export')")
    @Log(title = "游戏数学", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMath byMath)
    {
        List<ByMath> list = byMathService.selectByMathList(byMath);
        ExcelUtil<ByMath> util = new ExcelUtil<ByMath>(ByMath.class);
        return util.exportExcel(list, "math");
    }

    /**
     * 获取游戏数学详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:math:query')" + "||@ss.hasPermi('benyi:math:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byMathService.selectByMathById(id));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ByMath byMath) {
        List<ByMath> byMathDetails = byMathService.selectByMathListTree(byMath);
//        for(int i=0;i<byMathDetails.size();i++){
//            System.out.println("parentid="+byMathDetails.get(i).getParentId());
//        }
        return AjaxResult.success(byMathService.buildMathTreeSelect(byMathDetails));
    }

    /**
     * 新增游戏数学
     */
    @PreAuthorize("@ss.hasPermi('benyi:math:add')")
    @Log(title = "游戏数学", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMath byMath)
    {
        return toAjax(byMathService.insertByMath(byMath));
    }

    /**
     * 修改游戏数学
     */
    @PreAuthorize("@ss.hasPermi('benyi:math:edit')")
    @Log(title = "游戏数学", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMath byMath)
    {
        return toAjax(byMathService.updateByMath(byMath));
    }

    /**
     * 删除游戏数学
     */
    @PreAuthorize("@ss.hasPermi('benyi:math:remove')")
    @Log(title = "游戏数学", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byMathService.deleteByMathByIds(ids));
    }
}
