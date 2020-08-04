package com.ruoyi;

import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.tool.address.CleanUtil;
import com.ruoyi.project.tool.address.model.CleanAddress;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

public class CleanUtilTests {

    @Test
    public void testSortDistrict() {
        List<String> textList = LoadUtil.loadList("temp.dict");
        textList.sort((x, y) -> y.length() - x.length());
        textList.forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    public void testClear() {
        List<String> textList = LoadUtil.loadList("icbc.dict");

        textList.forEach(item -> {
            CleanAddress cleanAddress = CleanUtil.clear(item);
            System.out.println(cleanAddress);
        });


//        Assert.assertEquals(cleanAddress.getDistrict(), "");
//        Assert.assertEquals(cleanAddress.getDistrict(), "");
//        Assert.assertEquals(cleanAddress.getDistrict(), "");
//        Assert.assertEquals(cleanAddress.getDistrict(), "");
//        Assert.assertEquals(cleanAddress.getDistrict(), "");
//        Assert.assertEquals(cleanAddress.getDistrict(), "");
    }

    @Test
    public void testBase64() throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(
                "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");
        FileOutputStream out = new FileOutputStream("C:\\Work\\uv\\fonts2.woff");
        out.write(buffer);
        out.close();
    }
}


