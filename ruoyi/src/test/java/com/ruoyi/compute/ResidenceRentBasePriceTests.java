package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentOpeningCaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("uat")
public class ResidenceRentBasePriceTests {

    @Autowired
    private IOriginalResidenceRentOpeningCaseService originalResidenceRentOpeningCaseService;
    @Autowired
    private IOriginalResidenceRentClosingCaseService originalResidenceRentClosingCaseService;

    /**
     * 住宅租赁挂牌案例下载清洗
     */
    @Test
    public void testClearOpeningCase() {
        originalResidenceRentOpeningCaseService.pullData();
    }

    /**
     * 住宅租赁成交案例下载清洗
     */
    @Test
    public void testClearClosingCase() {
        originalResidenceRentClosingCaseService.pullData();
    }

}
