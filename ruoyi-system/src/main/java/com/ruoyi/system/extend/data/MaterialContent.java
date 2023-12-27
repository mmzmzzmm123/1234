package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class MaterialContent {

    private String materialContentId;

    private String materialGroupId;

    private String materialGroupName;

    private List<MaterialMsg> materialMsgs;

}
