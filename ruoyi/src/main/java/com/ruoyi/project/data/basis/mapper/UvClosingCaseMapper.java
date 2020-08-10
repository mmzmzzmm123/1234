package com.ruoyi.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.basis.domain.ClosingCaseAddress;
import com.ruoyi.project.data.basis.domain.UvClosingCase;

import java.util.List;

/**
 * 联城数库买卖成交案例
 *
 * @author lihe
 */
@DS("teemlink")
public interface UvClosingCaseMapper {

    /**
     * 总数
     *
     * @param queryModel
     * @return
     */
    int selectPageCount(UvClosingCase queryModel);

    /**
     * 分页查询
     *
     * @param queryModel
     * @return
     */
    List<UvClosingCase> selectPageList(UvClosingCase queryModel);

}
