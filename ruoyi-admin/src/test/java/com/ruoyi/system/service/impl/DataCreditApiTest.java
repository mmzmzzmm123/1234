
package com.ruoyi.system.service.impl;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.domain.model.credit.BlackRecord;
import com.ruoyi.system.domain.model.credit.CreditResponse;
import com.ruoyi.system.domain.model.credit.CreditToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuoYiApplication.class})
public class DataCreditApiTest {

    @Autowired
    private DataCreditApi dataCreditApi;

    @Test
    public void getToken() {
        CreditToken token = dataCreditApi.getToken();
        System.out.println(token.getAccess_token());
    }

    @Test
    public void checkIsBlack() {
        CreditResponse<BlackRecord> responseEntity = dataCreditApi.checkIsBlack("913505006628260557", 1);
        System.out.println(responseEntity.getData().getColumnShowList().getTotal());
    }

    @Test
    public void checkIsDishonestExecute() {
        CreditResponse<BlackRecord> responseEntity = dataCreditApi.checkIsDishonestExecute("913505006628260557", 1);
        System.out.println(responseEntity.getData().getColumnShowList().getTotal());
    }
}