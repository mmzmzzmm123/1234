package com.ruoyi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.dto.Called1100860002DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.callback.dto.CalledDTOThreadLocal;
import com.ruoyi.system.callback.processor.TgRobotProcessor;
import com.ruoyi.system.components.prepare.TaskExecuteChainGroup;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer;

@Component
public class TestClass implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.setProperty("env", "local") ;
		//SpringUtils.getBean(TaskExecuteChainGroup.class).execute("1747167826725740546");
		//模拟管理员
		CalledDTO d = new CalledDTO();
		d.setResultCode(0);
		d.setOptSerNo("20240118154942445613411823005");
		CalledDTOThreadLocal.set(d);
		new TgRobotProcessor().called1100910016(null);
		
		
		d.setResultCode(0);
		d.setOptSerNo("dbfc1f1c-8261-4bf5-a9dc-d3af0b4ca522");
		CalledDTOThreadLocal.set(d);
		new TgRobotProcessor().called1100910033(null);
		
		
		d.setResultCode(0);
		d.setOptSerNo("20240118154943223647610037282");
		CalledDTOThreadLocal.set(d);
		
		Called1100860002DTO dw = new Called1100860002DTO();
		dw.setAccessHash("xxxx1");
		new TgRobotProcessor().called1100860002(dw);
		
		
		
		// 管理员
		d.setResultCode(0);
		d.setOptSerNo("20240118155953993613411841698");
		CalledDTOThreadLocal.set(d);
		new TgRobotProcessor().called1100910053(null);
		
		
		SpringUtils.getBean(MultipackLogContainer.class).jobScan();
	}

}
