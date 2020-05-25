package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByLecturerMapper;
import com.ruoyi.project.benyi.domain.ByLecturer;
import com.ruoyi.project.benyi.service.IByLecturerService;

/**
 * 讲师Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-25
 */
@Service
public class ByLecturerServiceImpl implements IByLecturerService 
{
    @Autowired
    private ByLecturerMapper byLecturerMapper;

    /**
     * 查询讲师
     * 
     * @param id 讲师ID
     * @return 讲师
     */
    @Override
    public ByLecturer selectByLecturerById(Long id)
    {
        return byLecturerMapper.selectByLecturerById(id);
    }

    /**
     * 查询讲师列表
     * 
     * @param byLecturer 讲师
     * @return 讲师
     */
    @Override
    public List<ByLecturer> selectByLecturerList(ByLecturer byLecturer)
    {
        return byLecturerMapper.selectByLecturerList(byLecturer);
    }

    /**
     * 新增讲师
     * 
     * @param byLecturer 讲师
     * @return 结果
     */
    @Override
    public int insertByLecturer(ByLecturer byLecturer)
    {
        return byLecturerMapper.insertByLecturer(byLecturer);
    }

    /**
     * 修改讲师
     * 
     * @param byLecturer 讲师
     * @return 结果
     */
    @Override
    public int updateByLecturer(ByLecturer byLecturer)
    {
        return byLecturerMapper.updateByLecturer(byLecturer);
    }

    /**
     * 批量删除讲师
     * 
     * @param ids 需要删除的讲师ID
     * @return 结果
     */
    @Override
    public int deleteByLecturerByIds(Long[] ids)
    {
        return byLecturerMapper.deleteByLecturerByIds(ids);
    }

    /**
     * 删除讲师信息
     * 
     * @param id 讲师ID
     * @return 结果
     */
    @Override
    public int deleteByLecturerById(Long id)
    {
        return byLecturerMapper.deleteByLecturerById(id);
    }
}
