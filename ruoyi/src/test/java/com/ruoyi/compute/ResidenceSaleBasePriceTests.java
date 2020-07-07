package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
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
public class ResidenceSaleBasePriceTests {

    @Autowired
    private IOriginalResidenceSaleOpeningCaseService originalResidenceOpeningCaseService;
    @Autowired
    private IArtificialResidenceSalePriceService artificialResidenceSalePriceService;
    @Autowired
    private IOriginalResidenceSaleClosingCaseService originalResidenceClosingCaseService;

    /**
     * 成交案例清洗
     */
    @Test
    public void testClearClosingCase() {
        originalResidenceClosingCaseService.clear();
    }

    /**
     * 挂牌案例清洗
     */
    @Test
    public void testClearOpeningCase() {
        originalResidenceOpeningCaseService.clear();
    }


    @Test
    public void testBatchImport() {
        List<ComputeResidenceSaleBasePrice> list = new LinkedList<>();
        ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice = new ComputeResidenceSaleBasePrice();
        computeResidenceSaleBasePrice.setCommunityId("10101");
        computeResidenceSaleBasePrice.setBasePriceDraft(new BigDecimal(12222));
        list.add(computeResidenceSaleBasePrice);
        artificialResidenceSalePriceService.batchImport(202007, list);
    }

}
