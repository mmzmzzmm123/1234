package com.ruoyi.project.data.basis.service;

import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;
import com.ruoyi.project.data.basis.domain.UvClosingCase;
import com.ruoyi.project.data.basis.domain.UvTradingCase;

import java.util.List;

/**
 * 联城数库，买卖成交案例
 *
 * @author lihe
 */
public interface IUvClosingCaseService {

    /**
     * 分页查询
     *
     * @param queryModel
     * @return
     */
    List<UvClosingCase> pageList(UvClosingCase queryModel);

    /**
     * 分页数量
     *
     * @param queryModel
     * @return
     */
    Integer pageCount(UvClosingCase queryModel);

    /**
     * 查询es地址
     *
     * @param text
     * @return
     */
    List<UvTradingCase> findByText(String text);


}
