package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalResidenceSaleClosingCaseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("dev")
public class DownloadOriginalResidenceSaleClosingCaseServiceImplTests {
    @Autowired
    private DownloadOriginalResidenceSaleClosingCaseServiceImpl downloadOriginalResidenceSaleClosingCaseService;

    @Test
    public void testDownload() {
        downloadOriginalResidenceSaleClosingCaseService.download(202007, 202005);
    }
}
