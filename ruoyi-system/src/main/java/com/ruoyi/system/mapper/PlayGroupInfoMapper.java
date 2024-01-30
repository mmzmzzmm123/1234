package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayGroupInfoMapper  extends BaseMapper<PlayGroupInfo> {

    public PlayGroupInfo selectGroupInfoById(@Param("tgGroupId")String tgGroupId,@Param("playId")String playId);


    public Integer selectSuccessGroupCount(@Param("playId")String playId);

    public Integer selectErrorGroupCount(@Param("playId")String playId);

    List<PlayGroupInfo>  selectGroupInfoByPlayId(@Param("playId")String playId);


    Integer updateGroupState(@Param("playID")String playId);

    PlayGroupInfo selectGroupByUrl(@Param("groupUrl")String groupUrl,@Param("playId")String playId);

    PlayGroupInfo selectGroupById(@Param("groupId")String groupId);

}
