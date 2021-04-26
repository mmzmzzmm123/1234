package com.ruoyi.web.controller.xypsgl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.web.controller.zcrpsgl.TsbzZcrjdcjController;
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
import com.ruoyi.xypsgl.domain.TsbzXybmsq;
import com.ruoyi.xypsgl.service.ITsbzXybmsqService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学员报名申请Controller
 * 
 * @author tsbz
 * @date 2021-04-23
 */
@RestController
@RequestMapping("/xypsgl/xybmsq")
public class TsbzXybmsqController extends BaseController
{
    @Autowired
    private ITsbzXybmsqService tsbzXybmsqService;
    @Autowired
    private ITsbzZcrjdcjService tsbzZcrjdcjService;

    /**
     * 查询学员报名申请列表
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzXybmsq tsbzXybmsq)
    {
        startPage();
        List<TsbzXybmsq> list = tsbzXybmsqService.selectTsbzXybmsqList(tsbzXybmsq);
        return getDataTable(list);
    }

    /**
     * 导出学员报名申请列表
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:export')")
    @Log(title = "学员报名申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzXybmsq tsbzXybmsq)
    {
        List<TsbzXybmsq> list = tsbzXybmsqService.selectTsbzXybmsqList(tsbzXybmsq);
        ExcelUtil<TsbzXybmsq> util = new ExcelUtil<TsbzXybmsq>(TsbzXybmsq.class);
        return util.exportExcel(list, "xybmsq");
    }

    /**
     * 获取学员报名申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzXybmsqService.selectTsbzXybmsqById(id));
    }

    /**
     * 新增学员报名申请
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:add')")
    @Log(title = "学员报名申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzXybmsq tsbzXybmsq)
    {
        AjaxResult ajax = AjaxResult.success();

        // 获取基地招生名额
        int countZsme = tsbzZcrjdcjService.selectTsbzZcrjdcjById(tsbzXybmsq.getJdid()).getZsme();
        TsbzXybmsq tsbzXybmsq1 = new TsbzXybmsq();
        tsbzXybmsq1.setJdid(tsbzXybmsq.getJdid());
        tsbzXybmsq1.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        // 先判断是否此用户已经报名此基地
        if (tsbzXybmsqService.selectTsbzXybmsqList(tsbzXybmsq1).size() > 0) {
            return AjaxResult.error("您已报名次基地,无法再次报名");
        } else {
            // 获取已经报名的老师
            int countYbm = tsbzXybmsqService.selectCountYibaoming(tsbzXybmsq1).getCountYbm();
            // 判断是否有名额
            if (countYbm < countZsme) {
                tsbzXybmsq.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
                tsbzXybmsq.setCreateTime(new Date());
                tsbzXybmsqService.insertTsbzXybmsq(tsbzXybmsq);
                return ajax;
            } else {
                return AjaxResult.error("当前基地名额已满,无法继续报名");
            }
        }
    }

    /**
     * 修改学员报名申请
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:edit')")
    @Log(title = "学员报名申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzXybmsq tsbzXybmsq)
    {
        return toAjax(tsbzXybmsqService.updateTsbzXybmsq(tsbzXybmsq));
    }

    /**
     * 删除学员报名申请
     */
    @PreAuthorize("@ss.hasPermi('xypsgl:xybmsq:remove')")
    @Log(title = "学员报名申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzXybmsqService.deleteTsbzXybmsqByIds(ids));
    }
}
