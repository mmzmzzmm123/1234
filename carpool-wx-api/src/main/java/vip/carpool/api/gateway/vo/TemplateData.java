package vip.carpool.api.gateway.vo;

import lombok.Data;

/**
 * @Author: huangguojie
 * @Date: 2020/12/13 11:49
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class TemplateData {
    private String value;

    public TemplateData(String value) {
        this.value = value;
    }
}
