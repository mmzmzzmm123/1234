package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;

import java.util.List;

/**
 *
 */
public interface PlayMessageService extends IService<PlayMessage> {

    List<PlayMessage> listByPlayId(String playId);

    void deleteByPlayId(String playId);

}
