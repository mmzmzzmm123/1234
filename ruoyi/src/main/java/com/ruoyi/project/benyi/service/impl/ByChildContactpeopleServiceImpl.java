package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildContactpeopleMapper;
import com.ruoyi.project.benyi.domain.ByChildContactpeople;
import com.ruoyi.project.benyi.service.IByChildContactpeopleService;

/**
 * 幼儿紧急情况联系人Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-13
 */
@Service
public class ByChildContactpeopleServiceImpl implements IByChildContactpeopleService {
    @Autowired
    private ByChildContactpeopleMapper byChildContactpeopleMapper;

    /**
     * 查询幼儿紧急情况联系人
     *
     * @param id 幼儿紧急情况联系人ID
     * @return 幼儿紧急情况联系人
     */
    @Override
    public ByChildContactpeople selectByChildContactpeopleById(Long id) {
        return byChildContactpeopleMapper.selectByChildContactpeopleById(id);
    }

    /**
     * 查询幼儿紧急情况联系人列表
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 幼儿紧急情况联系人
     */
    @Override
    public List<ByChildContactpeople> selectByChildContactpeopleList(ByChildContactpeople byChildContactpeople) {
        return byChildContactpeopleMapper.selectByChildContactpeopleList(byChildContactpeople);
    }

    /**
     * 新增幼儿紧急情况联系人
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 结果
     */
    @Override
    public int insertByChildContactpeople(ByChildContactpeople byChildContactpeople) {
        byChildContactpeople.setCreateTime(DateUtils.getNowDate());
        return byChildContactpeopleMapper.insertByChildContactpeople(byChildContactpeople);
    }

    /**
     * 修改幼儿紧急情况联系人
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 结果
     */
    @Override
    public int updateByChildContactpeople(ByChildContactpeople byChildContactpeople) {
        return byChildContactpeopleMapper.updateByChildContactpeople(byChildContactpeople);
    }

    /**
     * 批量删除幼儿紧急情况联系人
     *
     * @param ids 需要删除的幼儿紧急情况联系人ID
     * @return 结果
     */
    @Override
    public int deleteByChildContactpeopleByIds(Long[] ids) {
        return byChildContactpeopleMapper.deleteByChildContactpeopleByIds(ids);
    }

    /**
     * 删除幼儿紧急情况联系人信息
     *
     * @param id 幼儿紧急情况联系人ID
     * @return 结果
     */
    @Override
    public int deleteByChildContactpeopleById(Long id) {
        return byChildContactpeopleMapper.deleteByChildContactpeopleById(id);
    }

    /**
     * 批量删除幼儿紧急情况联系人
     *
     * @param childIds 需要删除的幼儿紧急情况联系人ID
     * @return 结果
     */
    @Override
    public int deleteByChildContactpeopleByChildIds(Long[] childIds) {
        return byChildContactpeopleMapper.deleteByChildContactpeopleByChildIds(childIds);
    }
}
