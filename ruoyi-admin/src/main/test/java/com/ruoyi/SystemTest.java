package com.ruoyi;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.IVehicleSourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class SystemTest {
    @Autowired
    IVehicleSourceService vehicleSourceService;

    @Test
    public void testGetBrands(){
        R r = vehicleSourceService.getInfoById(1L);
        System.out.println(r.getData().toString());

    }
}


