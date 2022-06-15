package com.ruoyi.common.utils.http;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTP 请求工具类
 */
@Slf4j
public class HttpUtil {

    private HttpUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static RequestConfig requestMinConfig;
    private static final int MAX_TIMEOUT = 3 * 60 * 1000;
    private static final int MIN_CONFIG_MAX_TIMEOUT = 3 * 60 * 1000;
    private static final String charset = "UTF-8";

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        connMgr.setValidateAfterInactivity(30000);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();


        RequestConfig.Builder minConfigBuilder = RequestConfig.custom();
        // 设置连接超时
        minConfigBuilder.setConnectTimeout(MIN_CONFIG_MAX_TIMEOUT);
        // 设置读取超时
        minConfigBuilder.setSocketTimeout(MIN_CONFIG_MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        minConfigBuilder.setConnectionRequestTimeout(MIN_CONFIG_MAX_TIMEOUT);
        requestMinConfig = minConfigBuilder.build();
    }

    /**
     * get请求
     */
    public static String doGet(String url, Map<String, Object> pathParamMap,
                               Map<String, Object> queryParamMap, Map<String, Object> headerParamMap) throws IOException {
        HttpResponse response = null;
        String apiUrl = url;
        String result = null;
        try {
            apiUrl = processPathParam(apiUrl, pathParamMap);
            apiUrl = processQueryParam(apiUrl, queryParamMap);
            CloseableHttpClient httpclient = getHttpClient(url.startsWith("https") ? true : false);
            HttpGet httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(requestMinConfig);
            processHeaderParam(httpGet, headerParamMap);
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error("get={}请求时错误，pathParamMap={},queryParamMap={},headerParamMap={}，状态码={}", url,
                        pathParamMap, queryParamMap, headerParamMap, statusCode);

                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            if (result.length() > 500) {
                LOGGER.info("get={}请求成功，pathParamMap={},queryParamMap={},headerParamMap={}，输出信息={}", url,
                        pathParamMap, queryParamMap, headerParamMap, result.substring(0, 500) + ".......");
            } else {
                LOGGER.info("get={}请求成功，pathParamMap={},queryParamMap={},headerParamMap={}，输出信息={}", url,
                        pathParamMap, queryParamMap, headerParamMap, result);
            }
        } catch (IOException e) {

            LOGGER.error("get={}请求时错误，pathParamMap={},queryParamMap={},headerParamMap={}，错误信息={}", url,
                    pathParamMap, queryParamMap, headerParamMap, e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOGGER.error(
                            "get请求url={}关闭时出现错误，pathParamMap={},queryParamMap={},headerParamMap={}，错误信息={}", url,
                            pathParamMap, queryParamMap, headerParamMap, e);
                }
            }
        }
        return result;
    }


    /**
     * post请求
     */
    public static String doPost(String url, Map<String, Object> pathParamMap,
                                Map<String, Object> queryParamMap, Map<String, Object> bodyParamMap,
                                Map<String, Object> headerParamMap, boolean isJson) throws IOException {
        CloseableHttpResponse response = null;
        String apiUrl = url;
        String result = null;

        try {
            apiUrl = processPathParam(apiUrl, pathParamMap);
            apiUrl = processQueryParam(apiUrl, queryParamMap);
            CloseableHttpClient httpClient = getHttpClient(url.startsWith("https") ? true : false);
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            processHeaderParam(httpPost, headerParamMap);
            if (isJson) {
                processBodyParamJson(httpPost, bodyParamMap);
            } else {
                processBodyParamNormal(httpPost, bodyParamMap);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error(
                        "post请求url={}请求时错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，状态码={}",
                        url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, statusCode);


                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    LOGGER.info(
                            "post请求url={}请求成功，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，输出信息={}",
                            url, pathParamMap, queryParamMap, JSON.toJSONString (bodyParamMap), headerParamMap, EntityUtils.toString(entity, charset));
                }

                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            LOGGER.info(
                    "post请求url={}请求成功，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，输出信息={}",
                    url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, result);
        } catch (IOException e) {
            LOGGER.error(
                    "post请求url={}请求时错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，错误信息={}",
                    url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOGGER.error(
                            "post请求url={}关闭时出现错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，错误信息={}",
                            url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, e);
                }
            }
        }
        return result;
    }


    /**
     * put请求
     */
    public static String doPut(String url, Map<String, Object> pathParamMap,
                               Map<String, Object> queryParamMap, Map<String, Object> bodyParamMap,
                               Map<String, Object> headerParamMap, boolean isJson) throws IOException {

        CloseableHttpResponse response = null;
        String apiUrl = url;
        String result = null;

        try {
            apiUrl = processPathParam(apiUrl, pathParamMap);
            apiUrl = processQueryParam(apiUrl, queryParamMap);
            CloseableHttpClient httpClient = getHttpClient(url.startsWith("https") ? true : false);
            HttpPut httpPut = new HttpPut(apiUrl);
            httpPut.setConfig(requestConfig);
            processHeaderParam(httpPut, headerParamMap);
            if (isJson) {
                processBodyParamJson(httpPut, bodyParamMap);
            } else {
                processBodyParamNormal(httpPut, bodyParamMap);
            }
            response = httpClient.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error(
                        "put请求url={}请求时错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，状态码={}",
                        url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, statusCode);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    LOGGER.info(
                            "post请求url={}请求成功，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，输出信息={}",
                            url, pathParamMap, queryParamMap, JSON.toJSONString(bodyParamMap), headerParamMap, EntityUtils.toString(entity, charset));
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            LOGGER.info(
                    "put请求url={}请求成功，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，输出信息={}",
                    url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, result);
        } catch (IOException e) {
            LOGGER.error(
                    "put请求url={}请求时错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，错误信息={}",
                    url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOGGER.error(
                            "put请求url={}关闭时出现错误，pathParamMap={},queryParamMap={},bodyParamMap={},headerParamMap={}，错误信息={}",
                            url, pathParamMap, queryParamMap, bodyParamMap, headerParamMap, e);
                }
            }
        }
        return result;
    }


    private static String processQueryParam(String apiUrl, Map<String, Object> paramMap)
            throws UnsupportedEncodingException {
        if (null == paramMap) {
            return apiUrl;
        }

        StringBuffer param = new StringBuffer();

        boolean checkHasQuery = apiUrl.indexOf("?") > -1 ? true : false;

        int i = 0;
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (null == entry.getValue()) {
                continue;
            }

            if (!checkHasQuery && i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(covertString(entry.getValue()), charset));
            i++;
        }
        apiUrl += param;
        return apiUrl;
    }

    private static String processPathParam(String apiUrl, Map<String, Object> paramMap) {
        if (null == paramMap) {
            return apiUrl;
        }

        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (null == entry.getValue()) {
                continue;
            }
            apiUrl = apiUrl.replace("{" + entry.getKey() + "}", covertString(entry.getValue()));
        }
        return apiUrl;
    }

    private static void processHeaderParam(HttpRequestBase httpRequest,
                                           Map<String, Object> paramMap) {
        if (null == paramMap) {
            return;
        }

        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (null == entry.getValue()) {
                continue;
            }
            httpRequest.setHeader(entry.getKey(), covertString(entry.getValue()));
        }
    }

    private static void processBodyParamNormal(HttpRequestBase httpRequest,
                                               Map<String, Object> paramMap) {
        if (null == paramMap) {
            return;
        }

        if (!(httpRequest instanceof HttpPost) && !(httpRequest instanceof HttpPut)) {
            return;
        }

        List<NameValuePair> nameValuePairs = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), covertString(entry
                    .getValue()));
            nameValuePairs.add(pair);
        }

        if (null == nameValuePairs || nameValuePairs.isEmpty()) {
            return;
        }

        if (httpRequest instanceof HttpPost) {
            ((HttpPost) httpRequest)
                    .setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.forName(charset)));
        }

        if (httpRequest instanceof HttpPut) {
            ((HttpPut) httpRequest)
                    .setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.forName(charset)));
        }
    }

    private static void processBodyParamJson(HttpRequestBase httpRequest,
                                             Map<String, Object> paramMap) {
        if (null == paramMap) {
            return;
        }

        if (!(httpRequest instanceof HttpPost) && !(httpRequest instanceof HttpPut)) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                processBodyParamJson(httpRequest, (Map<String, Object>) entry.getValue());
            }
            jsonObject.put(entry.getKey(), entry.getValue());
        }

        if (jsonObject.isEmpty()) {
            return;
        }

        StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(), charset);//解决中文乱码问题
        stringEntity.setContentEncoding(charset);
        stringEntity.setContentType("application/json");

        if (httpRequest instanceof HttpPost) {
            ((HttpPost) httpRequest).setEntity(stringEntity);
        }

        if (httpRequest instanceof HttpPut) {
            ((HttpPut) httpRequest).setEntity(stringEntity);
        }
    }

    private static CloseableHttpClient getHttpClient(boolean isHttps) {
        try {
            if (isHttps) {
                return createSSLClientDefault();
            } else {
                return HttpClients.custom().setConnectionManager(connMgr)
                        .setDefaultRequestConfig(requestConfig).build();
            }
        } catch (Exception e) {
            LOGGER.error("创建http线程失败", e);
        }
        return HttpClients.createDefault();
    }

    private static String covertString(Object valueObj) {
        String value;
        if (valueObj instanceof String) {
            value = valueObj.toString();
        } else {
            value = JSON.toJSONString(valueObj);
        }
        return value;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * <b>Description.:</b><br>
     * <b>Author:jianb.jiang</b>
     * <br><b>Date:</b> 2019年3月26日 下午3:00:42
     *
     * @param request HttpServletRequest
     * @return bodyString
     */
    public static String getBodyString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName("UTF-8")));

            char[] bodyCharBuffer = new char[1024];
            int len = 0;
            while ((len = reader.read(bodyCharBuffer)) != -1) {
                sb.append(new String(bodyCharBuffer, 0, len));
            }
        } catch (Exception e) {
            log.error("从request获取body出错了:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error!", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("reader close error!", e);
                }
            }
        }
        return sb.toString();
    }


    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");
            String query;
            if (urlParts.length > 1) {
                query = urlParts[1];
            } else {
                query = url;
            }


            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8");
                }

                List<String> values = params.get(key);
                if (values == null) {
                    values = new ArrayList<String>();
                    params.put(key, values);
                }
                values.add(value);
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }


    public static void main(String[] args) {
        Map result = getQueryParams("busiType=2&cameraIds=1&cameraIds=2&token=111");
        System.out.println(JSON.toJSONString(result));
    }
}
