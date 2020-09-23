package com.ruoyi.project.system.service.impl;

import java.util.List;

import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.ByClassMapper;
import com.ruoyi.project.system.domain.ByClass;
import com.ruoyi.project.system.service.IByClassService;

/**
 * 班级信息Service业务层处理
 *
 * @author tsbz
 * @date 2020-04-14
 */
@Service
public class ByClassServiceImpl implements IByClassService {
    @Autowired
    private ByClassMapper byClassMapper;

    /**
     * 查询班级信息
     *
     * @param bjbh 班级信息ID
     * @return 班级信息
     */
    @Override
    public ByClass selectByClassById(String bjbh) {
        return byClassMapper.selectByClassById(bjbh);
    }

    @Override
    public ByClass selectByClassByUserId(ByClass byClass) {
        return byClassMapper.selectByClassByUserId(byClass);
    }

    /**
     * 查询班级信息列表
     *
     * @param byClass 班级信息
     * @return 班级信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<ByClass> selectByClassList(ByClass byClass) {
        return byClassMapper.selectByClassList(byClass);
    }

    /**
     * 新增班级信息
     *
     * @param byClass 班级信息
     * @return 结果
     */
    @Override
    public int insertByClass(ByClass byClass) {
        return byClassMapper.insertByClass(byClass);
    }

    /**
     * 修改班级信息
     *
     * @param byClass 班级信息
     * @return 结果
     */
    @Override
    public int updateByClass(ByClass byClass) {
        return byClassMapper.updateByClass(byClass);
    }

    /**
     * 批量删除班级信息
     *
     * @param bjbhs 需要删除的班级信息ID
     * @return 结果
     */
    @Override
    public int deleteByClassByIds(String[] bjbhs) {
        return byClassMapper.deleteByClassByIds(bjbhs);
    }

    /**
     * 删除班级信息信息
     *
     * @param bjbh 班级信息ID
     * @return 结果
     */
    @Override
    public int deleteByClassById(String bjbh) {
        return byClassMapper.deleteByClassById(bjbh);
    }

    /**
     * 查询班级信息列表
     *
     * @param byClass 班级信息
     * @return 班级信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<ByClass> selectststicstSchoolList(ByClass byClass) {
        return byClassMapper.selectststicstSchoolList(byClass);
    }
}