package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Panorama;
import com.ruoyi.system.domain.vo.IdNameVo;

/**
 * 全景管理Service接口
 *
 * @author ruoyi
 * @date 2021-11-02
 */
public interface IPanoramaService
{
    /**
     * 查询全景管理
     *
     * @param id 全景管理主键
     * @return 全景管理
     */
    public Panorama selectPanoramaById(Long id);

    /**
     * 查询全景管理列表
     *
     * @param panorama 全景管理
     * @return 全景管理集合
     */
    public List<Panorama> selectPanoramaList(Panorama panorama);

    /**
     * 新增全景管理
     *
     * @param panorama 全景管理
     * @return 结果
     */
    public int insertPanorama(Panorama panorama);

    /**
     * 修改全景管理
     *
     * @param panorama 全景管理
     * @return 结果
     */
    public int updatePanorama(Panorama panorama);

    /**
     * 批量删除全景管理
     *
     * @param ids 需要删除的全景管理主键集合
     * @return 结果
     */
    public int deletePanoramaByIds(Long[] ids);

    /**
     * 删除全景管理信息
     *
     * @param id 全景管理主键
     * @return 结果
     */
    public int deletePanoramaById(Long id);

    List<IdNameVo> getIdNames();
}
