package com.ruoyi.contest.service;

import com.ruoyi.contest.domain.AwardPerson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 参赛人员信息表 服务类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface AwardPersonService extends IService<AwardPerson> {

    /**
     *
     * @param awardPerson
     * @return
     */
    List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson);
}
