package com.ruoyi.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hotel.mapper.ChinaCityListMapper;
import com.ruoyi.hotel.domain.ChinaCityList;
import com.ruoyi.hotel.service.IChinaCityListService;

/**
 * 中国城市列Service业务层处理
 * 
 * @author sucheng
 * @date 2020-12-28
 */
@Service
public class ChinaCityListServiceImpl implements IChinaCityListService 
{
    @Autowired
    private ChinaCityListMapper chinaCityListMapper;

	public List<String> getAllProvinces() {
		return chinaCityListMapper.getAllProvinces();
	}

	public List<String> selectAdm2ByAdm1(String adm1) {
		return chinaCityListMapper.selectAdm2ByAdm1(adm1);
	}
	
    /**
     * 查询中国城市列
     * 
     * @param locationId 中国城市列ID
     * @return 中国城市列
     */
    @Override
    public ChinaCityList selectChinaCityListById(Long locationId)
    {
        return chinaCityListMapper.selectChinaCityListById(locationId);
    }

    /**
     * 查询中国城市列列表
     * 
     * @param chinaCityList 中国城市列
     * @return 中国城市列
     */
    @Override
    public List<ChinaCityList> selectChinaCityListList(ChinaCityList chinaCityList)
    {
        return chinaCityListMapper.selectChinaCityListList(chinaCityList);
    }

    /**
     * 新增中国城市列
     * 
     * @param chinaCityList 中国城市列
     * @return 结果
     */
    @Override
    public int insertChinaCityList(ChinaCityList chinaCityList)
    {
        return chinaCityListMapper.insertChinaCityList(chinaCityList);
    }

    /**
     * 修改中国城市列
     * 
     * @param chinaCityList 中国城市列
     * @return 结果
     */
    @Override
    public int updateChinaCityList(ChinaCityList chinaCityList)
    {
        return chinaCityListMapper.updateChinaCityList(chinaCityList);
    }

    /**
     * 批量删除中国城市列
     * 
     * @param locationIds 需要删除的中国城市列ID
     * @return 结果
     */
    @Override
    public int deleteChinaCityListByIds(Long[] locationIds)
    {
        return chinaCityListMapper.deleteChinaCityListByIds(locationIds);
    }

    /**
     * 删除中国城市列信息
     * 
     * @param locationId 中国城市列ID
     * @return 结果
     */
    @Override
    public int deleteChinaCityListById(Long locationId)
    {
        return chinaCityListMapper.deleteChinaCityListById(locationId);
    }
}
