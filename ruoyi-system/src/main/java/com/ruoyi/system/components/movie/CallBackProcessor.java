package com.ruoyi.system.components.movie;

public interface CallBackProcessor {

	void onMessageSuccess(String opt, SendMsgOptTempRedis.SendMsgOptTempEntry entry);

	void onMessageFailure(String opt, String errMsg, SendMsgOptTempRedis.SendMsgOptTempEntry entry);

}
