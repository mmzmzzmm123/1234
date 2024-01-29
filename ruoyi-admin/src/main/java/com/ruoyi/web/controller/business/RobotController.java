package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import com.ruoyi.system.domain.vo.robot.SetNameResourceVO;
import com.ruoyi.system.service.IRobotService;
import com.ruoyi.system.service.RobotStatisticsService;
import com.ruoyi.system.service.TelegramDetChatroomAdminDistrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Api(tags = "氛围号池")
@RestController
@RequestMapping("/robot")
public class RobotController {
    @Autowired
    private IRobotService robotService;

    @Autowired
    private TelegramDetChatroomAdminDistrService telegramDetChatroomAdminDistrService;

    @Autowired
    private RobotStatisticsService robotStatisticsService;
    @ApiOperation("查询号列表")
    @PostMapping("/selectRobotPageList")
    public R<Page<SelectRobotListVO>> selectRobotPageList(@RequestBody SelectRobotListDTO dto){
        return robotService.selectRobotPageList(dto);
    }

    @ApiOperation(value = "导出号列表")
    @PostMapping("/export")
    public R<Page<SelectRobotListVO>> export(@RequestBody SelectRobotListDTO dto, HttpServletResponse response){
        ExcelUtil<SelectRobotListVO> excelUtil = new ExcelUtil<>(SelectRobotListVO.class);
        List<SelectRobotListVO> records = new ArrayList<>();
        Page<SelectRobotListVO> page = null;
        int current = 1;
        do {
            dto.setPage(current);
            dto.setLimit(10000);
            R<Page<SelectRobotListVO>> result = robotService.selectRobotPageList(dto);
            page = result.getData();
            records.addAll(page.getRecords());
            ++current;
        } while (page.hasNext());
        excelUtil.exportExcel(response,records,"号列表");
        return R.ok();
    }

    @ApiOperation("同步账号")
    @PostMapping("/syncRobot")
    public R<Void> syncRobot(){
        return robotService.syncRobot();
    }

    @ApiOperation("设置头像")
    @PostMapping("/setHeadImg")
    public R<Void> setHeadImg(@RequestBody SetHeadImgDTO dto){
        return robotService.setHeadImg(dto);
    }

    @ApiOperation("设置名字姓氏简介")
    @PostMapping("/setName")
    public R<Void> setName(
            @ApiParam(value = "文件") @RequestParam(value = "file",required = false) MultipartFile file,
            @ApiParam(value = "名字") @RequestParam(value = "firstName",required = false) String firstName,
            @ApiParam(value = "姓氏")@RequestParam(value = "lastName",required = false) String lastName,
            @ApiParam(value = "个人简介")@RequestParam(value = "briefIntro",required = false) String briefIntro,
            @ApiParam(value = "机器人编号")@RequestParam(value = "robotSerialNos",required = false) List<String> robotSerialNos){
        SetNameInfoDTO setNameInfoDTO = new SetNameInfoDTO();
        setNameInfoDTO.setRobotSerialNos(robotSerialNos);
        if(StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(briefIntro)){
            List<SetNameResourceVO> setNameResourceVOS = robotService.analysisExcelInfo(file);
            setNameInfoDTO.setInfos(setNameResourceVOS);
        }else{
            List<SetNameResourceVO> list = new ArrayList<>();
            SetNameResourceVO setNameResourceVO = new SetNameResourceVO();
            setNameResourceVO.setLastName(lastName);
            setNameResourceVO.setFirstName(firstName);
            setNameResourceVO.setBriefIntro(briefIntro);
            list.add(setNameResourceVO);
            setNameInfoDTO.setInfos(list);
        }
        return robotService.setName(setNameInfoDTO);
    }

    @ApiOperation("设置用户名")
    @PostMapping("/setUserName")
    public R<Void> setUserName(@RequestBody SetUserNameDTO dto){
        return robotService.setUserName(dto);
    }

    @ApiOperation("设置手机号隐私")
    @PostMapping("/setPrivatePhone")
    public R<Void> setPrivatePhone(@RequestBody SetPrivatePhoneDTO dto){
        return robotService.setPrivatePhone(dto);
    }

    @ApiOperation("修改禁用启用状态")
    @PostMapping("/updateEnableType")
    public R<Void> updateEnableType(@RequestBody UpdateEnableTypeDTO dto){
        return robotService.updateEnableType(dto);
    }

    @ApiOperation("一键清除封号数据")
    @PostMapping("/clearSealData")
    public R<Void> clearSealData(@RequestBody ClearSealDataDTO dto){
        return robotService.clearSealData(dto);
    }

    @ApiOperation("移除号池")
    @PostMapping("/removeRobot")
    private R<Void> removeRobot(@RequestBody RemoveRobotDTO dto){
        return robotService.removeRobot(dto);
    }

    @ApiOperation("一键释放占用水军")
    @PostMapping("/releaseOccupyRobot")
    public R<Void> releaseOccupyRobot(@RequestBody ReleaseOccupyRobotDTO dto){
        return robotService.releaseOccupyRobot(dto);
    }

    @ApiOperation("定时同步号列表")
    @PostMapping("/syncRobotList")
    public void syncRobotList(){
        robotService.syncRobot();
    }

    @ApiOperation("定时同步黑群主列表")
    @PostMapping("/syncChatroomAdminDistr")
    public void syncChatroomAdminDistr(){
        telegramDetChatroomAdminDistrService.syncChatroomAdminDistr();
    }

    @PostMapping("/getRobot")
    public R<List<GetRobotVO>> getRobot(@RequestBody GetRobotDTO dto){
        R<List<GetRobotVO>> robot = robotStatisticsService.getRobot(dto);
        return robot;
    }

    @ApiOperation("定时清除号每日累计数")
    @PostMapping("/clearRobotOneDayNum")
    public void clearRobotOneDayNum(){
        robotStatisticsService.clearRobotOneDayNum();
    }

    @ApiOperation("定时任务-获取所有会话-回调统计机器人带群数")
    @PostMapping("/getRobotFreGroupDataByThirdKpTg")
    public void getRobotFreGroupDataByThirdKpTg() {
        robotService.getRobotFreGroupDataByThirdKpTg();
    }
}
