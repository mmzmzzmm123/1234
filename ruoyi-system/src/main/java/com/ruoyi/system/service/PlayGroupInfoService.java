package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 14:36
 * @Description :
 */
public interface PlayGroupInfoService extends IService<PlayGroupInfo> {

    List<PlayGroupInfo> listByPlayId(String playId);


    R<String> forceFinishGroup(String groupId,String playId);

}
