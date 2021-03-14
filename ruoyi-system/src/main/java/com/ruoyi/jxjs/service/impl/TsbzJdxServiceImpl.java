package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJdxMapper;
import com.ruoyi.jxjs.domain.TsbzJdx;
import com.ruoyi.jxjs.service.ITsbzJdxService;

/**
 * 基地校Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@Service
public class TsbzJdxServiceImpl implements ITsbzJdxService {
    @Autowired
    private TsbzJdxMapper tsbzJdxMapper;

    /**
     * 查询基地校
     *
     * @param id 基地校ID
     * @return 基地校
     */
    @Override
    public TsbzJdx selectTsbzJdxById(String id) {
        return tsbzJdxMapper.selectTsbzJdxById(id);
    }

    /**
     * 查询基地校
     *
     * @param jdxmc 基地校名称
     * @return 基地校
     */
    @Override
    public TsbzJdx selectTsbzJdxByJdxmc(String jdxmc) {
        return tsbzJdxMapper.selectTsbzJdxByJdxmc(jdxmc);
    }

    /**
     * 查询基地校列表
     *
     * @param tsbzJdx 基地校
     * @return 基地校
     */
    @Override
    public List<TsbzJdx> selectTsbzJdxList(TsbzJdx tsbzJdx) {
        return tsbzJdxMapper.selectTsbzJdxList(tsbzJdx);
    }

    /**
     * 新增基地校
     *
     * @param tsbzJdx 基地校
     * @return 结果
     */
    @Override
    public int insertTsbzJdx(TsbzJdx tsbzJdx) {
        tsbzJdx.setCreateTime(DateUtils.getNowDate());
        return tsbzJdxMapper.insertTsbzJdx(tsbzJdx);
    }

    /**
     * 修改基地校
     *
     * @param tsbzJdx 基地校
     * @return 结果
     */
    @Override
    public int updateTsbzJdx(TsbzJdx tsbzJdx) {
        return tsbzJdxMapper.updateTsbzJdx(tsbzJdx);
    }

    /**
     * 批量删除基地校
     *
     * @param ids 需要删除的基地校ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdxByIds(String[] ids) {
        return tsbzJdxMapper.deleteTsbzJdxByIds(ids);
    }

    /**
     * 删除基地校信息
     *
     * @param id 基地校ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdxById(String id) {
        return tsbzJdxMapper.deleteTsbzJdxById(id);
    }
}
