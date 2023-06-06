package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class WxPayAmount {
    //
    private Integer total;
    //
    private Integer payer_total;
    //
    private String currency;
    //
    private String payer_currency;
}
