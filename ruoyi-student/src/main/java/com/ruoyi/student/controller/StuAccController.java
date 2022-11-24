package com.ruoyi.student.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StuAccVo;
import com.ruoyi.student.service.IStuAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学生统计Controller
 *
 * @author wangzq
 * @date 2022-11-22
 */
@RestController
@RequestMapping("/student/account")
public class StuAccController extends BaseController {

    @Autowired
    private IStuAccService stuAccService;

    /**
     * 查询学生信息列表
     */
//    @PreAuthorize("@ss.hasPermi('student:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuAccVo stuAccVo)
    {
        startPage();
        List<StuAccVo> list = stuAccService.selectStuAccList(stuAccVo);
        return getDataTable(list);
    }
}
