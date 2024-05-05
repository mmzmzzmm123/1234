package com.ruoyi.system.price.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.price.mapper.TPriceMapper;
import com.ruoyi.system.price.domain.TPrice;
import com.ruoyi.system.price.service.ITPriceService;

/**
 * 商品价格Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@Service
public class TPriceServiceImpl implements ITPriceService
{
    @Autowired
    private TPriceMapper tPriceMapper;

    /**
     * 查询商品价格
     *
     * @param id 商品价格主键
     * @return 商品价格
     */
    @Override
    public TPrice selectTPriceById(Long id)
    {
        return tPriceMapper.selectTPriceById(id);
    }

    /**
     * 查询商品价格列表
     *
     * @param tPrice 商品价格
     * @return 商品价格
     */
    @Override
    public List<TPrice> selectTPriceList(TPrice tPrice)
    {
        return tPriceMapper.selectTPriceList(tPrice);
    }

    /**
     * 新增商品价格
     *
     * @param tPrice 商品价格
     * @return 结果
     */
    @Override
    public int insertTPrice(TPrice tPrice)
    {
        tPrice.setCreateTime(DateUtils.getNowDate());
        return tPriceMapper.insertTPrice(tPrice);
    }

    /**
     * 修改商品价格
     *
     * @param tPrice 商品价格
     * @return 结果
     */
    @Override
    public int updateTPrice(TPrice tPrice)
    {
        tPrice.setUpdateTime(DateUtils.getNowDate());
        return tPriceMapper.updateTPrice(tPrice);
    }

    /**
     * 批量删除商品价格
     *
     * @param ids 需要删除的商品价格主键
     * @return 结果
     */
    @Override
    public int deleteTPriceByIds(Long[] ids)
    {
        return tPriceMapper.deleteTPriceByIds(ids);
    }

    /**
     * 删除商品价格信息
     *
     * @param id 商品价格主键
     * @return 结果
     */
    @Override
    public int deleteTPriceById(Long id)
    {
        return tPriceMapper.deleteTPriceById(id);
    }

    @Override
    public TPrice queryLastTprice(Long productId) {
        return tPriceMapper.queryLastTprice(productId, DateUtil.beginOfDay(new Date()));
    }

    @Override
    public TPrice queryLastTprice(Long productId, Date loCreateTime) {
        return tPriceMapper.queryLastTprice(productId,loCreateTime);
    }

    @Override
    public List<TPrice> queryTpriceList(Long productId, Date loCreateTime) {
        return tPriceMapper.queryTpriceList(productId,loCreateTime);
    }
}
