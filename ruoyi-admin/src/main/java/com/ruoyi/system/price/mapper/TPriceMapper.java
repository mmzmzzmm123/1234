package com.ruoyi.system.price.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.price.domain.TPrice;
import org.apache.ibatis.annotations.Param;

/**
 * 商品价格Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public interface TPriceMapper
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
     * 删除商品价格
     *
     * @param id 商品价格主键
     * @return 结果
     */
    public int deleteTPriceById(Long id);

    /**
     * 批量删除商品价格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTPriceByIds(Long[] ids);

    public TPrice queryLastTprice(@Param("productId") Long productId, @Param("loCreateTime") Date loCreateTime);
    public List<TPrice> queryTpriceList(@Param("productId") Long productId, @Param("loCreateTime") Date loCreateTime);
}
