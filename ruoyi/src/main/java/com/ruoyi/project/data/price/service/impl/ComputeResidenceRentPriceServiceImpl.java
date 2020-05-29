package com.ruoyi.project.data.price.service.impl;

import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.ComputeResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IComputeResidenceRentPriceService;
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
public class ComputeResidenceRentPriceServiceImpl implements IComputeResidenceRentPriceService {

    private static final Logger log = LoggerFactory.getLogger(ComputeResidenceRentPriceServiceImpl.class);

    @Autowired
    private ComputeResidenceRentPriceMapper computeResidenceRentPriceMapper;

    @Override
    public List<ComputeResidenceRentBasePrice> selectList(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        return computeResidenceRentPriceMapper.selectComputeResidenceRentBasePriceList(computeResidenceRentBasePrice);
    }

    @Override
    public int selectCount(ComputeResidenceRentBasePrice computeResidenceRentBasePrice) {
        return computeResidenceRentPriceMapper.selectComputeResidenceRentBasePriceListCount(computeResidenceRentBasePrice);
    }

    @Override
    public ComputeResidenceRentBasePrice selectById(String id) {
        return computeResidenceRentPriceMapper.selectComputeResidenceRentBasePriceById(id);
    }

    @Override
    public int update(ComputeResidenceRentBasePrice officeBasePriceUltimate) {
        return 0;
    }

    @Override
    public String batchImport(List<ComputeResidenceRentBasePrice> officeBasePriceUltimates, String operName) {
        return null;
    }
}
