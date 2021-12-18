package vip.carpool.api.gateway.dto;

import lombok.Data;

/**
 * @Author: huangguojie
 * @Date: 2020/9/19 20:25
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class UserOfflineTaskFinishCallDTO {
    private Integer retCode;
    private String retMsg;
    private String content;
}
