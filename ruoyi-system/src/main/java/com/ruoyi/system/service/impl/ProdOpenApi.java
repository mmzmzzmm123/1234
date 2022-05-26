package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.model.FinanceProductInfo;
import com.ruoyi.system.domain.model.credit.FinanceProductResponse;
import com.ruoyi.system.utils.OpenApiAuthUtil;
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

import java.util.Objects;

@Service
public class ProdOpenApi {

    private static final Logger log = LoggerFactory.getLogger(ProdOpenApi.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 金融产品相关接口的域名
     */
    @Value("${bankapi.domain}")
    private String domain;

    /**
     * appKey
     */
    @Value("${bankapi.appKey}")
    private String appKey;

    /**
     * appSecret
     */
    @Value("${bankapi.appSecret}")
    private String appSecret;


    /**
     * 信贷直通车金融产品列表查询
     *
     * @return 黑名单记录
     */
    public JSONObject getFinanceProductList(String pageNum, String pageSize) {
        JSONObject returnObject = new JSONObject();

        String url = domain + "xdztc/financeProduct/list?pageNum=" + pageNum + "&pageSize=" + pageSize;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);
        ;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum", pageNum);
        jsonObject.put("pageSize", pageSize);
//        jsonObject.put("ID", pageNum);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<FinanceProductInfo>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<FinanceProductInfo>>() {
        };

        ResponseEntity<FinanceProductResponse<FinanceProductInfo>> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }

    // 获取接口返回信息
    private JSONObject getResult(FinanceProductResponse response) {
        JSONObject returnObject = new JSONObject();
        if (Objects.equals(200, response.getCode())) {
            returnObject.put("data", response.getData());
            returnObject.put("rows", response.getRows());
            returnObject.put("total", response.getTotal());

        } else {
            throw new CustomException(response.getMsg(), HttpStatus.ERROR);
        }
        return returnObject;
    }


}
