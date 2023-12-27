package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class MaterialContentPageResult {

    private String materialContentId;
    private String msgContent;
    private String msgTitle;
    private Integer materialType;
    private PostBotMsg postBotMsg;
    private Integer createPostBotStatus;
    private List<String> messageCodes;

}
