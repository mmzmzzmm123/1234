package com.ruoyi.project.data.price.service.impl;

import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IUltimateResidenceRentBasePriceService;
import com.ruoyi.project.system.service.impl.SysUserServiceImpl;
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
        return ultimateResidenceRentPriceMapper.selectListByRoute(ultimateResidenceRentBasePrice);
    }

    @Override
    public int selectCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        return ultimateResidenceRentPriceMapper.selectCountByRoute(ultimateResidenceRentBasePrice);
    }

    @Override
    public UltimateResidenceRentBasePrice selectById(Integer id) {
        return ultimateResidenceRentPriceMapper.selectByRoute(id);
    }

    @Override
    public int update(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice) {
        return ultimateResidenceRentPriceMapper.updateByRoute(ultimateResidenceRentBasePrice);
    }

    @Override
    public String batchImport(List<UltimateResidenceRentBasePrice> ultimateResidenceRentBasePrices, String operName) {
        return null;
    }
}
