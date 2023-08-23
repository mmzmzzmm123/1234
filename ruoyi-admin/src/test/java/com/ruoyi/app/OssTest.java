package com.ruoyi.app;

import com.qcloud.cos.model.Bucket;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.utils.cos.COSClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class OssTest {

    @Test
    public void createBucket() {
        String bucket = "zx";
        Bucket bucket1 = COSClientFactory.createBucket(bucket);
        System.out.println(bucket1);
        // COSBucket [name=zx-1316267898, creationDate=null, location=null, owner=null]
    }

}
