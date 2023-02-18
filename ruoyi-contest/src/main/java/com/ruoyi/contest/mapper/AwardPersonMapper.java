package com.ruoyi.contest.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.AwardPerson;

/**
 * 参赛人员信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface AwardPersonMapper extends BaseMapper<AwardPerson>
{

    /**
     * 查询参赛人员信息列表
     *
     * @param awardPerson 参赛人员信息
     * @return 参赛人员信息集合
     */
    public List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson);

}
