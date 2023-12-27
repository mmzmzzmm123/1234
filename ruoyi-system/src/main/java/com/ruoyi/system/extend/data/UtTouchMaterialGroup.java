package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class UtTouchMaterialGroup {

    private String materialGroupId;

    private String materialGroupName;

    private List<MaterialContent> materialContents;
}
