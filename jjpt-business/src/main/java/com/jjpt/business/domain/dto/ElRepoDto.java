package com.jjpt.business.domain.dto;

import com.jjpt.business.domain.ElRepo;
import lombok.Data;

/**
 * ElRepoDto
 *
 * @author 卢亚峰
 * @version 2024/05/18 22:32
 **/
@Data
public class ElRepoDto extends ElRepo {
    private static final long serialVersionUID = 1L;


    private Integer multiCount;


    private Integer radioCount;


    private Integer judgeCount;
}

