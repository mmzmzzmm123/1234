package com.ruoyi.contest.service;

import com.ruoyi.contest.domain.Award;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 获奖登记表 服务类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface AwardService extends IService<Award> {

    /**
     *
     * @param award
     * @return
     */
    List<Award> selectAwardList(Award award);
}
