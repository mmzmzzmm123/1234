package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxjscjMapper;
import com.ruoyi.jxjs.domain.TsbzJxjscj;
import com.ruoyi.jxjs.service.ITsbzJxjscjService;

/**
 * 见习教师成绩Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-09
 */
@Service
public class TsbzJxjscjServiceImpl implements ITsbzJxjscjService {
    @Autowired
    private TsbzJxjscjMapper tsbzJxjscjMapper;

    /**
     * 查询见习教师成绩
     *
     * @param id 见习教师成绩ID
     * @return 见习教师成绩
     */
    @Override
    public TsbzJxjscj selectTsbzJxjscjById(Long id) {
        return tsbzJxjscjMapper.selectTsbzJxjscjById(id);
    }

    /**
     * 查询见习教师成绩列表
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 见习教师成绩
     */
    @Override
    public List<TsbzJxjscj> selectTsbzJxjscjList(TsbzJxjscj tsbzJxjscj) {
        return tsbzJxjscjMapper.selectTsbzJxjscjList(tsbzJxjscj);
    }

    /**
     * 新增见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    @Override
    public int insertTsbzJxjscj(TsbzJxjscj tsbzJxjscj) {
        tsbzJxjscj.setCreateTime(DateUtils.getNowDate());
        return tsbzJxjscjMapper.insertTsbzJxjscj(tsbzJxjscj);
    }

    /**
     * 修改见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    @Override
    public int updateTsbzJxjscj(TsbzJxjscj tsbzJxjscj) {
        return tsbzJxjscjMapper.updateTsbzJxjscj(tsbzJxjscj);
    }

    /**
     * 批量删除见习教师成绩
     *
     * @param ids 需要删除的见习教师成绩ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjscjByIds(Long[] ids) {
        return tsbzJxjscjMapper.deleteTsbzJxjscjByIds(ids);
    }

    /**
     * 删除见习教师成绩信息
     *
     * @param id 见习教师成绩ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjscjById(Long id) {
        return tsbzJxjscjMapper.deleteTsbzJxjscjById(id);
    }
}
