package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxzxmdMapper;
import com.ruoyi.jxjs.domain.TsbzJxzxmd;
import com.ruoyi.jxjs.service.ITsbzJxzxmdService;

/**
 * 见习之星名单Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-23
 */
@Service
public class TsbzJxzxmdServiceImpl implements ITsbzJxzxmdService {
    @Autowired
    private TsbzJxzxmdMapper tsbzJxzxmdMapper;

    /**
     * 查询见习之星名单
     *
     * @param id 见习之星名单ID
     * @return 见习之星名单
     */
    @Override
    public TsbzJxzxmd selectTsbzJxzxmdById(Long id) {
        return tsbzJxzxmdMapper.selectTsbzJxzxmdById(id);
    }

    /**
     * 查询见习之星名单列表
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 见习之星名单
     */
    @Override
    public List<TsbzJxzxmd> selectTsbzJxzxmdList(TsbzJxzxmd tsbzJxzxmd) {
        return tsbzJxzxmdMapper.selectTsbzJxzxmdList(tsbzJxzxmd);
    }

    /**
     * 新增见习之星名单
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxmd(TsbzJxzxmd tsbzJxzxmd) {
        tsbzJxzxmd.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxmdMapper.insertTsbzJxzxmd(tsbzJxzxmd);
    }

    /**
     * 修改见习之星名单
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxmd(TsbzJxzxmd tsbzJxzxmd) {
        return tsbzJxzxmdMapper.updateTsbzJxzxmd(tsbzJxzxmd);
    }

    /**
     * 批量删除见习之星名单
     *
     * @param ids 需要删除的见习之星名单ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxmdByIds(Long[] ids) {
        return tsbzJxzxmdMapper.deleteTsbzJxzxmdByIds(ids);
    }

    /**
     * 删除见习之星名单信息
     *
     * @param id 见习之星名单ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxmdById(Long id) {
        return tsbzJxzxmdMapper.deleteTsbzJxzxmdById(id);
    }

    /**
     * 查询见习之星名单列表
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 见习之星名单集合
     */
    @Override
    public List<TsbzJxzxmd> selectTsbzJxzxmdKhjdList(TsbzJxzxmd tsbzJxzxmd) {
        return tsbzJxzxmdMapper.selectTsbzJxzxmdKhjdList(tsbzJxzxmd);
    }
}
