package com.ruoyi.system.components;

import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.utils.App;

@Configuration
public class AppInitializer {

	public App initApp() {
		return new App();
	}

}
