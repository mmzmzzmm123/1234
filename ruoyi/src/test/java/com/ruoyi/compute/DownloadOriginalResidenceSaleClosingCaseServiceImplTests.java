package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalResidenceSaleClosingCaseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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


    @Test
    public void testLoopDownload() {
        // , 201912
        Arrays.asList(201901, 201902, 201903, 201904, 201905, 201906, 201907, 201908, 201909, 201910).parallelStream().forEach(i -> {
            downloadOriginalResidenceSaleClosingCaseService.download(i + 2, i);
        });
//        downloadOriginalResidenceSaleClosingCaseService.download(202001, 201911);
//        downloadOriginalResidenceSaleClosingCaseService.download(202002, 201912);
//        downloadOriginalResidenceSaleClosingCaseService.download(202003, 202001);
//        downloadOriginalResidenceSaleClosingCaseService.download(202004, 202002);
//        downloadOriginalResidenceSaleClosingCaseService.download(202005, 202003);
//        downloadOriginalResidenceSaleClosingCaseService.download(202006, 202004);
//        downloadOriginalResidenceSaleClosingCaseService.download(202007, 202005);
//        downloadOriginalResidenceSaleClosingCaseService.download(202008, 202006);
    }
}
