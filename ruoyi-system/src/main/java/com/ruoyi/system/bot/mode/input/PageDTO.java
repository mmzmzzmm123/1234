package com.ruoyi.system.bot.mode.input;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:23
 */
@Data
public class PageDTO {

    private Integer currentPage  = 1;
    private Integer pageSize  = 10;
}
