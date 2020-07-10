package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OfficeAggregationCase;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalNewHouseCaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一手房案例下载备份
 *
 * @author lihe
 * @date 2020年7月9日
 */
@DS("compute")
public interface OriginalNewHouseCaseMapper {

    /**
     * 创建第一批一手房案例表
     *
     * @param yearMonth
     */
    void createFirstTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 创建第一批一手房案例
     *
     * @param originalNewHouseCase
     * @return
     */
    int insertFirstTable(OriginalNewHouseCase originalNewHouseCase);

    /**
     * 创建第二批一手房案例表
     *
     * @param yearMonth
     */
    void createSecondTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 创建第二批一手房案例
     *
     * @param originalNewHouseCase
     * @return
     */
    int insertSecondTable(OriginalNewHouseCase originalNewHouseCase);

    /**
     * 分页总数
     *
     * @param originalNewHouseCase
     * @return
     */
    int pageCount(OriginalNewHouseCase originalNewHouseCase);

    /**
     * 分页列表
     *
     * @param originalNewHouseCase
     * @return
     */
    List<OriginalNewHouseCase> pageList(OriginalNewHouseCase originalNewHouseCase);


}
