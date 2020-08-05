package com.ruoyi.project.tool.address;

/**
 * 地址构建
 *
 * @author lihe
 */
public interface AddressBuilder {

    /**
     * 清洗
     * @param text
     * @return
     */
    StandardAddress clear(String text);
}
