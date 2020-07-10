package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceSaleClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleClosingCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时下载住宅销售成交案例
 * 来源: 8433 LC_DataCenter
 *
 * @author lihe
 * @date 2020年7月9日
 */
@Service
@DS("compute")
public class DownloadOriginalResidenceSaleClosingCaseServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DownloadOriginalResidenceSaleClosingCaseServiceImpl.class);

    @Autowired
    private OriginalResidenceSaleClosingCaseMapper originalResidenceSaleClosingCaseMapper;
    @Autowired
    private DownloadOriginalResidenceSaleClosingCaseMapper downloadOriginalResidenceSaleClosingCaseMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 每个月10号拉取
     */
    public void download() {
        Integer remoteTableRoute = DateUtils.getLastYearMonth();
        Integer tableRoute = DateUtils.getNextYearMonth();

        download(tableRoute, remoteTableRoute);
    }

    /**
     * @param tableRoute
     * @param remoteTableRoute
     */
    public void download(Integer tableRoute, Integer remoteTableRoute) {

        /**
         * 删除原有表
         * 1. 创建成交案例原始表
         * 2. 创建成交案例清洗表
         * 3. 清洗成交案例
         */
        before(tableRoute);

        List<OriginalResidenceSaleClosingCase> originalResidenceSaleClosingCases =
                downloadOriginalResidenceSaleClosingCaseMapper.download(remoteTableRoute);

        running(tableRoute, originalResidenceSaleClosingCases);
        after(tableRoute);

        copy2UVData(tableRoute, remoteTableRoute);
    }


    /**
     * 准备工作
     *
     * @param tableRoute
     */
    private void before(Integer tableRoute) {
        originalResidenceSaleClosingCaseMapper.createRawTable(tableRoute);
        originalResidenceSaleClosingCaseMapper.createCleanTable(tableRoute);
    }

    /**
     * 数据入库
     *
     * @param tableRoute
     * @param originalResidenceSaleClosingCases
     */
    private void running(Integer tableRoute,
                         List<OriginalResidenceSaleClosingCase> originalResidenceSaleClosingCases) {

        originalResidenceSaleClosingCases.parallelStream().forEach(originalResidenceSaleClosingCase -> {
            originalResidenceSaleClosingCase.setYearMonth(tableRoute);
            originalResidenceSaleClosingCase.setCaseId(originalResidenceSaleClosingCase.generateCaseId());
            originalResidenceSaleClosingCase.setCleanPropertyType(originalResidenceSaleClosingCase.refinePropertyType());
            originalResidenceSaleClosingCaseMapper.insertRawTable(originalResidenceSaleClosingCase);
        });
    }

    /**
     * 清洗成交案例
     *
     * @param tableRoute
     */
    private void after(Integer tableRoute) {
        try {
            String rawSql = LoadUtil.loadContent("sql-template/clear_sale_closing_case.sql");
            String sql = rawSql.replace("#yearMonth#", tableRoute.toString());
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            logger.error("住宅销售成交案例清洗异常", e);
        }
    }

    /**
     * 拷贝数据到联城数库
     *
     * @param tableRoute
     * @param remoteTableRoute
     */
    @Async
    public void copy2UVData(Integer tableRoute, Integer remoteTableRoute) {
        try {
            String yearMonth = String.format("%d-%02d", remoteTableRoute / 100, remoteTableRoute % 100);
            String rawSql = LoadUtil.loadContent("sql-template/copy_ershou_data.sql");
            String sql = rawSql.replace("#tableRoute#", tableRoute.toString())
                    .replace("#yearMonth#", yearMonth);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            logger.error("住宅销售二手成交案例推送到联城数库异常", e);
        }
    }

}
