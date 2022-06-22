package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.system.domain.DataCompanyLoan;
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

import javax.validation.constraints.NotNull;
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
        int pageNum = query.getPageNum();
        int pageSize = query.getPageNum();

        String url = domain + "xdztc/financeProduct/list?pageNum=" + pageNum + "&pageSize=" + pageSize;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(query));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<JSONObject>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<JSONObject>>() {
                };

        ResponseEntity<FinanceProductResponse<JSONObject>> responseEntity = restTemplate.exchange(url,
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

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(query));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<FinanceProductResponse<JSONObject>> reference =
                new ParameterizedTypeReference<FinanceProductResponse<JSONObject>>() {
                };

        ResponseEntity<FinanceProductResponse<JSONObject>> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return getResult(responseEntity.getBody());
    }

    /**
     * 信贷直通车金融产品详情查询
     *
     * @return 信贷直通车金融产品详情查询
     */
    public AjaxResult getFinanceProductInfo(FinanceProductQuery query) {
        Long id = query.getId();
        String url = domain + "xdztc/getDetailById?id="+id;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(query));

        HttpEntity<AjaxResult> httpEntity = new HttpEntity<>(null, headers);
        ParameterizedTypeReference<AjaxResult> reference =
                new ParameterizedTypeReference<AjaxResult>() {
                };
        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, httpEntity, reference);
        log.info(String.valueOf(responseEntity.getBody()));
        return responseEntity.getBody();
    }


    /**
     * 信贷直通车字典查询
     *
     * @return 信贷直通车字典
     */
    public AjaxResult getDictByDictType(String dictType) {

        String url = domain + "xdztc/dict?dictType=" + dictType;

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dictType", dictType);

        HttpEntity<AjaxResult> httpEntity = new HttpEntity<>(null, headers);
        ParameterizedTypeReference<AjaxResult> reference =
                new ParameterizedTypeReference<AjaxResult>() {
                };

        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, httpEntity, reference);

        return responseEntity.getBody();
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
    public AjaxResult comUserReg(ComUserRegister register) {

        String url = domain + "xdztc/comUserReg";

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        log.debug("req json1:{}", JSON.toJSONString(register));

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(register));

        log.debug("req json:{}", jsonObject);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<AjaxResult> reference =
                new ParameterizedTypeReference<AjaxResult>() {
                };

        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return responseEntity.getBody();
    }

    /**
     * 企业入驻
     * @param userJsonObject 获取到的闽政通用户信息对象
     * @return
     */
    public AjaxResult comUserReg(JSONObject userJsonObject) {

        ComUserRegister register = new ComUserRegister();
        register.setLEGALPERSON_TYPE(userJsonObject.getString("LEGALPERSON_TYPE"));//法人类型
        register.setUSER_MOBILE(userJsonObject.getString("USER_MOBILE"));
        register.setUSER_ID(userJsonObject.getString("USER_ID"));
        register.setUSER_AUTHLEVEL(userJsonObject.getLong("USER_AUTHLEVEL"));
        register.setUSER_TYPE(userJsonObject.getString("USER_TYPE"));
        register.setLEGALPERSON_IDCARD(userJsonObject.getString("LEGALPERSON_IDCARD"));
        register.setCARD_TYPE(userJsonObject.getString("CARD_TYPE"));
        register.setLEGALPERSON_NAME(userJsonObject.getString("LEGALPERSON_NAME"));
        register.setLEGALPERSON_JBRNAME(userJsonObject.getString("LEGALPERSON_JBRNAME"));
        register.setUSER_NAME(userJsonObject.getString("USER_NAME"));
        register.setUSER_PHOTOURL(userJsonObject.getString("USER_PHOTOURL"));
        register.setUSER_IDCARD(userJsonObject.getString("USER_IDCARD"));
        register.setLEGALPERSON_JBRIDCARD(userJsonObject.getString("LEGALPERSON_JBRIDCARD"));
        register.setLEGAL_KEYID(userJsonObject.getString("LEGAL_KEYID"));
        return this.comUserReg(register);
    }

    /**
     * 推送贷款数据到业务方系统中
     * @param loan 银行贷款数据
     * @return
     */
    public AjaxResult pushCompanyLoan(DataCompanyLoan loan) {

        String url = domain + "xdztc/push";

        HttpHeaders headers = OpenApiAuthUtil.generateAuthHeaders(appKey, appSecret);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(loan));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, headers);
        ParameterizedTypeReference<AjaxResult> reference =
                new ParameterizedTypeReference<AjaxResult>() {
                };

        ResponseEntity<AjaxResult> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, httpEntity, reference);

        return responseEntity.getBody();
    }

    // 获取接口返回信息
    private JSONObject getResult(FinanceProductResponse response) {
        JSONObject returnObject = new JSONObject();
        log.info("original response: {}",response);
        if(response == null || !Objects.equals(200, response.getCode())){
            throw new CustomException(response == null?"接口返回异常":response.getMsg(), HttpStatus.ERROR);
        }else{
            if (!Objects.isNull(response.getData())) {
                log.info("original response.data: {}",response.getData());
                returnObject.put("data", response.getData());
            }
            if (!Objects.isNull(response.getRows())) {
                returnObject.put("rows", response.getRows());
                returnObject.put("total", response.getTotal());
            }
        }
        log.debug("return response data:{}",returnObject);
        return returnObject;

    }


}
