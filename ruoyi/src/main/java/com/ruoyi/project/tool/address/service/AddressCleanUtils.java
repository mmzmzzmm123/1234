package com.ruoyi.project.tool.address.service;

import com.ruoyi.project.tool.address.model.CleanAddress;

import java.util.LinkedList;
import java.util.List;

/**
 * 地址清洗
 *
 * @author lihe
 */
public class AddressCleanUtils {
    private AddressBuilder builder;

    private static List<String> specialChar = new LinkedList<>();

    static {
        specialChar.add("。");
        specialChar.add(".");
        specialChar.add("，");
        specialChar.add(",");
        specialChar.add("-");
        specialChar.add("——");
        specialChar.add("_");
        specialChar.add("、");
        specialChar.add("（");
        specialChar.add("）");
        specialChar.add("(");
        specialChar.add(")");
        specialChar.add("《");
        specialChar.add("》");
    }

    /**
     * @param text
     * @return
     */
    public List<CleanAddress> clear(String text) {
        String todoAddress = text.trim()
                .replace("\t", "")
                .replace(" ", "");

        for (int i = 0; i < specialChar.size(); i++) {
            if (todoAddress.contains(specialChar.get(i))) {
                builder = new DefaultAddressBuilder(todoAddress);
            }
        }

        return null;
    }
}
