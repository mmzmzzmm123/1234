package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentTeacher;

/**
 * 儿童学习与发展档案（教师）Mapper接口
 *
 * @author tsbz
 * @date 2020-08-10
 */
public interface ByChildLearndevelopmentTeacherMapper {
    /**
     * 查询儿童学习与发展档案（教师）
     *
     * @param id 儿童学习与发展档案（教师）ID
     * @return 儿童学习与发展档案（教师）
     */
    public ByChildLearndevelopmentTeacher selectByChildLearndevelopmentTeacherById(Long id);

    /**
     * 查询儿童学习与发展档案（教师）列表
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 儿童学习与发展档案（教师）集合
     */
    public List<ByChildLearndevelopmentTeacher> selectByChildLearndevelopmentTeacherList(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher);

    /**
     * 新增儿童学习与发展档案（教师）
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 结果
     */
    public int insertByChildLearndevelopmentTeacher(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher);

    /**
     * 修改儿童学习与发展档案（教师）
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 结果
     */
    public int updateByChildLearndevelopmentTeacher(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher);

    /**
     * 删除儿童学习与发展档案（教师）
     *
     * @param id 儿童学习与发展档案（教师）ID
     * @return 结果
     */
    public int deleteByChildLearndevelopmentTeacherById(Long id);

    /**
     * 批量删除儿童学习与发展档案（教师）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByChildLearndevelopmentTeacherByIds(Long[] ids);
}
