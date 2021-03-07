package com.ruoyi.web.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2的接口配置
 * 
 * @author ruoyi
 */
@Configuration
@ConfigurationProperties(prefix = "business.config.ding-talk")
public class DingTalkConfig {
	/**
	 * agentId
	 */
	private String agentId;
	/**
	 * key
	 */
	private String appKey;
	/**
	 * 秘钥
	 */
	private String appSecret;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
