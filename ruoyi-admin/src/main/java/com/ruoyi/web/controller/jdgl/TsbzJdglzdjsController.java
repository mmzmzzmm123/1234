package com.ruoyi.web.controller.jdgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.zcrpsgl.domain.TsbzZcrjdcj;
import com.ruoyi.zcrpsgl.service.ITsbzZcrjdcjService;
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
import com.ruoyi.jdgl.domain.TsbzJdglzdjs;
import com.ruoyi.jdgl.service.ITsbzJdglzdjsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理制度建设Controller
 * 
 * @author tsbz
 * @date 2021-05-11
 */
@RestController
@RequestMapping("/jdgl/jdglzdjs")
public class TsbzJdglzdjsController extends BaseController
{
    @Autowired
    private ITsbzJdglzdjsService tsbzJdglzdjsService;
    @Autowired
    private ITsbzZcrjdcjService tsbzZcrjdcjService;

    /**
     * 查询基地管理制度建设列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdglzdjs tsbzJdglzdjs)
    {
        tsbzJdglzdjs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJdglzdjs> list = tsbzJdglzdjsService.selectTsbzJdglzdjsList(tsbzJdglzdjs);
        return getDataTable(list);
    }

    /**
     * 导出基地管理制度建设列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:export')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdglzdjs tsbzJdglzdjs)
    {
        List<TsbzJdglzdjs> list = tsbzJdglzdjsService.selectTsbzJdglzdjsList(tsbzJdglzdjs);
        ExcelUtil<TsbzJdglzdjs> util = new ExcelUtil<TsbzJdglzdjs>(TsbzJdglzdjs.class);
        return util.exportExcel(list, "jdglzdjs");
    }

    /**
     * 获取基地管理制度建设详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdglzdjsService.selectTsbzJdglzdjsById(id));
    }

    /**
     * 新增基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:add')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdglzdjs tsbzJdglzdjs)
    {
        AjaxResult ajax = AjaxResult.success();

        TsbzZcrjdcj tsbzZcrjdcj = new TsbzZcrjdcj();
        tsbzZcrjdcj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        List<TsbzZcrjdcj> jdcjList = tsbzZcrjdcjService.selectTsbzZcrjdcjList(tsbzZcrjdcj);
        if (jdcjList.size()>0) {
            tsbzJdglzdjs.setJdid(jdcjList.get(0).getId());
            tsbzJdglzdjs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
            tsbzJdglzdjs.setSctime(new Date());
            tsbzJdglzdjs.setCreateTime(new Date());
            tsbzJdglzdjsService.insertTsbzJdglzdjs(tsbzJdglzdjs);
            return ajax;
        }else {
            return AjaxResult.error("当前用户没有基地,无法新增制度建设");
        }


    }

    /**
     * 修改基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:edit')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdglzdjs tsbzJdglzdjs)
    {
        return toAjax(tsbzJdglzdjsService.updateTsbzJdglzdjs(tsbzJdglzdjs));
    }

    /**
     * 删除基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:remove')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdglzdjsService.deleteTsbzJdglzdjsByIds(ids));
    }
}
