package com.ruoyi.system.extend.data;

import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class GetMaterialContentsData extends UtTouchQueryPage {

    private String userCode;

    private String materialGroupId;

    private String likeStr;

}
