package com.ruoyi.project.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.BySchoolMapper;
import com.ruoyi.project.system.domain.BySchool;
import com.ruoyi.project.system.service.IBySchoolService;

/**
 * 幼儿园机构Service业务层处理
 *
 * @author tsbz
 * @date 2020-04-08
 */
@Service
public class BySchoolServiceImpl implements IBySchoolService {
    @Autowired
    private BySchoolMapper bySchoolMapper;

    /**
     * 查询幼儿园机构
     *
     * @param id 幼儿园机构ID
     * @return 幼儿园机构
     */
    @Override
    public BySchool selectBySchoolById(Long id) {
        return bySchoolMapper.selectBySchoolById(id);
    }

    /**
     * 查询距开通截止日期近15天的幼儿园机构列表
     *
     * @param bySchool 幼儿园机构
     * @return 幼儿园机构集合
     */
    @Override
    public List<BySchool> selectBySchoolWarnList(BySchool bySchool) {
        return bySchoolMapper.selectBySchoolWarnList(bySchool);
    }

    /**
     * 查询幼儿园机构列表
     *
     * @param bySchool 幼儿园机构
     * @return 幼儿园机构
     */
    @Override
    public List<BySchool> selectBySchoolList(BySchool bySchool) {
        return bySchoolMapper.selectBySchoolList(bySchool);
    }

    /**
     * 新增幼儿园机构
     *
     * @param bySchool 幼儿园机构
     * @return 结果
     */
    @Override
    public int insertBySchool(BySchool bySchool) {
        bySchool.setCreateTime(DateUtils.getNowDate());
        return bySchoolMapper.insertBySchool(bySchool);
    }

    /**
     * 修改幼儿园机构
     *
     * @param bySchool 幼儿园机构
     * @return 结果
     */
    @Override
    public int updateBySchool(BySchool bySchool) {
        return bySchoolMapper.updateBySchool(bySchool);
    }

    /**
     * 批量删除幼儿园机构
     *
     * @param ids 需要删除的幼儿园机构ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolByIds(Long[] ids) {
        return bySchoolMapper.deleteBySchoolByIds(ids);
    }

    /**
     * 删除幼儿园机构信息
     *
     * @param id 幼儿园机构ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolById(Long id) {
        return bySchoolMapper.deleteBySchoolById(id);
    }
}
