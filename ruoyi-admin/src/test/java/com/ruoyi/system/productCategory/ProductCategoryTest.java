package com.ruoyi.system.productCategory;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
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
public class ProductCategoryTest {
    @Resource
    private TProductCategoryMapper productCategoryMapper;

    @Test
    public void insertBatchTest() {
        List<TProductCategory> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TProductCategory tProductCategory = new TProductCategory();
            tProductCategory.setProductId(Long.valueOf(i));
            tProductCategory.setCategoryId(Long.valueOf(i + 1));
            tProductCategory.setCreateTime(new Date());
//            tProductCategory.setCreateBy("测试类");
            list.add(tProductCategory);
        }
        productCategoryMapper.insertTProductCategoryBatch(list);

    }
}
