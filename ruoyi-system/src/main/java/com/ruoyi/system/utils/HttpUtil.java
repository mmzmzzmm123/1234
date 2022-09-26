package com.ruoyi.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.ip.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date HttpClient工具类
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "utf-8");

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get请求
     * 请求头信息：Content-Type: application/json
     *
     * @param url
     * @return
     */
    public static String doGetJson(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            request.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), "utf-8");

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 拼接参数
     * get请求
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static String doGet(String url, Map<String, Object> paramMap) {
        StringBuffer params = new StringBuffer();
        if (paramMap != null && paramMap.keySet().size() > 0) {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (value != null && StringUtils.isNotEmpty(value.toString())) {
                    params.append("&" + key + "=" + value.toString());
                }
            }
            return url.endsWith("?") ? doGetJson(url + params.substring(1, params.length())) :
                    doGetJson(url + "?" + params.substring(1, params.length()));
        } else {
            return doGetJson(url);
        }
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params) {

        BufferedReader in = null;
        try {
            // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法  
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            //请求成功
            if (code == 200) {
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), "utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                return sb.toString();
            } else {
                System.out.println("状态码：" + code);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
                logger.error("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * post raw请求方式
     *
     * @param url
     * @param params
     */
    public static String postRaw(String url, String params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            String charSet = "UTF-8";
            StringEntity stringEntity = new StringEntity(params, charSet);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String content = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(content);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Http请求
     *
     * @param httpurl
     * @param params
     * @return
     * @throws Exception
     */
    public static String doHttpPost(String httpurl, Map<String, Object> params) throws Exception {
        URL url = new URL(httpurl);
        //获取httpurlConnection连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置读取超时
        conn.setConnectTimeout(60000);
        //设置读取超时
        conn.setReadTimeout(60000);
        //传送数据
        conn.setDoOutput(true);
        //读取数据
        conn.setDoInput(true);
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置传入参数格式
        conn.setRequestProperty("Content-Type", "application/json");
        //获取输出流
        OutputStream os = conn.getOutputStream();
        //输出数据
        if (params != null) {
            StringBuffer strBuf = new StringBuffer();
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String inputName = (String) entry.getKey();
                String inputValue = (String) entry.getValue();
                if (inputValue == null) {
                    continue;
                }
                strBuf.append(inputName).append("=").append(inputValue);
                if (iter.hasNext()) {
                    strBuf.append("&");
                }
            }
            os.write(strBuf.toString().getBytes());
        }
        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuffer sb = new StringBuffer();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if (null != br) {
            br.close();
        }
        if (null != is) {
            is.close();
        }
        if (null != os) {
            os.close();
        }
        //关闭连接
        conn.disconnect();

        return sb.toString();
    }

    /**
     * 公安接口 接口加鉴权访问
     *
     * @param url
     * @param params
     * @param authorization
     * @return
     * @throws Exception
     */
    public static String doPostByParams(String url, Map<String, Object> params, String authorization) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Basic " + authorization);
        String mimeType = "application/json";
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(JSON.toJSONString(params), mimeType, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                logger.info(jsonString);
                return jsonString;
            } else {
                logger.error("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 公安接口 接口加鉴权访问
     * 返回参数使用utf-8 解决中文乱码问题
     *
     * @param httpUrl
     * @param params
     * @param authorization
     * @return
     * @throws Exception
     */
    public static String doPostByParamsUtf8(String httpUrl, Map<String, Object> params, String authorization) throws Exception {
        URL url = new URL(httpUrl);
//        Header(httpurl);
        //获取httpurlConnection连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置读取超时
        conn.setConnectTimeout(60000);
        //设置读取超时
        conn.setReadTimeout(60000);
        //传送数据
        conn.setDoOutput(true);
        //读取数据
        conn.setDoInput(true);
        //设置请求方式
        conn.setRequestMethod("POST");
        //设置传入参数格式
        conn.setRequestProperty("Content-Type", "application/json");
        // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
        conn.setRequestProperty("Authorization", "Basic " + authorization);
        //获取输出流
        OutputStream os = conn.getOutputStream();
        //输出数据
        if (params != null) {
            os.write(JSONObject.toJSONString(params).getBytes());
        }

        /***************************打印Header信息*****************************/
        Map<String, List<String>> map = conn.getHeaderFields();
        System.out.println("显示响应Header信息");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() +
                    " ,Value : " + entry.getValue());
        }
        //使用key获得响应Header;
        List<String> server = map.get("Server");
        if (server == null) {
            System.out.println("Key 'Server' is not found!");
        } else {
            for (String values : server) {
                System.out.println(values);
            }
        }

        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if (null != br) {
            br.close();
        }
        if (null != is) {
            is.close();
        }
        if (null != os) {
            os.close();
        }
        //关闭连接
        conn.disconnect();
        return sb.toString();
    }

    public static void header(String url) {
        try {
            URL obj = new URL(url);
            URLConnection conn = obj.openConnection();
            Map<String, List<String>> map = conn.getHeaderFields();
            System.out.println("显示响应Header信息\n");
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }
            System.out.println("\n使用key获得响应Header信息 \n");
            List<String> server = map.get("Server");
            if (server == null) {
                System.out.println("Key 'Server' is not found!");
            } else {
                for (String values : server) {
                    System.out.println(values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendGet(String httpUrl, String authorization) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置连接方式
        conn.setRequestMethod("GET");
        //设置主机连接时间超时时间3000毫秒
        conn.setConnectTimeout(1000 * 15);
        //设置读取远程返回数据的时间3000毫秒
        conn.setReadTimeout(1000 * 15);

        if (!authorization.isEmpty()) {
            //设置传入参数格式
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置鉴权信息
            conn.setRequestProperty("Authorization", authorization);
        }

        //设置请求头部
        // 终端IP
        conn.setRequestProperty("datauserIp", IpUtils.getHostIp());
        // 终端Mac
        conn.setRequestProperty("datauserMac", LocalMACUtil.getLocalMac());
        conn.setRequestProperty("datauserName", ConfigInfo.OPERATOR);
        conn.setRequestProperty("datauserSfzhm", ConfigInfo.IDCARD);

        //发送请求
        conn.connect();
        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        //接收读取数据
        StringBuffer sb = new StringBuffer();

        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if (null != br) {
            br.close();
        }
        if (null != is) {
            is.close();
        }
        //关闭连接
        conn.disconnect();
        return sb.toString();
    }

    /**
     *
     * @param httpUrl
     * @param authorization
     * @return
     * @throws IOException
     */
    public static String sendGet2(String httpUrl, String authorization) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置连接方式
        conn.setRequestMethod("GET");
        //设置主机连接时间超时时间3000毫秒
        conn.setConnectTimeout(1000 * 15);
        //设置读取远程返回数据的时间3000毫秒
        conn.setReadTimeout(1000 * 15);

        if (!authorization.isEmpty()) {
            //设置传入参数格式
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 设置鉴权信息
            conn.setRequestProperty("Authorization", authorization);
        }
        //发送请求
        conn.connect();
        //获取输入流
        InputStream is = conn.getInputStream();
        //封装输入流
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //接收读取数据
        StringBuffer sb = new StringBuffer();

        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }
        if (null != br) {
            br.close();
        }
        if (null != is) {
            is.close();
        }
        //关闭连接
        conn.disconnect();
        return sb.toString();
    }

    public static String httpClientPost(String url,String authorization,Map<String,Object> map) throws Exception {
        // 获取默认的请求客户端
        CloseableHttpClient client = HttpClients.createDefault();
        // 通过HttpPost来发送post请求
        HttpPost httpPost = new HttpPost(url);
        /*
         * post带参数开始
         */
        // 第三步：构造list集合，往里面丢数据
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String key : map.keySet()) {
            String value =String.valueOf(map.get(key));
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
            list.add(basicNameValuePair);
        }
        // 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
        // 第一步：通过setEntity 将我们的entity对象传递过去
        httpPost.setEntity(formEntity);
        /*
         * post带参数结束
         */
        // HttpEntity
        // 是一个中间的桥梁，在httpClient里面，是连接我们的请求与响应的一个中间桥梁，所有的请求参数都是通过HttpEntity携带过去的
        // 通过client来执行请求，获取一个响应结果
        CloseableHttpResponse response = client.execute(httpPost);
        response.setHeader("Authorization",authorization);
        HttpEntity entity = response.getEntity();
        String str = EntityUtils.toString(entity, "UTF-8");
        System.out.println(str);
        // 关闭
        response.close();
        return str;
    }

}

