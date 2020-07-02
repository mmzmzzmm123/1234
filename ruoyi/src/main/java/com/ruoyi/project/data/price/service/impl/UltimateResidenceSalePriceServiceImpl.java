package com.ruoyi.project.data.price.service.impl;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceSaleBasePrice;
import com.ruoyi.project.data.price.mapper.ComputeResidenceSalePriceMapper;
import com.ruoyi.project.data.price.mapper.UltimateResidenceSalePriceMapper;
import com.ruoyi.project.data.price.service.IComputeResidenceSalePriceService;
import com.ruoyi.project.data.price.service.IUltimateResidenceSalePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计算住宅基价Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
public class UltimateResidenceSalePriceServiceImpl implements IUltimateResidenceSalePriceService {

    @Autowired
    private UltimateResidenceSalePriceMapper ultimateResidenceSalePriceMapper;

    @Override
    public List<UltimateResidenceSaleBasePrice> selectList(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice) {
        return ultimateResidenceSalePriceMapper.selectPageList(ultimateResidenceSaleBasePrice);
    }

    @Override
    public int selectCount(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice) {
        return ultimateResidenceSalePriceMapper.selectPageCount(ultimateResidenceSaleBasePrice);
    }

    @Override
    public List<VueSelectModel> getYearMonth() {
        return ultimateResidenceSalePriceMapper.yearMonthList();
    }

//    private static final Logger log = LoggerFactory.getLogger(UltimateResidenceSalePriceServiceImpl.class);
//
//    @Autowired
//    private ComputeResidenceSalePriceMapper computeResidenceSalePriceMapper;
//
//    @Override
//    public List<ComputeResidenceSaleBasePrice> selectList(ComputeResidenceSaleBasePrice
//    computeResidenceSaleBasePrice) {
//        return computeResidenceSalePriceMapper.selectPageList(computeResidenceSaleBasePrice);
//    }
//
//    @Override
//    public int selectCount(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice) {
//        return computeResidenceSalePriceMapper.selectPageCount(computeResidenceSaleBasePrice);
//    }
//
//    @Override
//    public List<VueSelectModel> getYearMonth() {
//        return computeResidenceSalePriceMapper.yearMonthList();
//    }
}
