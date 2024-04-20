package com.ruoyi.system.product;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.mapper.TProductMapper;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RuoYiApplication.class)
public class ProductTest {
    @Resource
    private TProductMapper productMapper;

    @Test
    public void inertTest(){
        TProduct tProduct = new TProduct();
        tProduct.setName("测试产品1");
        tProduct.setStatus(1);
        int result = productMapper.insertTProduct(tProduct);
        System.out.println("TProduct = " + JSON.toJSONString(tProduct));
    }
}
