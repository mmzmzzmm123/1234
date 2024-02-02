package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.dto.Called1100910012DTO;
import com.ruoyi.system.callback.dto.Called1100910027DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.FriendRobot;
import com.ruoyi.system.mapper.FriendRobotMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgRobotInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.FriendRobotService;
import com.ruoyi.system.utils.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("friendRobotService")
public class FriendRobotServiceImpl extends ServiceImpl<FriendRobotMapper, FriendRobot> implements FriendRobotService {


    @Override
    public void addSync(CalledDTO root, List<Called1100910027DTO> dto) {
        if (CollUtil.isEmpty(dto)) {
            return;
        }
        String robotSerialNo = root.getRobotId();
        List<String> freSerialNos = dto.stream().map(Called1100910027DTO::getFrom_serial_no).collect(Collectors.toList());

        List<FriendRobot> list = this.list(new LambdaQueryWrapper<FriendRobot>()
                .in(FriendRobot::getFriendSerialNo, freSerialNos)
                .eq(FriendRobot::getRobotSerialNo, robotSerialNo));

        Map<String, FriendRobot> freMap = list
                .stream().collect(Collectors.toMap(v -> StringUtils.join(v.getFriendSerialNo(), v.getRobotSerialNo()), Function.identity()));

        RedisLock redisLock = SpringUtils.getBean(RedisLock.class);

        List<String> lockIds = new ArrayList<>();
        List<FriendRobot> addList = new ArrayList<>();
        for (Called1100910027DTO fre : dto) {
            String freSerialNo = fre.getFrom_serial_no();
            FriendRobot freInfo = freMap.getOrDefault(freSerialNo + robotSerialNo, new FriendRobot());
            freInfo.setRobotSerialNo(robotSerialNo);
            freInfo.setFriendSerialNo(freSerialNo);
            freInfo.setCreateTime(LocalDateTime.now());
            freInfo.setUpdateTime(LocalDateTime.now());

            if (StringUtils.isEmpty(freInfo.getId())) {
                freInfo.setId(IdWorker.getIdStr());
                freInfo.setStatus(0);
                String key = "addFreRobot:" + IdUtil.sumId(freSerialNo, robotSerialNo);
                if (redisLock.tryLock(key, 300)) {
                    addList.add(freInfo);
                    lockIds.add(key);
                }
            }
        }

        try {
            if (CollUtil.isNotEmpty(addList)) {
                this.saveBatch(addList);
            }
        } finally {
            lockIds.forEach(p -> redisLock.unlock(p));
        }

        List<FriendRobot> noSyncList = list.stream().filter(freRobot -> freRobot.getStatus() != 2).collect(Collectors.toList());
        if ((CollUtil.isNotEmpty(addList) || CollUtil.isNotEmpty(noSyncList)) //&& redisLock.tryLock(getSyncFreRobotKey(robotSerialNo), 300)
            ) {
            // 如果有新增数据 或 未同步完的数据，则需要同步号的好友。
            syncKpFre(addList, robotSerialNo);
        }
    }

    private void syncKpFre(List<FriendRobot> addList, String robotSerialNo) {
        ThirdTgRobotInputDTO data = new ThirdTgRobotInputDTO();
        data.setTgRobotId(robotSerialNo);
        OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.getRobotFreDataByThirdKpTg(data);
        if (result.isSuccess() && CollUtil.isNotEmpty(addList)) {
            addList.stream().forEach(v -> v.setStatus(1));
            try {
                this.updateBatchById(addList);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void unlockUpdateData(CalledDTO root, List<Called1100910012DTO> dto) {
        String robotSerialNo = root.getRobotId();

//        // 先释放同步好友的号锁
//        RedisLock redisLock = SpringUtils.getBean(RedisLock.class);
//        redisLock.unlock(getSyncFreRobotKey(robotSerialNo));
        if (CollUtil.isEmpty(dto)) {
            return;
        }

        List<String> freSerialNos = dto.stream().map(Called1100910012DTO::getFriend_serial_no).collect(Collectors.toList());

        List<FriendRobot> list = this.list(new LambdaQueryWrapper<FriendRobot>()
                .in(FriendRobot::getFriendSerialNo, freSerialNos)
                .eq(FriendRobot::getRobotSerialNo, robotSerialNo));

        Map<String, FriendRobot> freMap = list
                .stream().collect(Collectors.toMap(v -> StringUtils.join(v.getFriendSerialNo(), v.getRobotSerialNo()), Function.identity()));

        List<FriendRobot> updateList = new ArrayList<>();
        for (Called1100910012DTO fre : dto) {
            String freSerialNo = fre.getFriend_serial_no();
            FriendRobot freInfo = freMap.get(freSerialNo + robotSerialNo);
            if (freInfo == null) {
                continue;
            }
            freInfo.setUpdateTime(LocalDateTime.now());
            freInfo.setStatus(2);
            freInfo.setPhone(fre.getPhone());
            freInfo.setFirstName(fre.getFirst_name());
            freInfo.setLastName(fre.getLast_name());
            freInfo.setIsBot(fre.getIs_bot() != null && fre.getIs_bot() ? 1 : 0);
            freInfo.setUsername(fre.getUsername());
            updateList.add(freInfo);
        }

        if (CollUtil.isNotEmpty(updateList)) {
            this.updateBatchById(updateList);
        }

    }

    private String getSyncFreRobotKey(String robotSerialNo) {
        return "syncFreRobot:" + robotSerialNo;
    }
}
