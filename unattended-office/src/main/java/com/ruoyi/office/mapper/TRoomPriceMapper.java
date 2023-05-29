package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TRoomPrice;

/**
 * 房间价格Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface TRoomPriceMapper extends BaseMapper<TRoomPrice> {
    /**
     * 查询房间价格
     *
     * @param id 房间价格主键
     * @return 房间价格
     */
    public TRoomPrice selectTRoomPriceById(Long id);

    /**
     * 查询房间价格列表
     *
     * @param tRoomPrice 房间价格
     * @return 房间价格集合
     */
    public List<TRoomPrice> selectTRoomPriceList(TRoomPrice tRoomPrice);

    /**
     * 新增房间价格
     *
     * @param tRoomPrice 房间价格
     * @return 结果
     */
    public int insertTRoomPrice(TRoomPrice tRoomPrice);

    /**
     * 修改房间价格
     *
     * @param tRoomPrice 房间价格
     * @return 结果
     */
    public int updateTRoomPrice(TRoomPrice tRoomPrice);

    /**
     * 删除房间价格
     *
     * @param id 房间价格主键
     * @return 结果
     */
    public int deleteTRoomPriceById(Long id);

    /**
     * 批量删除房间价格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTRoomPriceByIds(Long[] ids);
}
