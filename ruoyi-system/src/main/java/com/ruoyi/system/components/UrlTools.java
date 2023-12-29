package com.ruoyi.system.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class UrlTools {
	/*
	 * 通过InputStream将文件读入byte[]数组中，再通过outputStream.write写出byte数组
	 */

	public static InputStream getInputStreamByUrl(String strUrl) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(20 * 1000);
			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtils.copy(conn.getInputStream(), output);
			return new ByteArrayInputStream(output.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}
