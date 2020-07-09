package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.IOriginalOfficeCaseService;
import com.ruoyi.project.data.price.service.impl.UltimateOfficeBasePriceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("dev")
public class OfficeBasePriceTests {

    @Autowired
    private IOriginalOfficeCaseService originalOfficeCaseService;

    @Autowired
    private UltimateOfficeBasePriceServiceImpl ultimateOfficeBasePriceService;

    /**
     * 下载案例并且作价
     */
    @Test
    public void testCompute() {
        originalOfficeCaseService.compute();
    }

    @Test
    public void testPushData() {
        ultimateOfficeBasePriceService.pushData(202007, 202006, 202005);
    }

}
