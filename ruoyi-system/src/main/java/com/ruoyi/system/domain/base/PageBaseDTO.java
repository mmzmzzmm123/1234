package com.ruoyi.system.domain.base;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 15:29
 * @Description :
 */
@Data
public class PageBaseDTO {
    private Integer page = 1;
    private Integer limit = 10;
}
