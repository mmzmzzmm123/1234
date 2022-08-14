package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.ReactionVo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/2
 * Time: 21:29
 */
public interface IReactionService {

    void addAndUpdateReaction(ReactionVo reactionVo);

    List<ReactionVo> getReactionList();
}
