package vip.carpool.api.gateway.vo;

import lombok.Data;

/**
 * @Author: huangguojie
 * @Date: 2020/9/25 21:24
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class CustomerVO {
    private String openId;
    private String secretId;
    private String secretKey;
    private String projectId;
    private String subProjectId;
}
