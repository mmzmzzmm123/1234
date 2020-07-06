package com.uvaluation.project.data.price.service.impl;

import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.uvaluation.project.data.price.mapper.ComputeResidenceRentPriceMapper;
import com.uvaluation.project.data.price.service.IComputeResidenceRentPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComputeResidenceRentPriceServiceImpl implements IComputeResidenceRentPriceService {

    private static final Logger log = LoggerFactory.getLogger(ComputeResidenceRentPriceServiceImpl.class);

    @Autowired
    private ComputeResidenceRentPriceMapper computeResidenceRentPriceMapper;

    @Override
    public List<ComputeResidenceRentBasePrice> selectPageList(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        return computeResidenceRentPriceMapper.selectPageList(computeResidenceRentBasePrice);
    }

    @Override
    public int selectPageCount(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        return computeResidenceRentPriceMapper.selectPageCount(computeResidenceRentBasePrice);
    }

    @Override
    public ComputeResidenceRentBasePrice selectById(String id) {
        return computeResidenceRentPriceMapper.selectById(id);
    }

    @Override
    public List<VueSelectModel> getYearMonth() {
        return computeResidenceRentPriceMapper.yearMonthList();
    }


}
