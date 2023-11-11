package com.ruoyi.platform.task;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.enums.SysShowHideEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.PlatformBanner;
import com.ruoyi.platform.mapper.PlatformBannerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/13 4:06
 */
@Slf4j
@Component("bannerTask")
@RequiredArgsConstructor
public class BannerTask {

    private final PlatformBannerMapper platformBannerMapper;

    /**
     * 巡查banner上下架数据（每天凌晨12点开始执行）
     * */
    public void patrol(){
        log.info("检查广告是否需要启用或停用：开始");
        List<PlatformBanner> platformBanners = platformBannerMapper.selectPlatformBannerList(new PlatformBanner());
        if (ObjectUtil.isEmpty(platformBanners)){
            log.info("检查广告是否需要启用或停用：结束，暂无数据可处理");
            return;
        }
        List<Long> enableIds = new ArrayList<>();
        List<Long> disableIds = new ArrayList<>();
        platformBanners.forEach(item -> {
            boolean isEffective = DateUtils.isEffectiveDate(item.getStartTime(), item.getEndTime());
            if (SysShowHideEnums.SHOW.getCode().equals(item.getState()) && !isEffective){
                disableIds.add(item.getId());
            }
            if (SysShowHideEnums.HIDE.getCode().equals(item.getState()) && isEffective){
                enableIds.add(item.getId());
            }
        });
        // 开始启用
        if (ObjectUtil.isNotEmpty(enableIds)){
            ThreadUtil.execAsync(()->{
                log.info("检查广告是否需要启用或停用：开始启用，数据{}", enableIds);
                platformBannerMapper.updateStateByIds(enableIds, SysShowHideEnums.SHOW.getCode());
                log.info("检查广告是否需要启用或停用：启用成功");
            });
        }

        // 开始禁用
        if (ObjectUtil.isNotEmpty(disableIds)){
            ThreadUtil.execAsync(()->{
                log.info("检查广告是否需要启用或停用：开始禁用，数据：{}", disableIds);
                platformBannerMapper.updateStateByIds(disableIds, SysShowHideEnums.HIDE.getCode());
                log.info("检查广告是否需要启用或停用：停用成功");
            });
        }
        log.info("检查广告是否需要启用或停用：成功");
    }
}
