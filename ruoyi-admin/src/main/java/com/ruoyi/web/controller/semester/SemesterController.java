package com.ruoyi.web.controller.semester;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stu.domain.Semester;
import com.ruoyi.stu.service.ISemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/semester")
public class SemesterController extends BaseController {

    @Autowired
    private ISemesterService semesterService;

    @Anonymous
    @GetMapping("list")
    public AjaxResult list(Semester semester)
    {
        List<Semester> list = semesterService.selectSemester(semester);
        return success(list);
    }


}
