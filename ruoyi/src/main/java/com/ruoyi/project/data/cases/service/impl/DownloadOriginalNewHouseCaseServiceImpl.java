package com.ruoyi.project.data.cases.service.impl;

import com.ruoyi.common.constant.LabelConstants;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.mapper.OriginalNewHouseCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalNewHouseCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时下载一手房案例数据并且推送到联城数库中
 *
 * @author lihe
 * @date 2020年7月9日
 */
@Service
public class DownloadOriginalNewHouseCaseServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DownloadOriginalNewHouseCaseServiceImpl.class);

    @Autowired
    private DownloadOriginalNewHouseCaseMapper downloadOriginalNewHouseCaseMapper;
    @Autowired
    private OriginalNewHouseCaseMapper originalNewHouseCaseMapper;

    /**
     * 每月9号下载第一批案例数据
     */
    @Scheduled(cron = "0 0 0 9 * ?")
    public void downloadFirst() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);

        Integer yearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        downloadFirst(yearMonth);
    }

    /**
     * 下载指定月份的第一批一手房案例
     *
     * @param yearMonth
     */
    public void downloadFirst(Integer yearMonth) {
        try {
            // 创建表
            originalNewHouseCaseMapper.createFirstTable(yearMonth);
            // 拉取数据
            List<OriginalNewHouseCase> originalNewHouseCaseList =
                    downloadOriginalNewHouseCaseMapper.getFirst(yearMonth);

            makeTag(originalNewHouseCaseList);

            // 数据入库
            originalNewHouseCaseList.parallelStream().forEach(originalNewHouseCase -> {
                originalNewHouseCase.setYearMonth(yearMonth);
                try {
                    originalNewHouseCaseMapper.insertFirstTable(originalNewHouseCase);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        } catch (Exception e) {
            logger.error("下载第一批一手房案例出错", e);
        }
    }

    /**
     * 每月25号下载第二批案例数据并且清洗第一批案例数据
     */
    @Scheduled(cron = "0 0 0 25 * ?")
    public void downloadSecond() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);

        Integer yearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        downloadSecond(yearMonth);
    }

    /**
     * 下载指定月份的第二批一手房案例
     *
     * @param yearMonth
     */
    public void downloadSecond(Integer yearMonth) {
        try {
            // 创建表
            originalNewHouseCaseMapper.createSecondTable(yearMonth);
            // 拉取数据
            List<OriginalNewHouseCase> originalNewHouseCaseList =
                    downloadOriginalNewHouseCaseMapper.getSecond(yearMonth);

            makeTag(originalNewHouseCaseList);
            // 数据入库
            originalNewHouseCaseList.parallelStream().forEach(originalNewHouseCase -> {
                originalNewHouseCase.setYearMonth(yearMonth);
                originalNewHouseCaseMapper.insertSecondTable(originalNewHouseCase);
            });

        } catch (Exception e) {
            logger.error("下载第二批一手房案例出错", e);
        }
    }

    private void makeTag(List<OriginalNewHouseCase> originalNewHouseCaseList) {
        originalNewHouseCaseList.parallelStream().forEach(originalNewHouseCase -> {
            if (LabelConstants.OTHER.equals(originalNewHouseCase.getCaseHouseType())) {
                if (originalNewHouseCase.getCaseAddress().contains(LabelConstants.CHE) ||
                        originalNewHouseCase.getCaseAddress().contains(LabelConstants.CHE_DI_XIA) ||
                        originalNewHouseCase.getCaseAddress().contains(LabelConstants.CHE_REN_FANG) ||
                        originalNewHouseCase.getCaseAddress().contains(LabelConstants.CHE_KU) ||
                        originalNewHouseCase.getCaseAddress().contains(LabelConstants.CHE_WEI)) {
                    originalNewHouseCase.setCaseLabel(LabelConstants.CHE_WEI);
                }
            }
        });

//        Map<String, List<OriginalNewHouseCase>> originalNewHouseCasePerCaseId =
//                originalNewHouseCaseList.stream().collect(Collectors.groupingBy(OriginalNewHouseCase::getCaseId));
//
//        originalNewHouseCasePerCaseId.entrySet().stream().filter(x -> x.getValue().size() > 1).forEach(caseIdGroup
//        -> {
//            caseIdGroup.getValue().stream().forEach(originalNewHouseCase -> {
//
//            });
//        });
    }
}
