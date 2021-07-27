package com.ruoyi.project.system.service.impl;

import java.util.List;

import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.ByTeacherJbxxMapper;
import com.ruoyi.project.system.domain.ByTeacherJbxx;
import com.ruoyi.project.system.service.IByTeacherJbxxService;

/**
 * 教师基本信息Service业务层处理
 *
 * @author tsbz
 * @date 2020-04-21
 */
@Service
public class ByTeacherJbxxServiceImpl implements IByTeacherJbxxService {
    @Autowired
    private ByTeacherJbxxMapper byTeacherJbxxMapper;

    /**
     * 查询教师基本信息
     *
     * @param id 教师基本信息ID
     * @return 教师基本信息
     */
    @Override
    public ByTeacherJbxx selectByTeacherJbxxById(Long id) {
        return byTeacherJbxxMapper.selectByTeacherJbxxById(id);
    }

    /**
     * 查询教师基本信息列表
     *
     * @param byTeacherJbxx 教师基本信息
     * @return 教师基本信息
     */
    @Override
    @DataScope(deptAlias = "u")
    public List<ByTeacherJbxx> selectByTeacherJbxxList(ByTeacherJbxx byTeacherJbxx) {
        return byTeacherJbxxMapper.selectByTeacherJbxxList(byTeacherJbxx);
    }

    /**
     * 查询教师基本信息列表
     *
     * @param byTeacherJbxx 教师基本信息
     * @return 教师基本信息集合
     */
    @Override
    public List<ByTeacherJbxx> selectByTeacherBrithList(ByTeacherJbxx byTeacherJbxx) {
        return byTeacherJbxxMapper.selectByTeacherBrithList(byTeacherJbxx);
    }

    /**
     * 查询教师基本信息列表
     *
     * @return 教师基本信息集合
     */
    @Override
    @DataScope(deptAlias = "u")
    public List<ByTeacherJbxx> selectByTeacherJbGroupXw(ByTeacherJbxx byTeacherJbxx) {
        return byTeacherJbxxMapper.selectByTeacherJbGroupXw(byTeacherJbxx);
    }

    /**
     * 新增教师基本信息
     *
     * @param byTeacherJbxx 教师基本信息
     * @return 结果
     */
    @Override
    public int insertByTeacherJbxx(ByTeacherJbxx byTeacherJbxx) {
        return byTeacherJbxxMapper.insertByTeacherJbxx(byTeacherJbxx);
    }

    /**
     * 修改教师基本信息
     *
     * @param byTeacherJbxx 教师基本信息
     * @return 结果
     */
    @Override
    public int updateByTeacherJbxx(ByTeacherJbxx byTeacherJbxx) {
        return byTeacherJbxxMapper.updateByTeacherJbxx(byTeacherJbxx);
    }

    /**
     * 批量删除教师基本信息
     *
     * @param ids 需要删除的教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteByTeacherJbxxByIds(Long[] ids) {
        return byTeacherJbxxMapper.deleteByTeacherJbxxByIds(ids);
    }

    /**
     * 删除教师基本信息信息
     *
     * @param id 教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteByTeacherJbxxById(Long id) {
        return byTeacherJbxxMapper.deleteByTeacherJbxxById(id);
    }
}
