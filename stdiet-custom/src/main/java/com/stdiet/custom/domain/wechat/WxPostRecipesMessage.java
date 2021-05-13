package com.stdiet.custom.domain.wechat;

import lombok.Data;

import java.util.Date;

@Data
public class WxPostRecipesMessage {

    String name;

    Date startDate;

    Date endDate;

    String remark;
}
