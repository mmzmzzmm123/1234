package com.ruoyi.system.components.movie;

public interface CallBackProcessor {

	void onMessageSuccess(String opt);

	void onMessageFailure(String opt, String errMsg);

}
