package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyykhpjjgMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyykhpjjg;
import com.ruoyi.jyykhgl.service.ITsbzJyykhpjjgService;

/**
 * 考核评价结果Service业务层处理
 *
 * @author tsbz
 * @date 2021-04-16
 */
@Service
public class TsbzJyykhpjjgServiceImpl implements ITsbzJyykhpjjgService {
    @Autowired
    private TsbzJyykhpjjgMapper tsbzJyykhpjjgMapper;

    /**
     * 查询考核评价结果
     *
     * @param id 考核评价结果ID
     * @return 考核评价结果
     */
    @Override
    public TsbzJyykhpjjg selectTsbzJyykhpjjgById(Long id) {
        return tsbzJyykhpjjgMapper.selectTsbzJyykhpjjgById(id);
    }

    /**
     * 查询考核评价结果列表
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 考核评价结果
     */
    @Override
    public List<TsbzJyykhpjjg> selectTsbzJyykhpjjgList(TsbzJyykhpjjg tsbzJyykhpjjg) {
        return tsbzJyykhpjjgMapper.selectTsbzJyykhpjjgList(tsbzJyykhpjjg);
    }

    /**
     * 新增考核评价结果
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 结果
     */
    @Override
    public int insertTsbzJyykhpjjg(TsbzJyykhpjjg tsbzJyykhpjjg) {
        tsbzJyykhpjjg.setCreateTime(DateUtils.getNowDate());
        return tsbzJyykhpjjgMapper.insertTsbzJyykhpjjg(tsbzJyykhpjjg);
    }

    /**
     * 修改考核评价结果
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 结果
     */
    @Override
    public int updateTsbzJyykhpjjg(TsbzJyykhpjjg tsbzJyykhpjjg) {
        return tsbzJyykhpjjgMapper.updateTsbzJyykhpjjg(tsbzJyykhpjjg);
    }

    /**
     * 批量删除考核评价结果
     *
     * @param ids 需要删除的考核评价结果ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyykhpjjgByIds(Long[] ids) {
        return tsbzJyykhpjjgMapper.deleteTsbzJyykhpjjgByIds(ids);
    }

    /**
     * 删除考核评价结果信息
     *
     * @param id 考核评价结果ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyykhpjjgById(Long id) {
        return tsbzJyykhpjjgMapper.deleteTsbzJyykhpjjgById(id);
    }
}
