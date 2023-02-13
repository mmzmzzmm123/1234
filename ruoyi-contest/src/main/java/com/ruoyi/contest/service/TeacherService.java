package com.ruoyi.contest.service;

import com.ruoyi.contest.domain.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教师信息表 服务类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface TeacherService extends IService<Teacher> {

    /**
     *
     * @param teacher
     * @return
     */
    List<Teacher> selectTeacherList(Teacher teacher);
}
