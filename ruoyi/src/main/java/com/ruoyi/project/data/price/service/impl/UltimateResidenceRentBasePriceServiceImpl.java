package com.ruoyi.project.data.price.service.impl;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IUltimateResidenceRentBasePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 核准住宅租赁基价 Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
public class UltimateResidenceRentBasePriceServiceImpl implements IUltimateResidenceRentBasePriceService {

    private static final Logger log = LoggerFactory.getLogger(UltimateResidenceRentBasePriceServiceImpl.class);

    @Autowired
    private UltimateResidenceRentPriceMapper ultimateResidenceRentPriceMapper;

    @Override
    public List<UltimateResidenceRentBasePrice> selectList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        return ultimateResidenceRentPriceMapper.selectPageList(ultimateResidenceRentBasePrice);
    }

    @Override
    public int selectCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        return ultimateResidenceRentPriceMapper.selectPageCount(ultimateResidenceRentBasePrice);
    }

    @Override
    public List<VueSelectModel> getYearMonth() {
        return ultimateResidenceRentPriceMapper.yearMonthList();
    }


}
