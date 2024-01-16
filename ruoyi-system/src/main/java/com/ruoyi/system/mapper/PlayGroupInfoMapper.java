package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import org.apache.ibatis.annotations.Param;

public interface PlayGroupInfoMapper  extends BaseMapper<PlayGroupInfo> {

    public PlayGroupInfo selectGroupInfoById(@Param("tgGroupId")String tgGroupId);

}
