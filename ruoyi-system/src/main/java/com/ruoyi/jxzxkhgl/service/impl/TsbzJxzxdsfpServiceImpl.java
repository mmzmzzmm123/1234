package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzJxzxdsfpMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxdsfp;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxdsfpService;

/**
 * 见习导师分配Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-10
 */
@Service
public class TsbzJxzxdsfpServiceImpl implements ITsbzJxzxdsfpService {
    @Autowired
    private TsbzJxzxdsfpMapper tsbzJxzxdsfpMapper;

    /**
     * 查询见习导师分配
     *
     * @param id 见习导师分配ID
     * @return 见习导师分配
     */
    @Override
    public TsbzJxzxdsfp selectTsbzJxzxdsfpById(Long id) {
        return tsbzJxzxdsfpMapper.selectTsbzJxzxdsfpById(id);
    }

    /**
     * 查询见习导师分配列表
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 见习导师分配
     */
    @Override
    public List<TsbzJxzxdsfp> selectTsbzJxzxdsfpList(TsbzJxzxdsfp tsbzJxzxdsfp) {
        return tsbzJxzxdsfpMapper.selectTsbzJxzxdsfpList(tsbzJxzxdsfp);
    }

    /**
     * 新增见习导师分配
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxdsfp(TsbzJxzxdsfp tsbzJxzxdsfp) {
        tsbzJxzxdsfp.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxdsfpMapper.insertTsbzJxzxdsfp(tsbzJxzxdsfp);
    }

    /**
     * 修改见习导师分配
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxdsfp(TsbzJxzxdsfp tsbzJxzxdsfp) {
        return tsbzJxzxdsfpMapper.updateTsbzJxzxdsfp(tsbzJxzxdsfp);
    }

    /**
     * 批量删除见习导师分配
     *
     * @param ids 需要删除的见习导师分配ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxdsfpByIds(Long[] ids) {
        return tsbzJxzxdsfpMapper.deleteTsbzJxzxdsfpByIds(ids);
    }

    /**
     * 删除见习导师分配信息
     *
     * @param id 见习导师分配ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxdsfpById(Long id) {
        return tsbzJxzxdsfpMapper.deleteTsbzJxzxdsfpById(id);
    }
}
