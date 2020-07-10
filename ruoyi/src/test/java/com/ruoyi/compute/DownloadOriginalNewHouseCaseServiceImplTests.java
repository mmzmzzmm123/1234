package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalNewHouseCaseServiceImpl;
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
public class DownloadOriginalNewHouseCaseServiceImplTests {
    @Autowired
    private DownloadOriginalNewHouseCaseServiceImpl downloadOriginalNewHouseCaseService;

    /**
     * 下载第一批一手房案例
     */
    @Test
    public void testDownloadFirst() {
        downloadOriginalNewHouseCaseService.downloadFirst();
    }

    /**
     * 下载第二批一手房案例
     */
    @Test
    public void testDownloadSecond() {
        downloadOriginalNewHouseCaseService.downloadSecond();
    }

    @Test
    public void testDownloadFistLoop() {
        for (int i = 202001; i <= 202005; i++) {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        }
    }

    @Test
    public void testDownloadSecondLoop() {
        for (int i = 202001; i <= 202005; i++) {
            downloadOriginalNewHouseCaseService.downloadSecond(i);
        }
    }


    @Test
    public void testDownloadLastYearFistLoop() {
        Arrays.asList(201901, 201902, 201903, 201904, 201905, 201906, 201907, 201908, 201909, 201910, 201911, 201912).parallelStream().forEach(i -> {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        });
    }

    @Test
    public void testDownload201901First() {
        downloadOriginalNewHouseCaseService.downloadFirst(201901);
    }

    @Test
    public void testDownload202003First() {
        downloadOriginalNewHouseCaseService.downloadFirst(202003);
    }

    @Test
    public void testDownloadLastYearSecondLoop() {
        Arrays.asList(201901, 201902, 201903, 201904, 201905, 201906, 201907, 201908, 201909, 201910, 201911, 201912).parallelStream().forEach(i -> {
            downloadOriginalNewHouseCaseService.downloadSecond(i);
        });
    }

    @Test
    public void testDownload201902Second() {
        downloadOriginalNewHouseCaseService.downloadSecond(201902);
    }
}
