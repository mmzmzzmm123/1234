package com.ruoyi.common.core.domain.dto.play;

import lombok.Data;

@Data
public class WordPageDTO {
    private String id;
    private Integer page = 1;
    private Integer pageSize = 20;
}
