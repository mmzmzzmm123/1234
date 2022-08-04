package com.ruoyi.escore.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.escore.domain.EscoreVo;
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
import com.ruoyi.escore.domain.Escore;
import com.ruoyi.escore.service.IEscoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分数Controller
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
@RestController
@RequestMapping("/escore/escore")
public class EscoreController extends BaseController
{
    @Autowired
    private IEscoreService escoreService;

    /**
     * 查询分数列表
     */
//    @PreAuthorize("@ss.hasPermi('escore:escore:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(Escore escore)
//    {
//        startPage();
//        List<Escore> list = escoreService.selectEscoreList(escore);
//        return getDataTable(list);
//    }
//

    @PreAuthorize("@ss.hasPermi('escore:escore:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscoreVo escoreVo)
    {
        startPage();
        List<EscoreVo> list = escoreService.selectEscoreVoList(escoreVo);
        return getDataTable(list);
    }

    /**
     * 导出分数列表
     */
    @PreAuthorize("@ss.hasPermi('escore:escore:export')")
    @Log(title = "分数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscoreVo escore)
    {
        List<EscoreVo> list = escoreService.selectEscoreVoList(escore);
        ExcelUtil<EscoreVo> util = new ExcelUtil<EscoreVo>(EscoreVo.class);
        util.exportExcel(response, list, "分数数据");
    }

    /**
     * 获取分数详细信息
     */
    @PreAuthorize("@ss.hasPermi('escore:escore:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(escoreService.selectEscoreById(id));
    }

    /**
     * 新增分数
     */
    @PreAuthorize("@ss.hasPermi('escore:escore:add')")
    @Log(title = "分数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscoreVo escore)
    {
        return toAjax(escoreService.insertEscore(escore));
    }

    /**
     * 修改分数
     */
    @PreAuthorize("@ss.hasPermi('escore:escore:edit')")
    @Log(title = "分数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Escore escore)
    {
        return toAjax(escoreService.updateEscore(escore));
    }

    /**
     * 删除分数
     */
    @PreAuthorize("@ss.hasPermi('escore:escore:remove')")
    @Log(title = "分数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(escoreService.deleteEscoreByIds(ids));
    }
}
