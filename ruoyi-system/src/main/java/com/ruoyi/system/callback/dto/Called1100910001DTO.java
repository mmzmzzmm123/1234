package com.ruoyi.system.callback.dto;

import cn.hutool.core.util.StrUtil;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * {"businessId":"","clusterType":null,"consumer":"","data":{"user_serial_no":"3672CBACAE8ED57C7D7BF81F8DF33A49","country_code":"44","user_id":"6512508787","robot_serial_no":"1CDBB31B78E3CADDB4322BF5FBE3A1E3","user_name":"rcytjgae4vqgjr4gjkmr","last_name":"Bennett","type":20,"first_name":"Patricia","account":"447404178435","head_img_url":""},"extend":"1722252696368988160|2501","extendVersionOne":"","loginType":null,"merchantId":"","msgBusiType":null,"nTimeStamp":null,"optSerNo":"20231108220015400644610654886","optUserId":"","originalParams":"","pushService":"","pushTime":null,"resultCode":0,"resultMsg":"SUCCESS","robotId":"1CDBB31B78E3CADDB4322BF5FBE3A1E3","serverName":"neighbour-message","tgRobotId":"","timId":"","type":1100910001,"userId":"","vcMerchantNo":"202004240382704","version":"","vrMerchatId":"","vrRobotId":"","wxId":""}
 */
@Data
public class Called1100910001DTO {

    private String user_serial_no;
    private String country_code;
    private String user_id;
    private String robot_serial_no;
    private String user_name;
    private String last_name;
    private Integer type;
    private String first_name;
    private String account;
    private String head_img_url;

}
