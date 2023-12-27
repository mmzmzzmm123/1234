package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class SavePostBotMsg {

    @ApiModelProperty("素材类型 Text(Text)=1 Photo(Photo)=2 Gif(Gif)=3 Video(Video)=4")
    private int materialMediaType;

    @ApiModelProperty("媒体链接（图片地址）")
    private String mediaUrl;

    @ApiModelProperty("文本内容")
    private String title;

    @ApiModelProperty("PostBot按钮数据")
    private List<SaveTextLinkList> textLinkList;

}
