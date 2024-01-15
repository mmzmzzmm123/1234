package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "氛围号池")
@RestController
@RequestMapping("/robot")
public class RobotController {

    @ApiOperation("查询号列表")
    @PostMapping("/selectRobotPageList")
    public R<Page<SelectRobotListVO>> selectRobotPageList(@RequestBody SelectRobotListDTO dto){
        return R.ok();
    }

    @ApiOperation("同步账号")
    @PostMapping("/syncRobot")
    public R<Void> syncRobot(){
        return R.ok();
    }

    @ApiOperation("设置头像")
    @PostMapping("/setHeadImg")
    public R<Void> setHeadImg(@RequestBody SetHeadImgDTO dto){
        return R.ok();
    }

    @ApiOperation("设置名字姓氏简介")
    @PostMapping("/setName")
    public R<Void> setName(@RequestParam("file") MultipartFile file,
                           @ApiParam(value = "名字")@RequestParam(value = "firstName")String firstName,
                           @ApiParam(value = "姓氏")@RequestParam(value = "lastName")String lastName,
                           @ApiParam(value = "个人简介")@RequestParam(value = "briefIntro")String briefIntro){
        return R.ok();
    }

    @ApiOperation("设置用户名")
    @PostMapping("/setUserName")
    public R<Void> setUserName(@RequestBody SetUserNameDTO dto){
        return R.ok();
    }

    @ApiOperation("设置用户名")
    @PostMapping("/setPrivatePhone")
    public R<Void> setPrivatePhone(@RequestBody SetPrivatePhoneDTO dto){
        return R.ok();
    }

    @ApiOperation("修改禁用启用状态")
    @PostMapping("/updateEnableType")
    public R<Void> updateEnableType(@RequestBody UpdateEnableTypeDTO dto){
        return R.ok();
    }

    @ApiOperation("一键清除封号数据")
    @PostMapping("/clearSealData")
    public R<Void> clearSealData(){
        return R.ok();
    }

    @ApiOperation("移除号池")
    @PostMapping("/removeRobot")
    private R<Void> removeRobot(@RequestBody RemoveRobotDTO dto){
        return R.ok();
    }

    @ApiOperation("一键释放占用水军")
    @PostMapping("/releaseOccupyRobot")
    public R<Void> releaseOccupyRobot(){
        return R.ok();
    }

}
