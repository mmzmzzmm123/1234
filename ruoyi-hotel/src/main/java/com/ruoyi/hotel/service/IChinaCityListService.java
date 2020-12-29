package com.ruoyi.hotel.service;

import java.util.List;

import com.ruoyi.hotel.domain.ChinaCityList;

/**
 * 中国城市列Service接口
 * 
 * @author sucheng
 * @date 2020-12-28
 */
public interface IChinaCityListService 
{
	public List<String> getAllProvinces();
	
	public List<String> selectAdm2ByAdm1(String adm1);
	
    /**
     * 查询中国城市列
     * 
     * @param locationId 中国城市列ID
     * @return 中国城市列
     */
    public ChinaCityList selectChinaCityListById(Long locationId);

    /**
     * 查询中国城市列列表
     * 
     * @param chinaCityList 中国城市列
     * @return 中国城市列集合
     */
    public List<ChinaCityList> selectChinaCityListList(ChinaCityList chinaCityList);

    /**
     * 新增中国城市列
     * 
     * @param chinaCityList 中国城市列
     * @return 结果
     */
    public int insertChinaCityList(ChinaCityList chinaCityList);

    /**
     * 修改中国城市列
     * 
     * @param chinaCityList 中国城市列
     * @return 结果
     */
    public int updateChinaCityList(ChinaCityList chinaCityList);

    /**
     * 批量删除中国城市列
     * 
     * @param locationIds 需要删除的中国城市列ID
     * @return 结果
     */
    public int deleteChinaCityListByIds(Long[] locationIds);

    /**
     * 删除中国城市列信息
     * 
     * @param locationId 中国城市列ID
     * @return 结果
     */
    public int deleteChinaCityListById(Long locationId);
}
