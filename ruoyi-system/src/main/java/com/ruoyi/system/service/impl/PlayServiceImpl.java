package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.system.domain.dto.play.PlayDTO;
import com.ruoyi.system.domain.dto.play.QueryPlayDTO;
import com.ruoyi.system.domain.vo.play.QueryPlayVO;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.service.IPlayService;
import org.springframework.stereotype.Service;

@Service
public class PlayServiceImpl extends ServiceImpl<PlayMapper, Play> implements IPlayService {
    @Override
    public R<String> create(PlayDTO dto) {
        //更新基础信息表

        //剧本内容，根据label将演员和剧本对应

        //判断混淆状态 t_play_message_confound

        return R.ok();
    }

    private void checkPlayParams() {

    }

    @Override
    public R<String> update(PlayDTO dto) {
        //判断状态，不同状态限制修改字段

        //更新基础信息表

        //删除人设包装

        //保存人设包装

        //判断混淆状态 t_play_message_confound

        return R.ok();
    }

    @Override
    public R<PlayDTO> info(String id) {
        PlayDTO playDTO = new PlayDTO();
        playDTO.setId("1");
        return R.ok(playDTO);
    }

    @Override
    public Page<QueryPlayVO> page(QueryPlayDTO dto) {
        Page<QueryPlayVO> page = new Page<>(dto.getPage(),dto.getLimit());

        return page;
    }
}
