package com.ruoyi.system.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetailTrack;
import com.ruoyi.common.utils.ListTools;

public interface PlayMessagePushDetailTrackMapper extends BaseMapper<PlayMessagePushDetailTrack> {

	public default List<String> getOne(String playId, String groupId, int sort) {
		List<PlayMessagePushDetailTrack> datas = selectList(new QueryWrapper<PlayMessagePushDetailTrack>().lambda()
				.eq(PlayMessagePushDetailTrack::getPlayId, playId).eq(PlayMessagePushDetailTrack::getGroupId, groupId)
				.eq(PlayMessagePushDetailTrack::getMessageSort, sort));

		if (CollectionUtils.isEmpty(datas)) {
			return Collections.emptyList();
		}
		List<String> ids = ListTools.extract(datas, f -> f.getRobotId());

		return new ArrayList<String>(new HashSet<>(ids));
	}
}
