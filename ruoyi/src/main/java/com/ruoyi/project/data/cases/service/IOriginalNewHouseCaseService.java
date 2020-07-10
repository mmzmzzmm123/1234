package com.ruoyi.project.data.cases.service;

import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;

import java.util.List;

/**
 * 一手房成交案例Service
 *
 * @author lihe
 */
public interface IOriginalNewHouseCaseService {

    /**
     * @param originalNewHouseCase
     * @return
     */
    List<OriginalNewHouseCase> selectList(OriginalNewHouseCase originalNewHouseCase);

    /**
     * 分页总数
     *
     * @param originalNewHouseCase
     * @return
     */
    int selectCount(OriginalNewHouseCase originalNewHouseCase);

}
