package com.ruoyi.common.obs;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Obs {
	private static final Map<String, ObsClient> obsCache = new ConcurrentHashMap<>();

	public static String upload(String ak, String sk, String bulkName, String endPoint, File file, String downloadUrl) {
		ObsClient client = obsCache.get(ak + "_" + sk);
		if (client == null) {
			client = new ObsClient(ak, sk, endPoint);
		}
		PutObjectResult r = client.putObject(bulkName, getFileKey(file.getName(), bulkName), file);
		return downloadUrl + r.getObjectKey();
	}

	public static String upload(String ak, String sk, String bulkName, String endPoint, String fileName,
			InputStream stream, String downloadUrl) {
		ObsClient client = obsCache.get(ak + "_" + sk);
		if (client == null) {
			client = new ObsClient(ak, sk, endPoint);
		}
		PutObjectResult r = client.putObject(bulkName, getFileKey(fileName, bulkName), stream);
		return downloadUrl + r.getObjectKey();
	}

	private static String getFileKey(String fileName, String bulkName) {
		final String lastStr = StringUtils.substringAfterLast(fileName, ".");

		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		String idStr = IdWorker.getIdStr();
		String fileKeyName = idStr.substring(idStr.length() - 16)
				+ MD5Utils.getMD5(fileName.getBytes()).substring(0, 10);
		sb.append("files/");
		if (!StringUtils.isEmpty(bulkName)) {
			sb.append("obs_" + bulkName).append("/");
		}
		sb.append(DateUtils.parseDateToStr("yyyy",date)).append("/")
				.append(DateUtils.parseDateToStr("MM",date)).append("/")
				.append(DateUtils.parseDateToStr("dd",date)).append("/").append(fileKeyName);
		if (!StringUtils.isEmpty(lastStr)) {
			sb.append(".").append(lastStr);
		}
		return sb.toString();
	}

}
