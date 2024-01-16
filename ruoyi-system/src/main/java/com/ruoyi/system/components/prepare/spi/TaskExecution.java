package com.ruoyi.system.components.prepare.spi;

import com.ruoyi.system.components.prepare.ExecutionParamContext;
import com.ruoyi.system.components.prepare.ExecutionResultContext;

public interface TaskExecution {

	ExecutionResultContext doExecute(ExecutionParamContext context);

}
