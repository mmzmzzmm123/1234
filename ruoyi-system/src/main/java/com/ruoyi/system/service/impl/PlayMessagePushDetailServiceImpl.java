package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.service.PlayMessagePushDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class PlayMessagePushDetailServiceImpl extends ServiceImpl<PlayMessagePushDetailMapper, PlayMessagePushDetail>
		implements PlayMessagePushDetailService {
	@Override
	public List<PlayMessagePushDetail> listByPlayIdStatistics(String playId) {
		if (StringUtils.isEmpty(playId)){
			return new ArrayList<>();
		}
		return baseMapper.listByPlayIdStatistics(playId);
	}
}
