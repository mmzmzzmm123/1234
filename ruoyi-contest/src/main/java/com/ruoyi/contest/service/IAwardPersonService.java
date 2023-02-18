package com.ruoyi.contest.service;

import java.util.List;

import com.ruoyi.contest.domain.AwardPerson;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 参赛人员信息Service接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IAwardPersonService extends IService<AwardPerson>
{
    /**
     * 查询参赛人员信息列表
     *
     * @param awardPerson 参赛人员信息
     * @return 参赛人员信息集合
     */
    public List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson);
}
