package com.ruoyi.framework.task;

import com.ruoyi.project.data.cases.service.IOriginalOfficeCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentOpeningCaseService;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalNewHouseCaseServiceImpl;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalResidenceSaleClosingCaseServiceImpl;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalResidenceSaleOpeningCaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {

    @Autowired
    private DownloadOriginalResidenceSaleOpeningCaseServiceImpl downloadOriginalResidenceSaleOpeningCaseService;
    @Autowired
    private DownloadOriginalResidenceSaleClosingCaseServiceImpl downloadOriginalResidenceSaleClosingCaseService;
    @Autowired
    private DownloadOriginalNewHouseCaseServiceImpl downloadOriginalNewHouseCaseService;
    @Autowired
    private IOriginalOfficeCaseService originalOfficeCaseService;
    @Autowired
    private IOriginalResidenceRentOpeningCaseService originalResidenceRentOpeningCaseService;
    @Autowired
    private IOriginalResidenceRentClosingCaseService originalResidenceRentClosingCaseService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 住宅销售挂牌案例
     */
    public void downloadResidenceOpeningCase() {
        downloadOriginalResidenceSaleOpeningCaseService.download();
    }

    /**
     * 下载第一批一手房成交案例
     */
    public void downloadFirstNewHouseCase() {
        downloadOriginalNewHouseCaseService.downloadFirst();
    }

    /**
     * 下载住宅销售二手房成交案例
     */
    public void downloadResidenceClosingCase() {
        downloadOriginalResidenceSaleClosingCaseService.download();
    }

    /**
     * 下载第二批一手房成交案例
     */
    public void downloadSecondNewHouseCase() {
        downloadOriginalNewHouseCaseService.downloadSecond();
    }

    /**
     * 下载办公案例和作价
     */
    public void downloadOfficeCase() {
        originalOfficeCaseService.compute();
    }

    /**
     * 住宅租赁挂牌案例下载
     */
    public void downloadRentOpeningCase() {
        originalResidenceRentOpeningCaseService.pullData();
    }

    /**
     * 住宅租赁成交案例下载
     */
    public void downloadRentClosingCase() {
        originalResidenceRentClosingCaseService.pullData();
    }
}
