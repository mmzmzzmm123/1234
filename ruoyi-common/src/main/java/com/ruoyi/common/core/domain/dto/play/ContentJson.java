package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("Content消息主体")
public class ContentJson implements Serializable {
    private static final long serialVersionUID = -6434841971083561503L;

    @ApiModelProperty("消息内容")
    private String sMateContent;

    @ApiModelProperty("消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件 2013 小程序 2016 音乐 201姓名 2017接粉号 2018自定义")
    private Integer momentTypeId;

    @ApiModelProperty("图片消息url ")
    private String sMateImgUrl;

    @ApiModelProperty("语音时长")
    private Integer sMateBVLen;

    @ApiModelProperty("语音链接")
    private String sMateAwrUrl;

    private String sMateVUrl ;
    private String sMateId ;
    private String sMateTitle ;
    private String title ;

    private String uuid;
}
