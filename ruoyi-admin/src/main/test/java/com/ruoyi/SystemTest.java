package com.ruoyi;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.Banner;
import com.ruoyi.system.service.IBannerService;
import com.ruoyi.system.service.IVehicleSourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class SystemTest {
    @Autowired
    IVehicleSourceService vehicleSourceService;

    @Autowired
    IBannerService bannerService;
    @Test
    public void testGetBrands(){
       // R r = vehicleSourceService.getInfoById(1L);
        //System.out.println(r.getData().toString());
        List<Banner> banners = bannerService.selectBannerByScene(1);
        banners.forEach(
                e-> System.out.println("测试获取bannerid"+e.getId())
        );

    }
}


