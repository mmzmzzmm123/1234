package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.constant.LabelConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.mapper.OriginalNewHouseCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalNewHouseCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时下载一手房案例数据并且推送到联城数库中
 *
 * @author lihe
 * @date 2020年7月9日
 */
@Service
@DS("compute")
public class DownloadOriginalNewHouseCaseServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DownloadOriginalNewHouseCaseServiceImpl.class);

    @Autowired
    private DownloadOriginalNewHouseCaseMapper downloadOriginalNewHouseCaseMapper;
    @Autowired
    private OriginalNewHouseCaseMapper originalNewHouseCaseMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 每月10号下载第一批案例数据
     */
    public void downloadFirst() {
        Integer yearMonth = DateUtils.getLastYearMonth();
        downloadFirst(yearMonth);
        afterFirst(yearMonth);
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
    public void downloadSecond() {
        Integer yearMonth = DateUtils.getLastYearMonth();
        downloadSecond(yearMonth);
        afterSecond(yearMonth);
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
    }

    /**
     * 第一批次入库后的更新操作
     *
     * @param tableRoute
     */
    private void afterFirst(Integer tableRoute) {
        try {
            String rawSql = LoadUtil.loadContent("sql-template/copy_yishou_data.sql");
            String yearMonth = String.format("%d-%02d", tableRoute / 100, tableRoute % 100);
            String sql = rawSql
                    .replace("#tableRoute#", tableRoute.toString())
                    .replace("#yearMonth#", yearMonth);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            logger.error("第一批次案例推送成交案例库异常", e);
            e.printStackTrace();
        }
    }

    /**
     * 第二批次入库后的更新操作
     *
     * @param tableRoute
     */
    private void afterSecond(Integer tableRoute) {
        //
        try {
            String rawSql = LoadUtil.loadContent("sql-template/copy_yishou_data.sql");
            String yearMonth = String.format("%d-%02d", tableRoute / 100, tableRoute % 100);
            String sql = rawSql
                    .replace("#tableRoute#", tableRoute.toString() + "_update")
                    .replace("#yearMonth#", yearMonth);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            logger.error("第二批次案例推送成交案例库异常", e);
            e.printStackTrace();
        }
    }
}
