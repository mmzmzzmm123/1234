package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.model.*;
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
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

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
     * @return 信贷直通车金融产品列表
     */
    public JSONObject getFinanceProductList(FinanceProductQuery query) {
        JSONObject returnObject = new JSONObject();
        int pageNum = query.getPageNum();
        int pageSize = query.getPageNum();

        String url = domain + "xdztc/financeProduct/list?pageNum=" + pageNum + "&pageSize=" + pageSize;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);
        ;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum", pageNum);
        jsonObject.put("pageSize", pageSize);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<FinanceProductInfo>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<FinanceProductInfo>>() {
                };

        ResponseEntity<FinanceProductResponse<FinanceProductInfo>> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }


    /**
     * 信贷直通车热门金融产品列表查询
     *
     * @return 信贷直通车热门金融产品列表
     */
    public JSONObject getFinanceProductHotList(FinanceProductQuery query) {

        String url = domain + "xdztc/financeProduct/hotList";

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);
        ;

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(query));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<FinanceHotProductInfo>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<FinanceHotProductInfo>>() {
                };

        ResponseEntity<FinanceProductResponse<FinanceHotProductInfo>> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }


    /**
     * 信贷直通车字典查询
     *
     * @return 信贷直通车字典
     */
    public JSONObject getDictByDictType(String dictType) {

        String url = domain + "xdztc/dict?dictType=" + dictType;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);
        ;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dictType", dictType);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<FinanceProductDictInfo>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<FinanceProductDictInfo>>() {
                };

        ResponseEntity<FinanceProductResponse<FinanceProductDictInfo>> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }


    /**
     * 信贷直通车金融产品申请
     *
     * @return 申请结果
     */
    public JSONObject financeProductApply(FinanceProductApply apply) {

        String url = domain + "xdztc/financeProductApply/submit";

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(apply));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse> reference =
                new ParameterizedTypeReference<FinanceProductResponse>() {
                };

        ResponseEntity<FinanceProductResponse> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }


    /**
     * 企业入驻
     *
     * @return 入驻结果
     */
    public JSONObject comUserReg(ComUserRegister register) {

        String url = domain + "xdztc/comUserReg";

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        log.debug("req json1:{}", JSON.toJSONString(register));

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(register));

        log.debug("req json:{}", jsonObject);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse> reference =
                new ParameterizedTypeReference<FinanceProductResponse>() {
                };

        ResponseEntity<FinanceProductResponse> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }

    // 获取接口返回信息
    private JSONObject getResult(FinanceProductResponse response) {
        JSONObject returnObject = new JSONObject();
        log.info("original response: {}",response);
        if (Objects.equals(200, response.getCode())) {
            if (!Objects.isNull(response.getData())) {
                returnObject.put("data", response.getData());
            }
            if (!Objects.isNull(response.getRows())) {
                returnObject.put("rows", response.getRows());
                returnObject.put("total", response.getTotal());
            }

        } else {
            throw new CustomException(response.getMsg(), HttpStatus.ERROR);
        }
        log.debug("return response data:{}",returnObject);
        return returnObject;
    }


}
