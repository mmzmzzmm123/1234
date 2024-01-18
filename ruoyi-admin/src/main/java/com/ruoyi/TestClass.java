package com.ruoyi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.callback.dto.CalledDTOThreadLocal;
import com.ruoyi.system.callback.processor.TgRobotProcessor;
import com.ruoyi.system.components.prepare.TaskExecuteChainGroup;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer;

@Component
public class TestClass implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		System.setProperty("env", "local") ;
//		SpringUtils.getBean(TaskExecuteChainGroup.class).execute("1747167826725740546");
//		
//		
//		//模拟管理员
//		CalledDTO d = new CalledDTO();
//		d.setResultCode(0);
//		d.setOptSerNo("");
//		CalledDTOThreadLocal.set(d);
//		new TgRobotProcessor().called1100910053(null);
//		
//	    CalledDTO root = CalledDTOThreadLocal.getAndRemove();
//		
//		
//		
//		
//		SpringUtils.getBean(MultipackLogContainer.class).jobScan();
	}

}
