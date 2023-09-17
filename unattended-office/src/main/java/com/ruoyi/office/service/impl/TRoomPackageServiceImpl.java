package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomPackageMapper;
import com.ruoyi.office.domain.TRoomPackage;
import com.ruoyi.office.service.ITRoomPackageService;

/**
 * 房间套餐价格Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-14
 */
@Service
public class TRoomPackageServiceImpl extends ServiceImpl<TRoomPackageMapper, TRoomPackage> implements ITRoomPackageService
{
    @Autowired
    private TRoomPackageMapper tRoomPackageMapper;

    /**
     * 查询房间套餐价格
     * 
     * @param id 房间套餐价格主键
     * @return 房间套餐价格
     */
    @Override
    public TRoomPackage selectTRoomPackageById(Long id)
    {
        return tRoomPackageMapper.selectTRoomPackageById(id);
    }

    /**
     * 查询房间套餐价格列表
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 房间套餐价格
     */
    @Override
    public List<TRoomPackage> selectTRoomPackageList(TRoomPackage tRoomPackage)
    {
        return tRoomPackageMapper.selectTRoomPackageList(tRoomPackage);
    }

    /**
     * 新增房间套餐价格
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 结果
     */
    @Override
    public int insertTRoomPackage(TRoomPackage tRoomPackage)
    {
        tRoomPackage.setCreateTime(DateUtils.getNowDate());
        return tRoomPackageMapper.insertTRoomPackage(tRoomPackage);
    }

    /**
     * 修改房间套餐价格
     * 
     * @param tRoomPackage 房间套餐价格
     * @return 结果
     */
    @Override
    public int updateTRoomPackage(TRoomPackage tRoomPackage)
    {
        tRoomPackage.setUpdateTime(DateUtils.getNowDate());
        return tRoomPackageMapper.updateTRoomPackage(tRoomPackage);
    }

    /**
     * 批量删除房间套餐价格
     * 
     * @param ids 需要删除的房间套餐价格主键
     * @return 结果
     */
    @Override
    public int deleteTRoomPackageByIds(Long[] ids)
    {
        return tRoomPackageMapper.deleteTRoomPackageByIds(ids);
    }

    /**
     * 删除房间套餐价格信息
     * 
     * @param id 房间套餐价格主键
     * @return 结果
     */
    @Override
    public int deleteTRoomPackageById(Long id)
    {
        return tRoomPackageMapper.deleteTRoomPackageById(id);
    }
}
