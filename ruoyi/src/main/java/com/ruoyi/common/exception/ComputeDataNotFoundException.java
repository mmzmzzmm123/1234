package com.ruoyi.common.exception;

import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * 作价数据未找到
 *
 * @author ruoyi
 */
public class ComputeDataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    public ComputeDataNotFoundException(String module) {
        this.module = module;
    }

    @Override
    public String getMessage() {
        return module + "未找到相关案例数据。";
    }
}
