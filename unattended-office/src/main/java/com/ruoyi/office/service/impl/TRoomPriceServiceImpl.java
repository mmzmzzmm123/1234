package com.ruoyi.office.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomPriceMapper;
import com.ruoyi.office.domain.TRoomPrice;
import com.ruoyi.office.service.ITRoomPriceService;

/**
 * 房间价格Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TRoomPriceServiceImpl extends ServiceImpl<TRoomPriceMapper, TRoomPrice> implements ITRoomPriceService {
    @Autowired
    private TRoomPriceMapper tRoomPriceMapper;

    /**
     * 查询房间价格
     *
     * @param id 房间价格主键
     * @return 房间价格
     */
    @Override
    public TRoomPrice selectTRoomPriceById(Long id) {
        return tRoomPriceMapper.selectTRoomPriceById(id);
    }

    /**
     * 查询房间价格列表
     *
     * @param tRoomPrice 房间价格
     * @return 房间价格
     */
    @Override
    public List<TRoomPrice> selectTRoomPriceList(TRoomPrice tRoomPrice) {
        return tRoomPriceMapper.selectTRoomPriceList(tRoomPrice);
    }

    /**
     * 新增房间价格
     *
     * @param tRoomPrice 房间价格
     * @return 结果
     */
    @Override
    public int insertTRoomPrice(TRoomPrice tRoomPrice) {
        tRoomPrice.setCreateTime(DateUtils.getNowDate());

        QueryWrapper<TRoomPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TRoomPrice::getRoomId, tRoomPrice.getRoomId());
        final List<TRoomPrice> tRoomPrices = baseMapper.selectList(queryWrapper);
        for (TRoomPrice roomPrice : tRoomPrices) {
            if (roomPrice.getStartTime().isBefore(tRoomPrice.getStopTime()) || roomPrice.getStopTime().isAfter(tRoomPrice.getStartTime()))
                throw new ServiceException("收费标准时间段交叉");
        }
        return tRoomPriceMapper.insertTRoomPrice(tRoomPrice);
    }

    /**
     * 修改房间价格
     *
     * @param tRoomPrice 房间价格
     * @return 结果
     */
    @Override
    public int updateTRoomPrice(TRoomPrice tRoomPrice) {
        tRoomPrice.setUpdateTime(DateUtils.getNowDate());
        return tRoomPriceMapper.updateTRoomPrice(tRoomPrice);
    }

    /**
     * 批量删除房间价格
     *
     * @param ids 需要删除的房间价格主键
     * @return 结果
     */
    @Override
    public int deleteTRoomPriceByIds(Long[] ids) {
        return tRoomPriceMapper.deleteTRoomPriceByIds(ids);
    }

    /**
     * 删除房间价格信息
     *
     * @param id 房间价格主键
     * @return 结果
     */
    @Override
    public int deleteTRoomPriceById(Long id) {
        return tRoomPriceMapper.deleteTRoomPriceById(id);
    }
}
