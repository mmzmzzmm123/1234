package com.ruoyi.consult.controller;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.notice.constant.NoticeConstant;
import com.ruoyi.notice.domain.PsyCourNotice;
import com.ruoyi.notice.service.IPsyCourNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 咨询师通知表 Controller
 *
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/consult/notice")
@Api(value = "CourseNoticeController" ,tags = {" 咨询师通知api"})
public class CourseNoticeController extends BaseController implements NoticeConstant {

    @Autowired
    private IPsyCourNoticeService psyCourNoticeService;

    /**
     * 通知 list
     */
    @PostMapping("/list")
    @ApiOperation(value = "咨询师通知分页列表")
    @RateLimiter
    public TableDataInfo list(@PathVariable("source") String source, @RequestBody PsyCourNotice psyCourNotice)
    {
        startPage();
        psyCourNotice.setNoticeType(NOTICE_MSG);
        psyCourNotice.setSourceType(SOURCE_PSY);
        if(StringUtils.isNoneBlank(psyCourNotice.getStatus())){
            psyCourNotice.setStatus(STATUS_NOT_READ);
        }
        List<PsyCourNotice> list = psyCourNoticeService.selectPsyCourNoticeList(psyCourNotice);
        return getDataTable(list);
    }


    /**
     * 通知详情
     */
    @ApiOperation(value = "咨询师通知详情")
    @GetMapping(value = "/view/{id}")
    public AjaxResult view(@PathVariable("id") Long id)
    {
        PsyCourNotice psyCourNotice = new PsyCourNotice();
        psyCourNotice.setNoticeId(id);
        psyCourNotice.setStatus(STATUS_READ);
        psyCourNoticeService.updatePsyCourNotice(psyCourNotice);
        return AjaxResult.success(psyCourNoticeService.selectPsyCourNoticeByNoticeId(id));
    }

}
