package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.core.domain.entity.robot.RobotStatistics;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.dto.robot.AddCountDTO;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.dto.robot.SelectRobotByRuleDTO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.domain.vo.robot.SelectRobotByRuleVO;
import com.ruoyi.system.mapper.RobotStatisticsMapper;
import com.ruoyi.system.mapper.VibeRuleMapper;
import com.ruoyi.system.service.RobotStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RobotStatisticsServiceImpl extends ServiceImpl<RobotStatisticsMapper, RobotStatistics> implements RobotStatisticsService {

    @Autowired
    private RobotStatisticsMapper robotStatisticsMapper;
    @Autowired
    private VibeRuleMapper vibeRuleMapper;
    @Autowired
    private RedisLock redisLock;

    @Transactional
    @Override
    public R<List<GetRobotVO>> getRobot(GetRobotDTO dto) {
        log.info("RobotStatisticsService_getRobot_params:{}", JSONObject.toJSONString(dto));
        VibeRule vibeRule = vibeRuleMapper.selectOne(new LambdaQueryWrapper<VibeRule>().last("limit 1"));
        if(vibeRule == null){
            return R.fail("查询不到规则表数据");
        }
        SelectRobotByRuleDTO selectRobotByRuleDTO = new SelectRobotByRuleDTO();
        selectRobotByRuleDTO.setGroupCount(vibeRule.getGroupNum());
        selectRobotByRuleDTO.setOneDayJoinGroupCount(vibeRule.getJoinLimitByDay());
        selectRobotByRuleDTO.setTotalJoinGroupCount(vibeRule.getJoinLimitByTotal());
        selectRobotByRuleDTO.setOneDaySetAdminCount(vibeRule.getSetManageLimitByDay());
        selectRobotByRuleDTO.setTotalSetAdminCount(vibeRule.getSetManageLimitByTotal());
        selectRobotByRuleDTO.setIsLock(dto.getIsLock());
        Boolean adminFlag = false;
        int size;
        List<SelectRobotByRuleVO> tmpSelectData;
        List<SelectRobotByRuleVO> selectRobotByRuleVOS = new ArrayList<>();
        //如果需要设置管理员的号,先获取设置管理员的号
        if(dto.getSetAdminCount() > 0){
            selectRobotByRuleDTO.setLimit(dto.getSetAdminCount());
            selectRobotByRuleDTO.setIsSetAdmin(1);
            adminFlag = true;
            selectRobotByRuleDTO.setCountryCode(dto.getCountryCode());
            selectRobotByRuleDTO.setSetAdminCount(dto.getSetAdminCount());
            selectRobotByRuleDTO.setIpType(dto.getIpType());
            selectRobotByRuleVOS = robotStatisticsMapper.selectRobotByRule(selectRobotByRuleDTO);
            log.info("getRobot selectRobotByRuleVOS:{}",selectRobotByRuleVOS);

            // 目标国家不够则随机取其它国家
            if (CollectionUtils.isEmpty(selectRobotByRuleVOS) || selectRobotByRuleVOS.size() < dto.getSetAdminCount()) {
                size = dto.getSetAdminCount() - (CollectionUtils.isEmpty(selectRobotByRuleVOS) ? 0 : selectRobotByRuleVOS.size());
                tmpSelectData = this.selectRobotByAllCountyCode(selectRobotByRuleDTO, size);
                log.info("RobotStatisticsService_getRobot_tmpAdminRobotSize:{}", null == tmpSelectData ? 0 : tmpSelectData.size());
                if (CollectionUtils.isNotEmpty(tmpSelectData)) {
                    selectRobotByRuleVOS.addAll(tmpSelectData);
                }
            }

            if(CollectionUtils.isEmpty(selectRobotByRuleVOS) || selectRobotByRuleVOS.size() < dto.getSetAdminCount()){
                log.info("RobotStatisticsService_getRobot_管理员号资源不足");
                return R.fail("管理员号资源不足");
            }
        }

        List<SelectRobotByRuleVO> selectRobotByRuleVOSTotal = new ArrayList<>();
        selectRobotByRuleVOSTotal.addAll(selectRobotByRuleVOS);
        List<SelectRobotByRuleVO> selectRobotByRuleVOS1 = new ArrayList<>();
        //如果有需要设置管理员号的,需要再次获取其他号
        if(dto.getCount() > 0){
            List<String> ips = selectRobotByRuleVOS.stream()
                    .map(SelectRobotByRuleVO::getIp)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(ips)){
                selectRobotByRuleDTO.setIps(ips);
            }
            List<String> excludeRobotSerialNos = selectRobotByRuleVOS.stream().map(SelectRobotByRuleVO::getRobotSerialNo).collect(Collectors.toList());
            selectRobotByRuleDTO.setIsSetAdmin(0);
            selectRobotByRuleDTO.setLimit(dto.getCount());
            selectRobotByRuleDTO.setExcludeRobotSerialNos(excludeRobotSerialNos);
            selectRobotByRuleVOS1 = robotStatisticsMapper.selectRobotByRule(selectRobotByRuleDTO);
            log.info("getRobot selectRobotByRuleVOS1:{}",selectRobotByRuleVOS1);

            if (CollectionUtils.isEmpty(selectRobotByRuleVOS1) || selectRobotByRuleVOS1.size() < dto.getCount()) {
                size = dto.getCount() - (CollectionUtils.isEmpty(selectRobotByRuleVOS1) ? 0 : selectRobotByRuleVOS1.size());
                tmpSelectData = this.selectRobotByAllCountyCode(selectRobotByRuleDTO, size);
                log.info("RobotStatisticsService_getRobot_tmpRobotSize:{}", null == tmpSelectData ? 0 : tmpSelectData.size());
                if (CollectionUtils.isNotEmpty(tmpSelectData)) {
                    selectRobotByRuleVOS1.addAll(tmpSelectData);
                }
            }

            if(null == selectRobotByRuleVOS1 || selectRobotByRuleVOS1.size() < dto.getCount()){
                return R.fail("普通号资源不足");
            }
            selectRobotByRuleVOSTotal.addAll(selectRobotByRuleVOS1);
        }
        List<GetRobotVO> resultData = new ArrayList<>();
        try {
            lockRobotByIds(selectRobotByRuleVOSTotal, dto.getIsLock());
            if(adminFlag){
                //增加设置管理员号的计数
                List<String> adminRobotSerialNos = selectRobotByRuleVOS.stream().map(SelectRobotByRuleVO::getRobotSerialNo).collect(Collectors.toList());
                AddCountDTO addCountDTO = new AddCountDTO();
                addCountDTO.setRobotSerialNos(adminRobotSerialNos);
                addCountDTO.setIsSetAdmin(1);
                robotStatisticsMapper.addCount(addCountDTO);
                for (String robotSerialNo : adminRobotSerialNos) {
                    GetRobotVO vo = new GetRobotVO();
                    vo.setIsSetAdmin(1);
                    vo.setRobotSerialNo(robotSerialNo);
                    resultData.add(vo);
                }
            }
            if(dto.getCount() > 0){
                //增加号的计数
                List<String> robotSerialNos = selectRobotByRuleVOS1.stream().map(SelectRobotByRuleVO::getRobotSerialNo).collect(Collectors.toList());
                AddCountDTO addCountDTO = new AddCountDTO();
                addCountDTO.setRobotSerialNos(robotSerialNos);
                addCountDTO.setIsSetAdmin(0);
                robotStatisticsMapper.addCount(addCountDTO);
                for (String robotSerialNo : robotSerialNos) {
                    GetRobotVO vo = new GetRobotVO();
                    vo.setIsSetAdmin(0);
                    vo.setRobotSerialNo(robotSerialNo);
                    resultData.add(vo);
                }
            }
        }catch (Exception e){
            log.error("getRobot 计数失败",e);
            return R.fail("计数失败");
        }
        return R.ok(resultData);
    }

    //将号标记为锁定状态
    private void lockRobotByIds(List<SelectRobotByRuleVO> robotData, int lockState) {
        if (CollectionUtils.isNotEmpty(robotData) && lockState == 1) {
            List<String> updateRobotStatisticsIds = robotData.stream()
                    .map(SelectRobotByRuleVO::getId)
                    .filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            log.info("getRobot_update_robotStatistics_ids:{}", updateRobotStatisticsIds);
            this.update(new LambdaUpdateWrapper<RobotStatistics>()
                    .in(RobotStatistics::getId, updateRobotStatisticsIds)
                    .set(RobotStatistics::getIsLock, 1));
        }
    }

    private List<SelectRobotByRuleVO> selectRobotByAllCountyCode(SelectRobotByRuleDTO query, Integer limit) {
        SelectRobotByRuleDTO selectRobotQuery = new SelectRobotByRuleDTO();
        BeanUtils.copyProperties(query, selectRobotQuery);
        selectRobotQuery.setCountryCode(null);
        selectRobotQuery.setLimit(limit);
        return robotStatisticsMapper.selectRobotByRule(selectRobotQuery);
    }

    @Override
    public boolean checkAndAddLeaderCount(String robotSerialNo, Integer count, Integer checkCount) {
        String key = "robotStatistics:leaderCount:" + robotSerialNo;
        //等待锁 等待10秒
        if (!redisLock.tryLock(key, 10, 5, TimeUnit.SECONDS)) {
            return false;
        }
        try {
            RobotStatistics robotStatistics = baseMapper.selectById(robotSerialNo);
            if(robotStatistics == null){
                return false;
            }
            if(robotStatistics.getTotalLeaderSetAdminCount() + count > checkCount){
                return false;
            }
            baseMapper.addLeaderCount(robotSerialNo, count);
            return true;
        } finally {
            redisLock.unlock(key);
        }
    }

    @Override
    public void restoreQuantity(String robotSerialNo, Integer count) {
        String key = "robotStatistics:leaderCount:" + robotSerialNo;
        //等待锁 等待10秒
        if (redisLock.tryLock(key, 10, 5, TimeUnit.SECONDS)) {
            //加锁失败直接相减
            baseMapper.addLeaderCount(robotSerialNo, -count);
            return ;
        }
        try {
            baseMapper.addLeaderCount(robotSerialNo, count);
        } finally {
            redisLock.unlock(key);
        }
    }

    @Override
    public boolean unLockRobot(List<String> robotSerialNos) {
        if(this.update(new LambdaUpdateWrapper<RobotStatistics>().in(RobotStatistics::getRobotSerialNo,robotSerialNos).set(RobotStatistics::getIsLock,0))){
            return true;
        }
        return false;
    }

    @Override
    public boolean decreaseRobotGroup(List<String> robotSerialNos) {
        if(!CollectionUtils.isEmpty(robotSerialNos)){
            baseMapper.decreaseRobotGroup(robotSerialNos);
            return true;
        }
        return false;
    }

    @Override
    public void clearRobotOneDayNum() {
        List<RobotStatistics> robotStatistics = baseMapper.selectList(null);
        if(!CollectionUtils.isEmpty(robotStatistics)){
            for (List<RobotStatistics> statistics : ListTools.partition(robotStatistics, 500)) {
                List<String> robotSerialNos = statistics.stream().map(RobotStatistics::getRobotSerialNo).collect(Collectors.toList());
                baseMapper.clearRobotOneDayNum(robotSerialNos);
            }
        }
    }


}
