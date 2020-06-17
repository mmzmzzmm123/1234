package com.uvaluation.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleClosingCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleOpeningCaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class ResidenceSaleBasePriceTests {

    @Autowired
    private IOriginalResidenceSaleOpeningCaseService originalResidenceOpeningCaseService;
    @Autowired
    private IOriginalResidenceSaleClosingCaseService originalResidenceClosingCaseService;

    @Test
    public void testClearOpeningCase() {
        originalResidenceOpeningCaseService.pullData();
    }

    @Test
    public void testClearClosingCase() {
        originalResidenceClosingCaseService.pullData();
    }


}
