package GYProject.mybatisTest.controller;

import GYProject.mybatisTest.entity.testVO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class testController {
    @Resource
    public GYProject.mybatisTest.mapper.testvoInterface testvoInterface;
    @Test
    public void test1(){
        List<testVO> testVOS = testvoInterface.selectList(null);
        testVOS.forEach(System.out::println);
    }

}
