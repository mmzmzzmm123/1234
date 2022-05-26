package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.model.credit.BlackRecord;
import com.ruoyi.system.domain.model.credit.CreditResponse;
import com.ruoyi.system.domain.model.credit.CreditToken;
import com.ruoyi.system.domain.model.credit.DishonestOrBlack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataCreditApi {

    private static final Logger log = LoggerFactory.getLogger(DataCreditApi.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 信用三期相关接口的域名
     */
    @Value("${credit.domain}")
    private String domain;

    /**
     * 平台账号
     */
    @Value("${credit.account}")
    private String account;

    /**
     * 密码
     */
    @Value("${credit.password}")
    private String password;

    /**
     * 也称之为platformId
     */
    @Value("${credit.appId}")
    private String appId;

    /**
     * 行业短信账号标识
     */
    @Value("${credit.code}")
    private String code;

    public CreditToken getToken() {

        //TODO 缓存中获取
        String url = domain + "credit/auth/servicelogin";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", account);
        jsonObject.put("password", password);
        jsonObject.put("code", code);
        jsonObject.put("appId", appId);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject);
        ParameterizedTypeReference<CreditResponse<CreditToken>> reference = new ParameterizedTypeReference<CreditResponse<CreditToken>>() {
        };
        ResponseEntity<CreditResponse<CreditToken>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, reference);

        CreditResponse<CreditToken> response = responseEntity.getBody();
        if (!(CreditResponse.SUCCESS == response.getCode())) {
            throwException(response.getMsg(), response.getErrors());
        }
        return response.getData();
    }

    /**
     * 企业黑名单核验接口
     *
     * @param creditCode 统一社会信用代码
     * @return 黑名单记录
     */
    public CreditResponse<BlackRecord> checkIsBlack(String creditCode, int pageSize) {

        String url = domain + "cmpapi/invoke/serviceInvoke/1.0/api/checkIsBlack";

        HttpHeaders headers = getHttpHeadersWithToken();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platformId", appId);
        jsonObject.put("pageSize", pageSize);
        jsonObject.put("creditCode", creditCode);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<CreditResponse<BlackRecord>> reference = new ParameterizedTypeReference<CreditResponse<BlackRecord>>() {
        };

        ResponseEntity<CreditResponse<BlackRecord>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, reference);
        return responseEntity.getBody();
    }

    /**
     * 1.5 失信被执行人核验接口
     *
     * @param creditCode 统一社会信用代
     * @return 失信被执行人记录
     */
    public CreditResponse<BlackRecord> checkIsDishonestExecute(String creditCode, int pageSize) {
        String url = domain + "cmpapi/invoke/serviceInvoke/1.0/api/checkIsDishonestExecute";
        HttpHeaders headers = getHttpHeadersWithToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platformId", appId);
        jsonObject.put("pageSize", pageSize);
        jsonObject.put("CARDNUMBER", creditCode);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<CreditResponse<BlackRecord>> reference = new ParameterizedTypeReference<CreditResponse<BlackRecord>>() {
        };

        ResponseEntity<CreditResponse<BlackRecord>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, reference);
        return responseEntity.getBody();
    }

    public DishonestOrBlack checkBlackOrIsDishonest(String creditCode) {

        DishonestOrBlack dishonestOrBlack = new DishonestOrBlack();

        // 黑名单、失信执行人
        CreditResponse<BlackRecord> blackRecordCreditResponse = checkIsBlack(creditCode, 1);

        if (!(CreditResponse.SUCCESS == blackRecordCreditResponse.getCode())) {
            throwException(blackRecordCreditResponse.getMsg(), blackRecordCreditResponse.getErrors());
        } else {
            if (blackRecordCreditResponse.getData().getColumnShowList().getTotal() > 0) {
                dishonestOrBlack.setBlack(true);
            }
        }
        CreditResponse<BlackRecord> dishonestExecuteResponse = checkIsDishonestExecute(creditCode, 1);

        if (!(CreditResponse.SUCCESS == dishonestExecuteResponse.getCode())) {
            throwException(dishonestExecuteResponse.getMsg(), dishonestExecuteResponse.getErrors());
        } else {
            if (dishonestExecuteResponse.getData().getColumnShowList().getTotal() > 0) {
                dishonestOrBlack.setDishonest(true);
            }
        }

        return dishonestOrBlack;
    }

    private void throwException(String msg, String errors) {
        log.error(errors);
        throw new CustomException(msg, HttpStatus.ERROR);
    }

    private HttpHeaders getHttpHeadersWithToken() {
        CreditToken creditToken = getToken();
        String token = creditToken.getAccess_token();
        String authorization = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorization);
        return headers;
    }


}
