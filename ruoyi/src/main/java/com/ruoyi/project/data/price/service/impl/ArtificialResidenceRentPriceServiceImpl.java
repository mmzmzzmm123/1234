package com.ruoyi.project.data.price.service.impl;

import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.ArtificialResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
public class ArtificialResidenceRentPriceServiceImpl implements IArtificialResidenceRentPriceService {

    private static final Logger log = LoggerFactory.getLogger(ArtificialResidenceRentPriceServiceImpl.class);

    @Autowired
    private ArtificialResidenceRentPriceMapper artificialResidenceRentPriceMapper;

    @Override
    public List<ArtificialResidenceRentBasePrice> selectList(ArtificialResidenceRentBasePrice
    ArtificialResidenceRentPrice) {
        return artificialResidenceRentPriceMapper.selectArtificialResidenceRentBasePriceList
        (ArtificialResidenceRentPrice);
    }

    @Override
    public int selectCount(ArtificialResidenceRentBasePrice ArtificialResidenceRentPrice) {
        return artificialResidenceRentPriceMapper.selectArtificialResidenceRentBasePriceListCount
        (ArtificialResidenceRentPrice);
    }
}