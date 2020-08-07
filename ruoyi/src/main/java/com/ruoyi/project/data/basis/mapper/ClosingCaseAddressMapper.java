package com.ruoyi.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.basis.domain.ClosingCaseAddress;

import java.util.List;

/**
 * 成交地址Mapper
 *
 * @author lihe
 */
@DS("teemlink")
public interface ClosingCaseAddressMapper {

    /**
     * 总数
     *
     * @return
     */
    int selectCount();

    /**
     * 分页查询
     *
     * @param offset
     * @param limit
     * @return
     */
    List<ClosingCaseAddress> selectPageList(int offset, int limit);

    /**
     * 更新
     *
     * @param closingCaseAddress
     * @return
     */
    int clear(ClosingCaseAddress closingCaseAddress);
}
