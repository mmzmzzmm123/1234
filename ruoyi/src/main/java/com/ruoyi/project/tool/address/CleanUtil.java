package com.ruoyi.project.tool.address;

import com.ruoyi.project.tool.address.model.CleanAddress;
import com.ruoyi.project.tool.address.model.CleanAddressBuilder;
import com.ruoyi.project.tool.address.service.impl.NoSignalAddressHandler;
import com.ruoyi.project.tool.address.service.impl.SignalAddressHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * 地址清洗
 *
 * @author lihe
 */
public class CleanUtil {

    /**
     * 清洗
     *
     * @param rawAddress
     * @return
     */
    public static CleanAddress clear(String rawAddress) {
        CleanAddress cleanAddress = new CleanAddress(rawAddress);
        if (cleanAddress.getContainsSpecialChar()) {
            new SignalAddressHandler().clear(cleanAddress);
        } else {
            new NoSignalAddressHandler().clear(cleanAddress);
        }
        return cleanAddress;
    }
}
