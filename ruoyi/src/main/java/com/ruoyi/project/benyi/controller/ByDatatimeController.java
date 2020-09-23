package com.ruoyi.project.benyi.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.benyi.domain.ByDatetime;
import com.ruoyi.project.benyi.service.IByDatetimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/benyi/datetime")
public class ByDatatimeController extends BaseController {
    @Autowired
    private IByDatetimeService byDatetimeService;

    /**
     * 查询园历管理(本一)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDatetime byDatetime) {
        startPage();
        List<ByDatetime> list = byDatetimeService.selectByDatetimeList(byDatetime);
        return getDataTable(list);
    }
}
