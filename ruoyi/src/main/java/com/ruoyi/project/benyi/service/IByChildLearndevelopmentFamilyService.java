package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentFamily;

/**
 * 儿童学习与发展档案（家长）Service接口
 *
 * @author tsbz
 * @date 2020-08-10
 */
public interface IByChildLearndevelopmentFamilyService {
    /**
     * 查询儿童学习与发展档案（家长）
     *
     * @param id 儿童学习与发展档案（家长）ID
     * @return 儿童学习与发展档案（家长）
     */
    public ByChildLearndevelopmentFamily selectByChildLearndevelopmentFamilyById(Long id);

    /**
     * 查询儿童学习与发展档案（家长）列表
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 儿童学习与发展档案（家长）集合
     */
    public List<ByChildLearndevelopmentFamily> selectByChildLearndevelopmentFamilyList(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily);

    /**
     * 新增儿童学习与发展档案（家长）
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 结果
     */
    public int insertByChildLearndevelopmentFamily(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily);

    /**
     * 修改儿童学习与发展档案（家长）
     *
     * @param byChildLearndevelopmentFamily 儿童学习与发展档案（家长）
     * @return 结果
     */
    public int updateByChildLearndevelopmentFamily(ByChildLearndevelopmentFamily byChildLearndevelopmentFamily);

    /**
     * 批量删除儿童学习与发展档案（家长）
     *
     * @param ids 需要删除的儿童学习与发展档案（家长）ID
     * @return 结果
     */
    public int deleteByChildLearndevelopmentFamilyByIds(Long[] ids);

    /**
     * 删除儿童学习与发展档案（家长）信息
     *
     * @param id 儿童学习与发展档案（家长）ID
     * @return 结果
     */
    public int deleteByChildLearndevelopmentFamilyById(Long id);
}
