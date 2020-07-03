package com.uvaluation.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.IOriginalOfficeCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleClosingCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleOpeningCaseService;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.ruoyi.project.data.price.service.IArtificialResidenceSalePriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("uat")
public class OfficeBasePriceTests {

    @Autowired
    private IOriginalOfficeCaseService originalOfficeCaseService;

    /**
     * 下载案例并且作价
     */
    @Test
    public void testCompute() {
        originalOfficeCaseService.compute();
    }
}
