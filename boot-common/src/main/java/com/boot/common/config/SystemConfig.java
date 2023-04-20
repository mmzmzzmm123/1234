package com.boot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sys.config")
@Data
public class SystemConfig {
	private String filePath;
	private String pythonFilePath;
	private String apiKey;
	private String pythonUrl;
	private String pythonToken;
	private String proxyIp;
	private int proxyPort;
	private String collectionName;
	private String milvusIp;
	private int topk;
	private int overTime;
	private String vecModelPath;
	private int milvusPort;
	private int maxTokens;
	private double score;
	private int maxFileLength;
	private String staticPath;
	private String sysPrompt;
	private String userPromptBefore;
	private String userPromptAfter;
	private String templatePath;
}
