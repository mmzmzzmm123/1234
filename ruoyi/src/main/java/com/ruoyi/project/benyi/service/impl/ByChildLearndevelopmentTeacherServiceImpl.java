package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildLearndevelopmentTeacherMapper;
import com.ruoyi.project.benyi.domain.ByChildLearndevelopmentTeacher;
import com.ruoyi.project.benyi.service.IByChildLearndevelopmentTeacherService;

/**
 * 儿童学习与发展档案（教师）Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-10
 */
@Service
public class ByChildLearndevelopmentTeacherServiceImpl implements IByChildLearndevelopmentTeacherService {
    @Autowired
    private ByChildLearndevelopmentTeacherMapper byChildLearndevelopmentTeacherMapper;

    /**
     * 查询儿童学习与发展档案（教师）
     *
     * @param id 儿童学习与发展档案（教师）ID
     * @return 儿童学习与发展档案（教师）
     */
    @Override
    public ByChildLearndevelopmentTeacher selectByChildLearndevelopmentTeacherById(Long id) {
        return byChildLearndevelopmentTeacherMapper.selectByChildLearndevelopmentTeacherById(id);
    }

    /**
     * 查询儿童学习与发展档案（教师）列表
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 儿童学习与发展档案（教师）
     */
    @Override
    public List<ByChildLearndevelopmentTeacher> selectByChildLearndevelopmentTeacherList(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        return byChildLearndevelopmentTeacherMapper.selectByChildLearndevelopmentTeacherList(byChildLearndevelopmentTeacher);
    }

    /**
     * 新增儿童学习与发展档案（教师）
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 结果
     */
    @Override
    public int insertByChildLearndevelopmentTeacher(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        byChildLearndevelopmentTeacher.setCreateTime(DateUtils.getNowDate());
        return byChildLearndevelopmentTeacherMapper.insertByChildLearndevelopmentTeacher(byChildLearndevelopmentTeacher);
    }

    /**
     * 修改儿童学习与发展档案（教师）
     *
     * @param byChildLearndevelopmentTeacher 儿童学习与发展档案（教师）
     * @return 结果
     */
    @Override
    public int updateByChildLearndevelopmentTeacher(ByChildLearndevelopmentTeacher byChildLearndevelopmentTeacher) {
        return byChildLearndevelopmentTeacherMapper.updateByChildLearndevelopmentTeacher(byChildLearndevelopmentTeacher);
    }

    /**
     * 批量删除儿童学习与发展档案（教师）
     *
     * @param ids 需要删除的儿童学习与发展档案（教师）ID
     * @return 结果
     */
    @Override
    public int deleteByChildLearndevelopmentTeacherByIds(Long[] ids) {
        return byChildLearndevelopmentTeacherMapper.deleteByChildLearndevelopmentTeacherByIds(ids);
    }

    /**
     * 删除儿童学习与发展档案（教师）信息
     *
     * @param id 儿童学习与发展档案（教师）ID
     * @return 结果
     */
    @Override
    public int deleteByChildLearndevelopmentTeacherById(Long id) {
        return byChildLearndevelopmentTeacherMapper.deleteByChildLearndevelopmentTeacherById(id);
    }
}
