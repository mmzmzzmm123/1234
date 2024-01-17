package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.mapper.PlayGroupInfoMapper;
import com.ruoyi.system.service.PlayGroupInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 14:36
 * @Description :
 */
@Service
@Slf4j
public class PlayGroupInfoServiceImpl extends ServiceImpl<PlayGroupInfoMapper, PlayGroupInfo>
        implements PlayGroupInfoService {


    @Override
    public List<PlayGroupInfo> listByPlayId(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<PlayGroupInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayGroupInfo::getPlayId, playId);
        lambdaQueryWrapper.orderByAsc(PlayGroupInfo::getCreateTime);
        return super.list(lambdaQueryWrapper);
    }
}
