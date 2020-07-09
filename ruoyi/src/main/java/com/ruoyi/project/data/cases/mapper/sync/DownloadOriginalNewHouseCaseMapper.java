package com.ruoyi.project.data.cases.mapper.sync;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一手房DAO
 *
 * @author lihe
 * @date 2020年7月9日
 */
@DS("calc")
public interface DownloadOriginalNewHouseCaseMapper {
    /**
     * 获取第一批一手房数据
     *
     * @param yearMonth
     * @return
     */
    List<OriginalNewHouseCase> getFirst(@Param("yearMonth") Integer yearMonth);

    /**
     * 获取第二批一手房数据
     *
     * @param yearMonth
     * @return
     */
    List<OriginalNewHouseCase> getSecond(@Param("yearMonth") Integer yearMonth);
}
