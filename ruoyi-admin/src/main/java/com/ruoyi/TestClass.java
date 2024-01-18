package com.ruoyi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.prepare.TaskExecuteChainGroup;

@Component
public class TestClass implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		System.setProperty("env", "local") ;
		SpringUtils.getBean(TaskExecuteChainGroup.class).execute("1747167826725740546");
		
	}

}
