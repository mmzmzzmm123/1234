package com.ruoyi.web.controller.zcrpsgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.qtjs.domain.TsbzJsjbxx;
import com.ruoyi.qtjs.service.ITsbzJsjbxxService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.zcrpsgl.domain.TsbzZcrbmsq;
import com.ruoyi.zcrpsgl.service.ITsbzZcrbmsqService;
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
import com.ruoyi.zcrpsgl.domain.TsbzZcrjdcj;
import com.ruoyi.zcrpsgl.service.ITsbzZcrjdcjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主持人基地Controller
 * 
 * @author tsbz
 * @date 2021-04-23
 */
@RestController
@RequestMapping("/zcrpsgl/zcrjdcj")
public class TsbzZcrjdcjController extends BaseController
{
    @Autowired
    private ITsbzZcrjdcjService tsbzZcrjdcjService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ITsbzJsjbxxService tsbzJsjbxxService;
    @Autowired
    private ITsbzZcrbmsqService tsbzZcrbmsqService;

    /**
     * 查询主持人基地列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:list')" + "||@ss.hasPermi('xypsgl:xybmsq:list')" + "||@ss.hasPermi('xypsgl:xyxxsh:list')" + "||@ss.hasPermi('xypsgl:xyqjsh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzZcrjdcj tsbzZcrjdcj)
    {
        startPage();
        List<TsbzZcrjdcj> list = tsbzZcrjdcjService.selectTsbzZcrjdcjList(tsbzZcrjdcj);
        return getDataTable(list);
    }

    /**
     * 导出主持人基地列表
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:export')")
    @Log(title = "主持人基地", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzZcrjdcj tsbzZcrjdcj)
    {
        List<TsbzZcrjdcj> list = tsbzZcrjdcjService.selectTsbzZcrjdcjList(tsbzZcrjdcj);
        ExcelUtil<TsbzZcrjdcj> util = new ExcelUtil<TsbzZcrjdcj>(TsbzZcrjdcj.class);
        return util.exportExcel(list, "zcrjdcj");
    }

    /**
     * 获取主持人基地详细信息
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:query')" + "||@ss.hasPermi('xypsgl:xybmsq:add')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzZcrjdcjService.selectTsbzZcrjdcjById(id));
    }

    /**
     * 新增主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:add')")
    @Log(title = "主持人基地", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzZcrjdcj tsbzZcrjdcj)
    {
        AjaxResult ajax = AjaxResult.success();
        // 通过登录id获取档案编号  查询教师id
        String dabh = userService.selectUserById(SecurityUtils.getLoginUser().getUser().getUserId()).getUserName();
        TsbzJsjbxx tsbzJsjbxx = new TsbzJsjbxx();
        tsbzJsjbxx.setDabh(dabh);
        String jsid = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx).get(0).getJsid();
        // 传入教师id和确认状态 判断此主持人是否可创建基地
        TsbzZcrbmsq tsbzZcrbmsq = new TsbzZcrbmsq();
        tsbzZcrbmsq.setJsid(Long.valueOf(jsid));
        tsbzZcrbmsq.setJgqrStatus("1");
        List list1 = tsbzZcrbmsqService.selectTsbzZcrbmsqList(tsbzZcrbmsq);
        if (list1.size() > 0) {
            tsbzZcrjdcj.setZcrid(Long.valueOf(jsid));
            tsbzZcrjdcj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
            tsbzZcrjdcj.setCreateTime(new Date());
            tsbzZcrjdcjService.insertTsbzZcrjdcj(tsbzZcrjdcj);
            return ajax;
        } else {
            return AjaxResult.error("没有此主持人或审核未通过,无法创建基地");
        }
    }

    /**
     * 修改主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:edit')")
    @Log(title = "主持人基地", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzZcrjdcj tsbzZcrjdcj)
    {
        return toAjax(tsbzZcrjdcjService.updateTsbzZcrjdcj(tsbzZcrjdcj));
    }

    /**
     * 删除主持人基地
     */
    @PreAuthorize("@ss.hasPermi('zcrpsgl:zcrjdcj:remove')")
    @Log(title = "主持人基地", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzZcrjdcjService.deleteTsbzZcrjdcjByIds(ids));
    }
}
