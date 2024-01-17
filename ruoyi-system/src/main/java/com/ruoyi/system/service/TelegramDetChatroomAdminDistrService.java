package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.robot.TelegramDetChatroomAdminDistr;

import java.util.List;

public interface TelegramDetChatroomAdminDistrService {
    void story(List<TelegramDetChatroomAdminDistr> newAdminDistrs);
    void syncChatroomAdminDistr();
}
