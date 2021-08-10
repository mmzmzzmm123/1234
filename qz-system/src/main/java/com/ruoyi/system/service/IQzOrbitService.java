package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QzOrbit;

/**
 * 圈子Service接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface IQzOrbitService 
{
    /**
     * 查询圈子
     * 
     * @param quanId 圈子主键
     * @return 圈子
     */
    public QzOrbit selectQzOrbitByQuanId(Long quanId);

    /**
     * 查询圈子列表
     * 
     * @param qzOrbit 圈子
     * @return 圈子集合
     */
    public List<QzOrbit> selectQzOrbitList(QzOrbit qzOrbit);

    /**
     * 新增圈子
     * 
     * @param qzOrbit 圈子
     * @return 结果
     */
    public int insertQzOrbit(QzOrbit qzOrbit);

    /**
     * 修改圈子
     * 
     * @param qzOrbit 圈子
     * @return 结果
     */
    public int updateQzOrbit(QzOrbit qzOrbit);

    /**
     * 批量删除圈子
     * 
     * @param quanIds 需要删除的圈子主键集合
     * @return 结果
     */
    public int deleteQzOrbitByQuanIds(Long[] quanIds);

    /**
     * 删除圈子信息
     * 
     * @param quanId 圈子主键
     * @return 结果
     */
    public int deleteQzOrbitByQuanId(Long quanId);
}
