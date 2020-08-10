package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildLearndevelopmentFamilyMapper;
import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentFamily;
import com.ruoyi.project.benyi.service.IByChildLearndevelopmentFamilyService;

/**
 * 儿童学习与发展档案（家长）Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-10
 */
@Service
public class ByChildLearndevelopmentFamilyServiceImpl implements IByChildLearndevelopmentFamilyService {
    @Autowired
    private ByChildLearndevelopmentFamilyMapper byChildLearndevelopmentFamilyMapper;

    /**
     * 查询儿童学习与发展档案（家长）
     *
     * @param id 儿童学习与发展档案（家长）ID
     * @return 儿童学习与发展档案（家长）
     */
    @Override
    public ByChildLearndevelopmentFamily selectByChildLearndevelopmentFamilyById(Long id) {
        return byChildLearndevelopmentFamilyMapper.selectByChildLearndevelopmentFamilyById(id);
    }

    /**
     * 查询儿童学习与发展档案（家长）列表
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 儿童学习与发展档案（家长）
     */
    @Override
    public List<ByChildLearndevelopmentFamily> selectByChildLearndevelopmentFamilyList(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        return byChildLearndevelopmentFamilyMapper.selectByChildLearndevelopmentFamilyList(byChildLearndevelopmentFamily);
    }

    /**
     * 新增儿童学习与发展档案（家长）
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 结果
     */
    @Override
    public int insertByChildLearndevelopmentFamily(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        byChildLearndevelopmentFamily.setCreateTime(DateUtils.getNowDate());
        return byChildLearndevelopmentFamilyMapper.insertByChildLearndevelopmentFamily(byChildLearndevelopmentFamily);
    }

    /**
     * 修改儿童学习与发展档案（家长）
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 结果
     */
    @Override
    public int updateByChildLearndevelopmentFamily(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily) {
        return byChildLearndevelopmentFamilyMapper.updateByChildLearndevelopmentFamily(byChildLearndevelopmentFamily);
    }

    /**
     * 批量删除儿童学习与发展档案（家长）
     *
     * @param ids 需要删除的儿童学习与发展档案（家长）ID
     * @return 结果
     */
    @Override
    public int deleteByChildLearndevelopmentFamilyByIds(Long[] ids) {
        return byChildLearndevelopmentFamilyMapper.deleteByChildLearndevelopmentFamilyByIds(ids);
    }

    /**
     * 删除儿童学习与发展档案（家长）信息
     *
     * @param id 儿童学习与发展档案（家长）ID
     * @return 结果
     */
    @Override
    public int deleteByChildLearndevelopmentFamilyById(Long id) {
        return byChildLearndevelopmentFamilyMapper.deleteByChildLearndevelopmentFamilyById(id);
    }
}
