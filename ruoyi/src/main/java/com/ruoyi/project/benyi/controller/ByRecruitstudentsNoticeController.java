package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.ByRecruitstudentsNotice;
import com.ruoyi.project.benyi.service.IByRecruitstudentsNoticeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 入园通知书Controller
 *
 * @author tsbz
 * @date 2020-07-28
 */
@RestController
@RequestMapping("/benyi/recruitstudentsnotice")
public class ByRecruitstudentsNoticeController extends BaseController {
    @Autowired
    private IByRecruitstudentsNoticeService byRecruitstudentsNoticeService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询入园通知书列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByRecruitstudentsNotice byRecruitstudentsNotice) {
        byRecruitstudentsNotice.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        startPage();
        List<ByRecruitstudentsNotice> list = byRecruitstudentsNoticeService.selectByRecruitstudentsNoticeList(byRecruitstudentsNotice);
        return getDataTable(list);
    }

    /**
     * 导出入园通知书列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:export')")
    @Log(title = "入园通知书", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByRecruitstudentsNotice byRecruitstudentsNotice) {
        List<ByRecruitstudentsNotice> list = byRecruitstudentsNoticeService.selectByRecruitstudentsNoticeList(byRecruitstudentsNotice);
        ExcelUtil<ByRecruitstudentsNotice> util = new ExcelUtil<ByRecruitstudentsNotice>(ByRecruitstudentsNotice.class);
        return util.exportExcel(list, "notice");
    }

    /**
     * 获取入园通知书详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byRecruitstudentsNoticeService.selectByRecruitstudentsNoticeById(id));
    }

    /**
     * 新增入园通知书
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:add')")
    @Log(title = "入园通知书", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByRecruitstudentsNotice byRecruitstudentsNotice) {
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool()) {
            byRecruitstudentsNotice.setType("2");
            byRecruitstudentsNotice.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        } else {
            byRecruitstudentsNotice.setType("1");
        }
        return toAjax(byRecruitstudentsNoticeService.insertByRecruitstudentsNotice(byRecruitstudentsNotice));
    }

    /**
     * 修改入园通知书
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:edit')")
    @Log(title = "入园通知书", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByRecruitstudentsNotice byRecruitstudentsNotice) {
        return toAjax(byRecruitstudentsNoticeService.updateByRecruitstudentsNotice(byRecruitstudentsNotice));
    }

    /**
     * 复制入园通知书
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:edit')")
    @Log(title = "入园通知书", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@PathVariable Long id) {
        System.out.println("id===" + id);
        ByRecruitstudentsNotice byRecruitstudentsNotice = byRecruitstudentsNoticeService.selectByRecruitstudentsNoticeById(id);
        ByRecruitstudentsNotice byRecruitstudentsNoticeCopy = new ByRecruitstudentsNotice();
        byRecruitstudentsNoticeCopy.setType("2");
        byRecruitstudentsNoticeCopy.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        byRecruitstudentsNoticeCopy.setContent(byRecruitstudentsNotice.getContent());
        byRecruitstudentsNoticeCopy.setTitle(byRecruitstudentsNotice.getTitle());

        return toAjax(byRecruitstudentsNoticeService.insertByRecruitstudentsNotice(byRecruitstudentsNoticeCopy));
    }

    /**
     * 删除入园通知书
     */
    @PreAuthorize("@ss.hasPermi('benyi:recruitstudentsnotice:remove')")
    @Log(title = "入园通知书", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //判断id是否为系统定义
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            ByRecruitstudentsNotice byRecruitstudentsNotice = byRecruitstudentsNoticeService.selectByRecruitstudentsNoticeById(id);
            if (byRecruitstudentsNotice.getType() == "1") {
                return AjaxResult.error("包含系统通知书，无法删除");
            }
        }

        return toAjax(byRecruitstudentsNoticeService.deleteByRecruitstudentsNoticeByIds(ids));
    }
}
