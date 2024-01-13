package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/5/005 13:37
 * @Description :
 */
@Data
public class PlayConfusionStateVO {

    private Integer playId;

    private Integer total;

    private Integer successCount;

    private Integer failCount;

}
