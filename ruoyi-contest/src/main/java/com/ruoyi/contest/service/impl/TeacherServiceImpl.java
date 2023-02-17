package com.ruoyi.contest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.contest.domain.Teacher;
import com.ruoyi.contest.mapper.TeacherMapper;
import com.ruoyi.contest.service.ITeacherService;
import com.ruoyi.system.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-16
 */
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService
{

    private final SysDeptMapper deptMapper;

    /**
     * 查询教师信息列表
     *
     * @param teacher 教师信息
     * @return 教师信息
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher)
    {
        return baseMapper.selectTeacherList(teacher);
    }

    @Override
    public List<Teacher> listWithDeptName(Teacher teacher) {
        List<Teacher> list = baseMapper.selectTeacherList(teacher);
        if (list!=null){
            for (Teacher t : list) {
                Map<String, Object> params=new HashMap<>();
                String deptName="";
                SysDept dept = deptMapper.selectDeptById(t.getDeptId());
                if (dept!=null){
                    deptName=dept.getDeptName();
                }
                params.put("deptName", deptName);
                t.setParams(params);
            }
        }
        return list;
    }

}
