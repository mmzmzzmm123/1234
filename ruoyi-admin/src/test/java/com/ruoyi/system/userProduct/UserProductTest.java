package com.ruoyi.system.userProduct;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.app.product.ao.UserProductAO;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
import com.ruoyi.system.userproduct.domain.TUserProduct;
import com.ruoyi.system.userproduct.service.ITUserProductService;
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
public class UserProductTest {
    @Resource
    private UserProductAO userProductAO;

    @Test
    public void insertTest(){
        userProductAO.addSubscribe(1L,1L);
    }
}
