package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncOriginalResidenceSaleOpeningCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 定时下载住宅销售挂牌案例
 * 来源: 8433 LC_DataCenter
 *
 * @author lihe
 * @date 2020年7月9日
 */
@Service
@DS("compute")
public class DownloadOriginalResidenceSaleOpeningCaseServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DownloadOriginalResidenceSaleOpeningCaseServiceImpl.class);

    @Autowired
    private DownloadOriginalResidenceSaleOpeningCaseMapper downloadOriginalResidenceSaleOpeningCaseMapper;
    @Autowired
    private OriginalResidenceSaleOpeningCaseMapper originalResidenceSaleOpeningCaseMapper;
    @Autowired
    private SyncOriginalResidenceSaleOpeningCaseMapper syncOriginalResidenceSaleOpeningCaseMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 每个月25号拉取
     */
    public void download() {
        Integer tableRoute = DateUtils.getNextYearMonth();
        Integer remoteTableRoute = DateUtils.getYearMonth();

        download(tableRoute, remoteTableRoute);
    }

    /**
     * @param tableRoute
     * @param remoteTableRoute
     */
    public void download(Integer tableRoute, Integer remoteTableRoute) {

        /**
         * 删除原有表
         * 1. 下载数据
         * 2. 同步数据
         * 3. 创建表
         * 4. 插入数据
         * 5. 推送数据
         */
        before(tableRoute, remoteTableRoute);

        List<OriginalResidenceSaleOpeningCase> originalResidenceSaleOpeningCases =
                downloadOriginalResidenceSaleOpeningCaseMapper.download();

        running(tableRoute, remoteTableRoute, originalResidenceSaleOpeningCases);
        after(tableRoute, remoteTableRoute);
    }


    /**
     * 准备工作
     *
     * @param tableRoute
     * @param remoteTableRoute
     */
    private void before(Integer tableRoute, Integer remoteTableRoute) {
        originalResidenceSaleOpeningCaseMapper.createRawTable(tableRoute);
        originalResidenceSaleOpeningCaseMapper.createCleanTable(tableRoute);
        originalResidenceSaleOpeningCaseMapper.createAssembleTable(tableRoute);
        originalResidenceSaleOpeningCaseMapper.createComputePriceTable(tableRoute);
        originalResidenceSaleOpeningCaseMapper.createArtificialPriceTable(tableRoute);
        originalResidenceSaleOpeningCaseMapper.createUltimatePriceTable(tableRoute);
        syncOriginalResidenceSaleOpeningCaseMapper.createRawTable(remoteTableRoute);
    }

    /**
     * 数据入库
     *
     * @param tableRoute
     * @param remoteTableRoute
     */
    private void running(Integer tableRoute, Integer remoteTableRoute,
                         List<OriginalResidenceSaleOpeningCase> originalResidenceSaleOpeningCaseList) {

        originalResidenceSaleOpeningCaseList.parallelStream().forEach(originalResidenceSaleOpeningCase -> {
            originalResidenceSaleOpeningCase.setYearMonth(tableRoute);
            originalResidenceSaleOpeningCase.setCaseId(originalResidenceSaleOpeningCase.getNewCaseId());
            originalResidenceSaleOpeningCaseMapper.insertRawTable(originalResidenceSaleOpeningCase);

            originalResidenceSaleOpeningCase.setYearMonth(remoteTableRoute);
            syncOriginalResidenceSaleOpeningCaseMapper.insertRawTable(originalResidenceSaleOpeningCase);
        });
    }

    /**
     * 计算住宅基价
     *
     * @param tableRoute
     * @param remoteTableRoute
     */
    private void after(Integer tableRoute, Integer remoteTableRoute) {
        try {
            // 清洗挂牌案例
            String rawSql = LoadUtil.loadContent("sql-template/clear_sale_opening_case.sql");
            String sql = rawSql.replace("#yearMonth#", tableRoute.toString())
                    .replace("#lastYearMonth#", remoteTableRoute.toString());
            jdbcTemplate.update(sql);

            // 作价
            rawSql = LoadUtil.loadContent("sql-template/compute_sale_price.sql");
            sql = rawSql.replace("#yearMonth#", tableRoute.toString())
                    .replace("#lastYearMonth#", remoteTableRoute.toString())
                    .replace("#lastPriceDate#", String.format("%d-%d-01", tableRoute / 100, tableRoute % 100));
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            logger.error("住宅作价异常", e);
        }
    }

}
