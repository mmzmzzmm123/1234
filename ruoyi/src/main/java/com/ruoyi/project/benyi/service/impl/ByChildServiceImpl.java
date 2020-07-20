package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildMapper;
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;

/**
 * 幼儿信息Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-20
 */
@Service
public class ByChildServiceImpl implements IByChildService {
    @Autowired
    private ByChildMapper byChildMapper;

    /**
     * 查询幼儿信息
     *
     * @param id 幼儿信息ID
     * @return 幼儿信息
     */
    @Override
    public ByChild selectByChildById(Long id) {
        return byChildMapper.selectByChildById(id);
    }

    /**
     * 查询幼儿信息列表
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息
     */
    @Override
    public List<ByChild> selectByChildList(ByChild byChild) {
        return byChildMapper.selectByChildList(byChild);
    }

    /**
     * 新增幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    @Override
    public int insertByChild(ByChild byChild) {
        byChild.setCreateTime(DateUtils.getNowDate());
        return byChildMapper.insertByChild(byChild);
    }

    /**
     * 修改幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    @Override
    public int updateByChild(ByChild byChild) {
        return byChildMapper.updateByChild(byChild);
    }

    /**
     * 批量删除幼儿信息
     *
     * @param ids 需要删除的幼儿信息ID
     * @return 结果
     */
    @Override
    public int deleteByChildByIds(Long[] ids) {
        return byChildMapper.deleteByChildByIds(ids);
    }

    /**
     * 删除幼儿信息信息
     *
     * @param id 幼儿信息ID
     * @return 结果
     */
    @Override
    public int deleteByChildById(Long id) {
        return byChildMapper.deleteByChildById(id);
    }
}
