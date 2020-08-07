package com.ruoyi;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.basis.domain.ClosingCaseAddress;
import com.ruoyi.project.data.basis.mapper.ClosingCaseAddressMapper;
import com.ruoyi.project.tool.address.AddressResult;
import com.ruoyi.project.tool.address.utils.AddressUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("uat")
public class AddressUtilDBTests {

    @Autowired
    private ClosingCaseAddressMapper mapper;

    /**
     * 地址清洗测试
     */
    @Test
    public void testClear() {
        // 5000
        Integer total = mapper.selectCount();

        int div = total / 5000;
        if (0 != total % 5000) {
            div += 1;
        }

        Arrays.stream(IntStream.range(0, div).toArray()).parallel().forEach(pageIndex -> {
            System.out.println(pageIndex);
            List<ClosingCaseAddress> list = mapper.selectPageList(pageIndex * 5000, 5000);
            list.forEach(item -> {
                try {
                    AddressResult addressResult = AddressUtil.clear(item.getAddress());
                    if (null == addressResult.getCleanAddress()) {
                        return;
                    }
                    String address = String.join(",", addressResult.getCleanAddress());
                    item.setCleanAddress(address);
                    mapper.clear(item);
                } catch (Exception e) {
                    System.out.println(item.getAddress());
                    e.printStackTrace();
                }
            });
        });
    }
}


