package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzOrbitMapper;
import com.ruoyi.system.domain.QzOrbit;
import com.ruoyi.system.service.IQzOrbitService;

/**
 * 圈子Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzOrbitServiceImpl implements IQzOrbitService 
{
    @Autowired
    private QzOrbitMapper qzOrbitMapper;

    /**
     * 查询圈子
     * 
     * @param quanId 圈子主键
     * @return 圈子
     */
    @Override
    public QzOrbit selectQzOrbitByQuanId(Long quanId)
    {
        return qzOrbitMapper.selectQzOrbitByQuanId(quanId);
    }

    /**
     * 查询圈子列表
     * 
     * @param qzOrbit 圈子
     * @return 圈子
     */
    @Override
    public List<QzOrbit> selectQzOrbitList(QzOrbit qzOrbit)
    {
        return qzOrbitMapper.selectQzOrbitList(qzOrbit);
    }

    /**
     * 新增圈子
     * 
     * @param qzOrbit 圈子
     * @return 结果
     */
    @Override
    public int insertQzOrbit(QzOrbit qzOrbit)
    {
        qzOrbit.setCreateTime(DateUtils.getNowDate());
        return qzOrbitMapper.insertQzOrbit(qzOrbit);
    }

    /**
     * 修改圈子
     * 
     * @param qzOrbit 圈子
     * @return 结果
     */
    @Override
    public int updateQzOrbit(QzOrbit qzOrbit)
    {
        qzOrbit.setUpdateTime(DateUtils.getNowDate());
        return qzOrbitMapper.updateQzOrbit(qzOrbit);
    }

    /**
     * 批量删除圈子
     * 
     * @param quanIds 需要删除的圈子主键
     * @return 结果
     */
    @Override
    public int deleteQzOrbitByQuanIds(Long[] quanIds)
    {
        return qzOrbitMapper.deleteQzOrbitByQuanIds(quanIds);
    }

    /**
     * 删除圈子信息
     * 
     * @param quanId 圈子主键
     * @return 结果
     */
    @Override
    public int deleteQzOrbitByQuanId(Long quanId)
    {
        return qzOrbitMapper.deleteQzOrbitByQuanId(quanId);
    }
}
