package com.ruoyi.system.components.prepare.multipack;

import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.enums.ScanProgressEnum;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.prepare.TaskExecuteChainGroup;
import com.ruoyi.system.mapper.PlayMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 开始 人设，剧本等
 * 
 * @author Administrator
 *
 */
@SPI("StartupJobProcessor")
@Slf4j
public class StartupJobProcessor implements LogJobProcessor {

	@Override
	public void handle(Play play) {
		if (play.getScanProgress().intValue() != ScanProgressEnum.Robot.getVal()) {
			return;
		}

		if (play.getRobotSettingsState().intValue() == 1) {
			// 号人设状态 0-未执行 1-已执行
			return;
		}
		log.info("StartupJobProcessor {}" , play);

		
		SpringUtils.getBean(TaskExecuteChainGroup.class).execute(play.getId());
		// 改状态为已经执行
		Play update = new Play();
		update.setId(play.getId());
		update.setRobotSettingsState(1);
		SpringUtils.getBean(PlayMapper.class).updateById(update);
		log.info("更新剧本状态号人设状态 {}", play);
	}

}
