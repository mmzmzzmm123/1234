package com.ruoyi.contest;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.contest.mapper.DeptMapper;
import com.ruoyi.contest.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lsyonlygoddes
 * @time 2023/02/09 13:15
 */
@SpringBootTest(classes = RuoYiApplication.class)
public class TestMapper {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private DeptService deptService;

    @Test
    void test1() {
//        deptMapper.selectList(null);
//        List<Dept> list = deptService.list();
//        list.forEach(System.out::println);
//        System.out.println("========");
//        Dept dept = new Dept();
//        dept.setDeptName("部门1");
//        deptService.save(dept);
//        deptService.removeById(1);

//        Page<Dept> page = new Page<>(1,2);
//        deptMapper.selectPage(page,null);

        deptMapper.selByDeptId(1L);



    }
}
