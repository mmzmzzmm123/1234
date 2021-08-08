package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.MongoRepository.TestMongdbRepository;
import com.ruoyi.system.mongDomain.TestMongdb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "首页模块")
@RestController
@RequestMapping("/app/test/")
public class IndexController {
    @Autowired
    TestMongdbRepository testMongdbRepository;

    @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi")
    public AjaxResult sayHi(@RequestParam(value = "name")String name){
        return  AjaxResult.success("您好："+name);
    }


    @ApiOperation(value = "mongdbDemo-查询")
    @GetMapping("/testMongdbfindAllByName")
    public AjaxResult testMongdbfindAllByName(@RequestParam(value = "name")String name){
        //testMongdbRepository.findAllByName(name);
        return  AjaxResult.success(testMongdbRepository.findAllByName(name));
    }

    @ApiOperation(value = "mongdbDemo-新增")
    @PostMapping ("/testMongdbSave")
    public AjaxResult testMongdbSave(@RequestBody JSONObject jsonObject){
        TestMongdb testMongdb = new TestMongdb();
        testMongdb.setName(jsonObject.getString("name"));
        testMongdb.setAge(jsonObject.getString("age"));
        testMongdb.setTestMongdbId(UUID.randomUUID().toString());
        testMongdbRepository.save(testMongdb);
        return  AjaxResult.success();
    }

    @ApiOperation(value = "mongdbDemo-更新")
    @PostMapping ("/testMongdbUpdate")
    public AjaxResult testMongdbUpdate(@RequestBody JSONObject jsonObject){
        TestMongdb testMongdb = new TestMongdb();
        testMongdb.setTestMongdbId(jsonObject.getString("id"));
        testMongdb.setName(jsonObject.getString("name"));

        Example<TestMongdb> example = Example.of(testMongdb);
        TestMongdb update =  testMongdbRepository.findOne(example).get();
        if (update != null) {
            update.setAge(jsonObject.getString("age"));
            testMongdbRepository.save(update);
            return  AjaxResult.success();
        }
        return  AjaxResult.error("修改失败，数据不存在");
    }
}
