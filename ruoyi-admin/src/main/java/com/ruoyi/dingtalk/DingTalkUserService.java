package com.ruoyi.dingtalk;

import org.springframework.stereotype.Component;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;

/**
 * 钉钉用户接口
 * 
 * @author YW
 *
 */
@Component
public class DingTalkUserService {

	public OapiUserGetResponse getUserInfo(String authCode, String accessToken) throws ApiException {
		DingTalkClient dingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(authCode);
		request.setHttpMethod("GET");
		OapiUserGetuserinfoResponse response = dingTalkClient.execute(request, accessToken);
		String userId = response.getUserid();
		return getUserInfoByUserId(accessToken, userId);
	}

	private OapiUserGetResponse getUserInfoByUserId(String accessToken, String userId) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userId);
		request.setHttpMethod("GET");
		OapiUserGetResponse response = client.execute(request, accessToken);
		return response;
	}

}
