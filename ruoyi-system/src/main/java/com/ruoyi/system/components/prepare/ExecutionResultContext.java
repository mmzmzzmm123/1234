package com.ruoyi.system.components.prepare;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExecutionResultContext {

	private final ExecutionParamContext paramContext;

	private boolean succeed = false;

	private String errMsg;

	private boolean needWait = false;

	public boolean continued() {
		return succeed && !needWait;
	}

	/***
	 * 同步
	 * 
	 * @param paramContext
	 * @return
	 */
	public static ExecutionResultContext buildSync(ExecutionParamContext paramContext) {
		ExecutionResultContext ctx = new ExecutionResultContext(paramContext);
		ctx.setSucceed(true);
		ctx.setNeedWait(false);
		return ctx;
	}

	public static ExecutionResultContext buildAsync(ExecutionParamContext paramContext) {
		ExecutionResultContext ctx = new ExecutionResultContext(paramContext);
		ctx.setSucceed(true);
		ctx.setNeedWait(true);
		return ctx;
	}

	public static ExecutionResultContext buildError(ExecutionParamContext paramContext, String errMsg) {
		ExecutionResultContext ctx = new ExecutionResultContext(paramContext);
		ctx.setSucceed(false);
		ctx.setErrMsg(errMsg);
		return ctx;
	}

}
