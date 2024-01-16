package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.mapper.PlayMessageConfoundMapper;
import com.ruoyi.system.service.IPlayService;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.PlayMessageConfoundService;
import com.ruoyi.system.service.PlayMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class PlayMessageConfoundServiceImpl extends ServiceImpl<PlayMessageConfoundMapper, PlayMessageConfound>
        implements PlayMessageConfoundService {


    @Resource
    private IPlayService playService;

    @Resource
    private PlayMessageService playMessageService;

    @Resource
    private PlayMessageConfoundLogService playMessageConfoundLogService;

    @Override
    public void clearAndCreateConfound(Integer playId) {
        if (playId == null) {
            return;
        }
        this.clearAndCreateConfound(playService.getById(playId), playMessageService.listByPlayId(playId));
    }

    @Override
    public void clearAndCreateConfound(Play playInfo, List<PlayMessage> messages) {
        if (playInfo == null || CollectionUtils.isEmpty(messages)) {
            return;
        }
        // 清理关联数据
        this.clearConfound(playInfo.getId());

        // 创建混淆数据
        try {
            for (PlayMessage message : messages) {
                List<ContentJson> messageContentList = message.getMessageContentList();
                if (CollectionUtils.isEmpty(messageContentList) && StringUtils.isNotBlank(message.getMessageContent())) {
                    messageContentList = JSONArray.parseArray(message.getMessageContent(), ContentJson.class);
                }
                List<PlayMessageConfound> confoundList = new ArrayList<>();
                for (ContentJson contentJson : messageContentList) {
                    Integer momentTypeId = contentJson.getMomentTypeId();
                    // 过滤不需要混淆的类型
                    if (!PlayConstants.confoundMomentTypeIds.contains(momentTypeId)) {
                        continue;
                    }
                    PlayMessageConfound confound = new PlayMessageConfound();
                    confound.setPlayId(playInfo.getId());
                    confound.setPlayMessageId(message.getPlayMessageId());
                    confound.setUuid(contentJson.getUuid());
                    confound.setMomentTypeId(momentTypeId);
                    String confoundContent = momentTypeId == 2002 ?
                            contentJson.getSMateImgUrl() : contentJson.getSMateContent();
                    confound.setConfoundContent(confoundContent);
                    confound.setGroupNum(playInfo.getGroupNum());
                    confoundList.add(confound);
                }
                super.saveBatch(confoundList);
                playMessageConfoundLogService.createConfoundLog(confoundList);
            }
        } catch (Exception e) {
            log.info("clearAndCreateConfound error", e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void clearConfound(String playId) {
        LambdaQueryWrapper<PlayMessageConfound> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayMessageConfound::getPlayId, playId);
        List<PlayMessageConfound> confoundList = super.list(lambdaQueryWrapper);
        if (CollectionUtils.isNotEmpty(confoundList)) {
            return;
        }
        List<Integer> confoundIds = confoundList.stream()
                .map(PlayMessageConfound::getId)
                .collect(Collectors.toList());
        super.removeByIds(confoundIds);
        playMessageConfoundLogService.removeByConfoundIds(confoundIds);
    }

}




