package vip.carpool.api.gateway.controller.wechat;

import cn.hutool.core.util.XmlUtil;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import vip.carpool.api.gateway.common.api.ApiCode;
import vip.carpool.api.gateway.common.api.ApiResult;
import vip.carpool.api.gateway.common.exception.BadRequestException;
import vip.carpool.api.gateway.common.utils.XmlMapUtil;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wechat")
@Api(value = "企业付款到用户零钱", tags = "企业付款到用户零钱", description = "企业付款到用户零钱")
public class WeChatPayUserController {

    private final static String ACTIVE_TEST = "test";
    private final static String ACTIVE_PROD = "prod";
    private static final String CONFIG_FILENAME_DEV = "client_cert_dev.p12";
    private static final String CONFIG_FILENAME_TEST = "client_cert_test.p12";
    private static final String CONFIG_FILENAME_PROD = "client_cert_prod.p12";

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${wx.client.cert.path:}")
    private String clientCertPath;
    @Value("${mmpaymkttransfers}")
    private String mmpaymkttransfers;

    @PostMapping("/payUser")
    public ApiResult payUser(String xmlData, String certificateKey) {
        if (StringUtils.isBlank(xmlData) || StringUtils.isBlank(certificateKey)) {
            throw new BadRequestException("付款入参为空，请检查：xml， mchId");
        }
        String path;
        if (env.equals(ACTIVE_TEST)) {
            path = clientCertPath + CONFIG_FILENAME_TEST;
        } else if (env.equals(ACTIVE_PROD)) {
            path = clientCertPath + CONFIG_FILENAME_PROD;
        } else {
            path = CONFIG_FILENAME_DEV;
        }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        try {
            //指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            ;
            //指定PKCS12的密码(商户ID)
            keyStore.load(inputStream, certificateKey.toCharArray());
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certificateKey.toCharArray()).build();
            //指定TLS版本, Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            //设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost(mmpaymkttransfers);
            //这里要设置编码，不然xml中有中文的话会提示签名失败或者展示乱码
            httppost.addHeader("Content-Type", "text/xml");
            StringEntity se = new StringEntity(xmlData, "UTF-8");
            httppost.setEntity(se);
            CloseableHttpResponse responseEntry = httpclient.execute(httppost);
            Document document = XmlUtil.readXML(responseEntry.getEntity().getContent());
            String result = doc2FormatString(document);
            Map<String, String> resultMap = XmlMapUtil.xmlToMap(result);
            String returnCode = resultMap.get("return_code");
            String resultCode = resultMap.get("result_code");
            if ("FAIL".equals(returnCode) || "FAIL".equals(resultCode)) {
                return ApiResult.fail(ApiCode.FAIL, resultMap);
            }
            resultMap.remove("mch_appid");
            resultMap.remove("mchid");
            return ApiResult.ok(resultMap);
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new BadRequestException("提取证书失败，请检查密钥是否正确：" + e.getMessage());
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new BadRequestException("提取证书异常，请检查证书是否正确：" + e.getMessage());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            throw new BadRequestException("提取证书失败：" + e.getMessage());
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            throw new BadRequestException("提取证书失败，证书已损坏：" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BadRequestException("提取证书异常，请检查证书是否为PKCS12格式：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("读取证书异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("xml转map异常：" + e.getMessage());
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String doc2FormatString(Document doc) {
        String docString = "";
        if (doc != null) {
            StringWriter stringWriter = new StringWriter();
            try {
                OutputFormat format = new OutputFormat(doc, "UTF-8", true);
                //format.setIndenting(true);//设置是否缩进，默认为true
                //format.setIndent(4);//设置缩进字符数
                //format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false
                //format.setLineWidth(500);//设置行宽度
                XMLSerializer serializer = new XMLSerializer(stringWriter, format);
                serializer.asDOMSerializer();
                serializer.serialize(doc);
                docString = stringWriter.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stringWriter != null) {
                    try {
                        stringWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log.info("XML内容：" + docString);
        return docString;
    }


}
