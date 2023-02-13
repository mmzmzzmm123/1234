package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.AwardPerson;

import java.util.List;

/**
 * <p>
 * 参赛人员信息表 Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface AwardPersonMapper extends BaseMapper<AwardPerson> {
    /**
     * 查询参赛人员信息列表
     *
     * @param awardPerson 参赛人员信息
     * @return 参赛人员信息集合
     */
    public List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson);

}
