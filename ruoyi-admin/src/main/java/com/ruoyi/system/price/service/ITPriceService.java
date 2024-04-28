package com.ruoyi.system.price.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.price.domain.TPrice;

/**
 * 商品价格Service接口
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public interface ITPriceService
{
    /**
     * 查询商品价格
     *
     * @param id 商品价格主键
     * @return 商品价格
     */
    public TPrice selectTPriceById(Long id);

    /**
     * 查询商品价格列表
     *
     * @param tPrice 商品价格
     * @return 商品价格集合
     */
    public List<TPrice> selectTPriceList(TPrice tPrice);

    /**
     * 新增商品价格
     *
     * @param tPrice 商品价格
     * @return 结果
     */
    public int insertTPrice(TPrice tPrice);

    /**
     * 修改商品价格
     *
     * @param tPrice 商品价格
     * @return 结果
     */
    public int updateTPrice(TPrice tPrice);

    /**
     * 批量删除商品价格
     *
     * @param ids 需要删除的商品价格主键集合
     * @return 结果
     */
    public int deleteTPriceByIds(Long[] ids);

    /**
     * 删除商品价格信息
     *
     * @param id 商品价格主键
     * @return 结果
     */
    public int deleteTPriceById(Long id);

    //查询上次改价的价格数据
    TPrice queryLastTprice(Long productId);
    //查询loCreateTime之前最后一次改价的价格数据
    TPrice queryLastTprice(Long productId, Date loCreateTime);
}
