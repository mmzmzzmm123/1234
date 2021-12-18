package vip.carpool.api.gateway.dto;

import lombok.Data;

/**
 * @Author: huangguojie
 * @Date: 2020/12/13 15:13
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class SubscribeMessageDTO {

    /**
     * errcode : 0
     * errmsg : ok
     */
    private Integer errcode;
    private String errmsg;

}
