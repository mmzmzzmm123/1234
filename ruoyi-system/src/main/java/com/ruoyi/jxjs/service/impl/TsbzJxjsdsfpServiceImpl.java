package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxjsdsfpMapper;
import com.ruoyi.jxjs.domain.TsbzJxjsdsfp;
import com.ruoyi.jxjs.service.ITsbzJxjsdsfpService;

/**
 * 见习教师导师分配Service业务层处理
 *
 * @author tsbz
 * @date 2021-06-28
 */
@Service
public class TsbzJxjsdsfpServiceImpl implements ITsbzJxjsdsfpService {
    @Autowired
    private TsbzJxjsdsfpMapper tsbzJxjsdsfpMapper;

    /**
     * 查询见习教师导师分配
     *
     * @param id 见习教师导师分配ID
     * @return 见习教师导师分配
     */
    @Override
    public TsbzJxjsdsfp selectTsbzJxjsdsfpById(Long id) {
        return tsbzJxjsdsfpMapper.selectTsbzJxjsdsfpById(id);
    }

    /**
     * 查询见习教师导师分配列表
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 见习教师导师分配
     */
    @Override
    public List<TsbzJxjsdsfp> selectTsbzJxjsdsfpList(TsbzJxjsdsfp tsbzJxjsdsfp) {
        return tsbzJxjsdsfpMapper.selectTsbzJxjsdsfpList(tsbzJxjsdsfp);
    }

    /**
     * 查询见习教师导师分配列表
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 见习教师导师分配集合
     */
    @Override
    public List<TsbzJxjsdsfp> selectTsbzJxjsdsfpListExport(TsbzJxjsdsfp tsbzJxjsdsfp){
        return tsbzJxjsdsfpMapper.selectTsbzJxjsdsfpListExport(tsbzJxjsdsfp);
    }

    /**
     * 新增见习教师导师分配
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 结果
     */
    @Override
    public int insertTsbzJxjsdsfp(TsbzJxjsdsfp tsbzJxjsdsfp) {
        tsbzJxjsdsfp.setCreateTime(DateUtils.getNowDate());
        return tsbzJxjsdsfpMapper.insertTsbzJxjsdsfp(tsbzJxjsdsfp);
    }

    /**
     * 修改见习教师导师分配
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 结果
     */
    @Override
    public int updateTsbzJxjsdsfp(TsbzJxjsdsfp tsbzJxjsdsfp) {
        return tsbzJxjsdsfpMapper.updateTsbzJxjsdsfp(tsbzJxjsdsfp);
    }

    /**
     * 批量删除见习教师导师分配
     *
     * @param ids 需要删除的见习教师导师分配ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsdsfpByIds(Long[] ids) {
        return tsbzJxjsdsfpMapper.deleteTsbzJxjsdsfpByIds(ids);
    }

    /**
     * 删除见习教师导师分配信息
     *
     * @param id 见习教师导师分配ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsdsfpById(Long id) {
        return tsbzJxjsdsfpMapper.deleteTsbzJxjsdsfpById(id);
    }
}
