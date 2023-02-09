package com.ruoyi.web.controller.contest;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.contest.entity.Dept;
import com.ruoyi.contest.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lsyonlygoddes
 * @time 2023/02/09 13:51
 */
@RestController
@RequestMapping("/contest/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("list")
    @Anonymous
    public List<Dept> list(){
        return deptService.list();
    }
}
