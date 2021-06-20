package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByFamilyeduMapper;
import com.ruoyi.project.benyi.domain.ByFamilyedu;
import com.ruoyi.project.benyi.service.IByFamilyeduService;

/**
 * 家庭教育Service业务层处理
 *
 * @author tsbz
 * @date 2021-06-20
 */
@Service
public class ByFamilyeduServiceImpl implements IByFamilyeduService {
    @Autowired
    private ByFamilyeduMapper byFamilyeduMapper;

    /**
     * 查询家庭教育
     *
     * @param id 家庭教育ID
     * @return 家庭教育
     */
    @Override
    public ByFamilyedu selectByFamilyeduById(Long id) {
        return byFamilyeduMapper.selectByFamilyeduById(id);
    }

    /**
     * 查询家庭教育列表
     *
     * @param byFamilyedu 家庭教育
     * @return 家庭教育
     */
    @Override
    public List<ByFamilyedu> selectByFamilyeduList(ByFamilyedu byFamilyedu) {
        return byFamilyeduMapper.selectByFamilyeduList(byFamilyedu);
    }

    /**
     * 新增家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    @Override
    public int insertByFamilyedu(ByFamilyedu byFamilyedu) {
        byFamilyedu.setCreateTime(DateUtils.getNowDate());
        return byFamilyeduMapper.insertByFamilyedu(byFamilyedu);
    }

    /**
     * 修改家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    @Override
    public int updateByFamilyedu(ByFamilyedu byFamilyedu) {
        return byFamilyeduMapper.updateByFamilyedu(byFamilyedu);
    }

    /**
     * 批量删除家庭教育
     *
     * @param ids 需要删除的家庭教育ID
     * @return 结果
     */
    @Override
    public int deleteByFamilyeduByIds(Long[] ids) {
        return byFamilyeduMapper.deleteByFamilyeduByIds(ids);
    }

    /**
     * 删除家庭教育信息
     *
     * @param id 家庭教育ID
     * @return 结果
     */
    @Override
    public int deleteByFamilyeduById(Long id) {
        return byFamilyeduMapper.deleteByFamilyeduById(id);
    }
}
