package com.ruoyi.system.extend.data;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class MaterialMsg {


    @JSONField(name = "msg_num")
    private Integer msgNum;

    @JSONField(name = "msg_type")
    private Integer msgType;

    @JSONField(name = "msg_content")
    private String msgContent;

    @JSONField(name = "msg_title")
    private String msgTitle;
}
