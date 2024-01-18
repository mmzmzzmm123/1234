package com.ruoyi.system.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import com.ruoyi.system.domain.vo.robot.SetNameResourceVO;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.mapper.RobotMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.*;
import com.ruoyi.system.openapi.model.output.ExtTgBatchRobotSimpInfoData;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotByMerchantVO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IRobotService;
import com.ruoyi.system.service.RobotStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RobotServiceImpl extends ServiceImpl<RobotMapper, Robot> implements IRobotService {
    @Resource
    private RobotMapper robotMapper;
    @Resource
    private GroupRobotMapper groupRobotMapper;

    @Resource
    private RobotStatisticsService robotStatisticsService;
    @Override
    public R<Page<SelectRobotListVO>> selectRobotPageList(SelectRobotListDTO dto) {
        Page<SelectRobotListVO> page = new Page<>(dto.getPage(),dto.getLimit());
        List<SelectRobotListVO> selectRobotListVOS = robotMapper.selectRobotPageList(page, dto);
        page.setRecords(selectRobotListVOS);
        return R.ok(page);
    }

    @Override
    public R<Void> syncRobot() {
        ThirdTgSelectRobotListByRadioDTO robotDTO = new ThirdTgSelectRobotListByRadioDTO();
        robotDTO.setLimit(1000);
        robotDTO.setPage(1);
        robotDTO.setMerchantId("");
        OpenApiResult<Page<ExtTgSelectRobotByMerchantVO>> robotListResult = OpenApiClient.selectRobotListByRadioByThirdUtchatTg(robotDTO);
        log.info("pullApiRobotDataList robotListResult:{}",JSON.toJSONString(robotListResult));
        if(robotListResult.isSuccess()){
            Page<ExtTgSelectRobotByMerchantVO> data = robotListResult.getData();
            this.pullRobotDataList(data.getRecords());
            long count = data.getTotal()/robotDTO.getLimit() - 1;
            if(count > 0){
                for (long i = 0; i < count; i++) {
                    robotDTO.setPage(robotDTO.getPage()+1);
                    OpenApiResult<Page<ExtTgSelectRobotByMerchantVO>> pageOpenApiResult = OpenApiClient.selectRobotListByRadioByThirdUtchatTg(robotDTO);
                    log.info("pullApiRobotDataList pageOpenApiResult:{}",JSON.toJSONString(pageOpenApiResult));
                    if(pageOpenApiResult.isSuccess()){
                        Page<ExtTgSelectRobotByMerchantVO> pageData = pageOpenApiResult.getData();
                        this.pullRobotDataList(pageData.getRecords());
                    }

                }
            }
        }
        return R.ok();
    }

    private void pullRobotDataList(List<ExtTgSelectRobotByMerchantVO> dataList){
        if(CollectionUtils.isEmpty(dataList)){
            log.info("List ExtTgSelectRobotByMerchantVO 等于空");
            return;
        }
        List<String> robotSerialNos = dataList.stream().map(ExtTgSelectRobotByMerchantVO::getRobotSerialNo).collect(Collectors.toList());
        Map<String, ExtTgBatchRobotSimpInfoData> simpMap = this.pullApiRobotSimpMap(robotSerialNos);
        log.info("pullRobotDataList robotSerialNos:{},simpMap:{},",robotSerialNos,simpMap);
        List<Robot> robotList = Lists.newArrayList();
        for (ExtTgSelectRobotByMerchantVO vo : dataList) {
            Robot robot = JSON.parseObject(JSON.toJSONString(vo), Robot.class);
            robot.setPhone(vo.getAccount());
            robot.setId(vo.getRobotId());
            if(simpMap.containsKey(robot.getRobotSerialNo())){
                ExtTgBatchRobotSimpInfoData mapData = simpMap.get(robot.getRobotSerialNo());
                robot.setTgAppVersion(mapData.getTgAppVersion());
                robot.setProtocolType(mapData.getProtocolType());
                robot.setProxyType(mapData.getProxyType());
                robot.setVpnIp(mapData.getSenderProxyIp());
                if(StringUtils.isNotEmpty(mapData.getSenderProxyIp())){
                    String[] split = mapData.getSenderProxyIp().split(".");
                    robot.setVpnIpB(split[0]+"."+split[1]);
                    robot.setVpnIpB(split[0]+"."+split[1]+"."+split[2]);
                }
            }
            robotList.add(robot);
        }
        this.storyRobot(robotList);
    }

    private void storyRobot(List<Robot> newRobotList){
        List<String> newRobotSerialNos = newRobotList.stream().map(Robot::getRobotSerialNo).collect(Collectors.toList());
        List<Robot> oldRobotList = robotMapper.selectList(new LambdaUpdateWrapper<Robot>().in(Robot::getRobotSerialNo, newRobotSerialNos));
        if(CollectionUtils.isEmpty(oldRobotList)){
            this.saveBatch(this.compensateSetGroupOwnerRobot(newRobotList));
            this.insertRobotStatistics(newRobotList);
            return;
        }
        List<Robot> saveRobotList = Lists.newArrayList();
        List<Robot> updateRobotList = Lists.newArrayList();
        Map<String,Robot> oldMap = new HashMap<>();
        oldRobotList.forEach(item->oldMap.put(item.getRobotSerialNo(),item));
        for (Robot newRobot : newRobotList) {
            if(!oldMap.containsKey(newRobot.getRobotSerialNo())){
                newRobot.setUpdateTime(new Date());
                newRobot.setCreateTime(new Date());
                saveRobotList.add(newRobot);
                continue;
            }
            Boolean flag = false;
            Robot oldRobot = oldMap.get(newRobot.getRobotSerialNo());
            if(modify(oldRobot.getUserName(),newRobot.getUserName())){
                oldRobot.setUserName(newRobot.getUserName());
                flag = true;
            }
            if(modify(oldRobot.getFirstName(),newRobot.getFirstName())){
                oldRobot.setFirstName(newRobot.getFirstName());
                flag = true;
            }
            if(modify(oldRobot.getLastName(),newRobot.getLastName())){
                oldRobot.setLastName(newRobot.getLastName());
                flag = true;
            }
            if(modify(oldRobot.getPhone(),newRobot.getPhone())){
                oldRobot.setPhone(newRobot.getPhone());
                flag = true;
            }
            if(modify(oldRobot.getHeadImgUrl(),newRobot.getHeadImgUrl())){
                oldRobot.setHeadImgUrl(newRobot.getHeadImgUrl());
                flag = true;
            }
            if(modify(oldRobot.getCountry(),newRobot.getCountry())){
                oldRobot.setCountry(newRobot.getCountry());
                flag = true;
            }
            if(modify(oldRobot.getCountryCode(),newRobot.getCountryCode())){
                oldRobot.setCountryCode(newRobot.getCountryCode());
                flag = true;
            }
            if(modify(oldRobot.getType(),newRobot.getType())){
                oldRobot.setType(newRobot.getType());
                flag = true;
            }
            if(modify(oldRobot.getHeartbeatStatus(),newRobot.getHeartbeatStatus())){
                oldRobot.setHeartbeatStatus(newRobot.getHeartbeatStatus());
                flag = true;
            }
            if(modify(oldRobot.getSealStatus(),newRobot.getSealStatus())){
                oldRobot.setSealStatus(newRobot.getSealStatus());
                flag = true;
            }
            if(modify(oldRobot.getProtocolType(),newRobot.getProtocolType())){
                oldRobot.setProtocolType(newRobot.getProtocolType());
                flag = true;
            }
            if(modify(oldRobot.getProxyType(),newRobot.getProxyType())){
                oldRobot.setProxyType(newRobot.getProxyType());
                flag = true;
            }
            if(modify(oldRobot.getTgAppVersion(),newRobot.getTgAppVersion())){
                oldRobot.setTgAppVersion(newRobot.getTgAppVersion());
                flag = true;
            }
            if(modify(oldRobot.getVpnIp(),newRobot.getVpnIp())){
                oldRobot.setVpnIp(newRobot.getVpnIp());
                flag = true;
            }
            if(modify(oldRobot.getVpnIpB(),newRobot.getVpnIpB())){
                oldRobot.setVpnIpB(newRobot.getVpnIpB());
                flag = true;
            }
            if(modify(oldRobot.getVpnIpC(),newRobot.getVpnIpC())){
                oldRobot.setVpnIpC(newRobot.getVpnIpC());
                flag = true;
            }
            if(flag){
                oldRobot.setUpdateTime(new Date());
                updateRobotList.add(oldRobot);
            }
        }
        if(!CollectionUtils.isEmpty(saveRobotList)){
            this.saveBatch(this.compensateSetGroupOwnerRobot(saveRobotList));
            this.insertRobotStatistics(saveRobotList);
        }
        if(!CollectionUtils.isEmpty(updateRobotList)){
            this.updateBatchById(updateRobotList);
        }
    }

    /**
     * 生成机器人统计数据
     * @param saveRobotList
     */
    private void insertRobotStatistics(List<Robot> saveRobotList){
        List<RobotStatistics> saveRobotStatistics = Lists.newArrayList();
        for (Robot robot : saveRobotList) {
            RobotStatistics robotStatistics = new RobotStatistics();
            robotStatistics.setId(IdWorker.getIdStr());
            robotStatistics.setRobotSerialNo(robot.getRobotSerialNo());
            robotStatistics.setCreateTime(new Date());
            robotStatistics.setUpdateTime(new Date());
            robotStatistics.setOneDayJoinGroupCount(0);
            robotStatistics.setTotalJoinGroupCount(0);
            robotStatistics.setOneDaySetAdminCount(0);
            robotStatistics.setTotalSetAdminCount(0);
            robotStatistics.setGroupCount(0);
            robotStatistics.setIsLock(0);
            robotStatistics.setTotalLeaderSetAdminCount(0);
            saveRobotStatistics.add(robotStatistics);
        }
        robotStatisticsService.saveBatch(saveRobotStatistics);

    }

    /**
     * 补充机器号是否是群主
     * @param saveRobotList
     */
    private List<Robot> compensateSetGroupOwnerRobot(List<Robot> saveRobotList){
        List<String> robotSerialNos = saveRobotList.stream().map(Robot::getRobotSerialNo).collect(Collectors.toList());
        List<GroupRobot> groupRobots = groupRobotMapper.selectList(new LambdaUpdateWrapper<GroupRobot>()
                .in(GroupRobot::getRobotId, robotSerialNos)
                .eq(GroupRobot::getMemberType, 1));
        if(CollectionUtils.isEmpty(groupRobots)){
            return saveRobotList;
        }
        Map<String,GroupRobot> map = new HashMap<>();
        groupRobots.forEach(item->map.put(item.getRobotId(),item));
        for (Robot robot : saveRobotList) {
            if(map.containsKey(robot.getRobotSerialNo())){
                robot.setGroupOwner(1);
            }
        }
        return saveRobotList;

    }


    private Boolean modify(Object oldData,Object newData){
        if(ObjectUtil.isEmpty(oldData) && ObjectUtil.isEmpty(newData)){
            return false;
        }
        if(ObjectUtil.isEmpty(oldData) && !ObjectUtil.isEmpty(newData)){
            return true;
        }
        if(!ObjectUtil.isEmpty(oldData) && ObjectUtil.isEmpty(newData)){
            return true;
        }
        if(!oldData.equals(newData)){
            return true;
        }
        return false;
    }

    private Map<String,ExtTgBatchRobotSimpInfoData> pullApiRobotSimpMap(List<String> robotSerialNos){
        ThirdTgBatchRobotSimpInfoInputDTO dto = new ThirdTgBatchRobotSimpInfoInputDTO();
        dto.setRobotSerialNos(robotSerialNos);
        OpenApiResult<TgBaseOutputDTO<List<ExtTgBatchRobotSimpInfoData>>> result = OpenApiClient.batchRobotSimpInfoByThirdKpTg(dto);
        Map<String,ExtTgBatchRobotSimpInfoData> map = new HashMap<>();
        if(result.isSuccess()){
            List<ExtTgBatchRobotSimpInfoData> data = result.getData().getData();
            data.forEach(item->map.put(item.getRobotSerialNo(),item));
            return map;
        }
        return map;
    }


    @Override
    public R<Void> setHeadImg(SetHeadImgDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        if(CollectionUtils.isEmpty(dto.getHeadImgUrls())){
            return R.fail("头像地址不能为空");
        }
        for (int i = 0; i < dto.getRobotSerialNos().size(); i++) {
            ThirdTgModifyRobotHeadImgInputDTO inputDTO = new ThirdTgModifyRobotHeadImgInputDTO();
            inputDTO.setTgRobotId(dto.getRobotSerialNos().get(i));
            inputDTO.setHeadimgUrl(dto.getHeadImgUrls().get(i%dto.getHeadImgUrls().size()));
            OpenApiResult<TgBaseOutputDTO> vo = OpenApiClient.modifyRobotHeadImgByThirdKpTg(inputDTO);
            log.info("setHeadImg inputDTO:{},vo:{}",inputDTO,vo);
        }
        return R.ok();
    }

    @Override
    public R<Void> setName(SetNameInfoDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        if(CollectionUtils.isEmpty(dto.getInfos())){
            return R.fail("名称信息不能为空");
        }
        for (int i = 0; i < dto.getRobotSerialNos().size(); i++) {
            ThirdTgModifyNameInputDTO inputDTO = new ThirdTgModifyNameInputDTO();
            inputDTO.setTgRobotId(dto.getRobotSerialNos().get(i));
            SetNameResourceVO info = dto.getInfos().get(i % dto.getInfos().size());
            inputDTO.setLastNameBase64(info.getLastName());
            inputDTO.setFirstNameBase64(info.getFirstName());
            inputDTO.setBriefIntroBase64(info.getBriefIntro());
            OpenApiResult<TgBaseOutputDTO> vo = OpenApiClient.modifyNameByThirdKpTg(inputDTO);
            log.info("setName inputDTO:{},vo:{}",inputDTO,vo);
        }
        return R.ok();
    }

    @Override
    public List<SetNameResourceVO> analysisExcelInfo(MultipartFile file) {
        List<SetNameResourceVO> excel = Lists.newArrayList();
        try {
            ExcelUtil<SetNameResourceVO> util = new ExcelUtil<>(SetNameResourceVO.class);
            excel = util.importExcel(file.getInputStream()).stream().filter(f->StringUtils.isNotEmpty(f.getFirstName()) && StringUtils.isNotEmpty(f.getLastName())).collect(Collectors.toList());
        }catch (Exception e){
            log.info("robotService analysisExcelInfo.error={}", file.getOriginalFilename(), e);
        }
        Assert.notEmpty(excel, "文件解析失败");
        return excel;
    }

    @Override
    public R<Void> setUserName(SetUserNameDTO dto) {
        if(StringUtils.isEmpty(dto.getUserName())){
            return R.fail("用户名不能为空");
        }
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        List<String> userNameList = Lists.newArrayList();
        for (String code : dto.getCode()) {
            userNameList.add(dto.getUserName()+code);
        }
        for (int i = 0; i < dto.getRobotSerialNos().size(); i++) {
            ThirdTgModifyUserNameInputDTO inputDTO = new ThirdTgModifyUserNameInputDTO();
            inputDTO.setTgRobotId(dto.getRobotSerialNos().get(i));
            inputDTO.setUserName(userNameList.get(i%userNameList.size()));
            OpenApiResult<TgBaseOutputDTO> vo = OpenApiClient.modifyUserNameByThirdKpTg(inputDTO);
            log.info("setUserName inputDTO:{},vo:{}",inputDTO,vo);
        }
        return R.ok();
    }

    @Override
    public R<Void> setPrivatePhone(SetPrivatePhoneDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        for (String robotSerialNo : dto.getRobotSerialNos()) {
            ThirdTgSetPhoneVisibilityInputDTO inputDTO = new ThirdTgSetPhoneVisibilityInputDTO();
            inputDTO.setTgRobotId(robotSerialNo);
            inputDTO.setType(dto.getType());
            OpenApiResult<TgBaseOutputDTO> vo = OpenApiClient.setPhoneVisibilityByThirdKpTg(inputDTO);
            log.info("setPrivatePhone inputDTO:{},vo:{}",inputDTO,vo);
        }
        return R.ok();
    }

    @Override
    public R<Void> updateEnableType(UpdateEnableTypeDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        if(dto.getEnableType() == null){
            return R.fail("类型不能为空");
        }
        boolean update = this.update(new LambdaUpdateWrapper<Robot>()
                .in(Robot::getEnableType, dto.getRobotSerialNos())
                .set(Robot::getEnableType, dto.getEnableType()));
        if(update)return R.ok();
        return R.fail("操作失败");
    }

    @Override
    public R<Void> clearSealData(ClearSealDataDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        this.update(new LambdaUpdateWrapper<Robot>().in(Robot::getRobotSerialNo,dto.getRobotSerialNos()).set(Robot::getDeleteStatus,1));
        return R.ok();
    }

    @Override
    public R<Void> removeRobot(RemoveRobotDTO dto) {
        return null;
    }

    @Override
    public R<Void> releaseOccupyRobot(ReleaseOccupyRobotDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        robotStatisticsService.update(new LambdaUpdateWrapper<RobotStatistics>()
                .in(RobotStatistics::getRobotSerialNo,dto.getRobotSerialNos())
                .set(RobotStatistics::getIsLock,0));
        return R.ok();
    }

    @Override
    public R<Void> setGroupOwner(List<String> robotSerialNos) {
        if(CollectionUtils.isEmpty(robotSerialNos)){
            return R.ok();
        }
        this.update(new LambdaUpdateWrapper<Robot>().in(Robot::getRobotSerialNo,robotSerialNos).set(Robot::getGroupOwner,1));
        return R.ok();
    }

    @Override
    public RobotStatisticsVO getRobotStatisticsVO(List<String> robotIds) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(robotIds)){
            return new RobotStatisticsVO();
        }
        return robotMapper.getRobotStatisticsVO(robotIds);
    }
}
