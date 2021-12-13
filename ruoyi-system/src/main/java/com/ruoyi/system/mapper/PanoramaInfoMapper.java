package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PanoramaInfo;

/**
 * 全景图管理Mapper接口
 * 
 * @author ruoyi
 * @date 2021-11-01
 */
public interface PanoramaInfoMapper 
{
    /**
     * 查询全景图管理
     * 
     * @param id 全景图管理主键
     * @return 全景图管理
     */
    public PanoramaInfo selectPanoramaInfoById(Long id);

    /**
     * 查询全景图管理列表
     * 
     * @param panoramaInfo 全景图管理
     * @return 全景图管理集合
     */
    public List<PanoramaInfo> selectPanoramaInfoList(PanoramaInfo panoramaInfo);

    /**
     * 新增全景图管理
     * 
     * @param panoramaInfo 全景图管理
     * @return 结果
     */
    public int insertPanoramaInfo(PanoramaInfo panoramaInfo);

    /**
     * 修改全景图管理
     * 
     * @param panoramaInfo 全景图管理
     * @return 结果
     */
    public int updatePanoramaInfo(PanoramaInfo panoramaInfo);

    /**
     * 删除全景图管理
     * 
     * @param id 全景图管理主键
     * @return 结果
     */
    public int deletePanoramaInfoById(Long id);

    /**
     * 批量删除全景图管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePanoramaInfoByIds(Long[] ids);
}
