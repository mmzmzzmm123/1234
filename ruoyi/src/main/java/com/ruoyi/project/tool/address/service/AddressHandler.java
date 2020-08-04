package com.ruoyi.project.tool.address.service;

import com.ruoyi.project.tool.address.model.CleanAddress;

/**
 * 地址处理方法
 *
 * @author lihe
 */
public interface AddressHandler {

    /**
     * 地址清洗
     *
     * @param cleanAddress
     */
    void clear(CleanAddress cleanAddress);

}
