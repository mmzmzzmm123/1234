package com.ruoyi.stu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stu.mapper.StuClsMapper;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.service.IStuClsService;

/**
 * 班级年级参数Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@Service
public class StuClsServiceImpl implements IStuClsService 
{
    @Autowired
    private StuClsMapper stuClsMapper;

    /**
     * 查询班级年级参数
     * 
     * @param id 班级年级参数主键
     * @return 班级年级参数
     */
    @Override
    public StuCls selectStuClsById(Long id)
    {
        return stuClsMapper.selectStuClsById(id);
    }

    /**
     * 查询班级年级参数列表
     * 
     * @param stuCls 班级年级参数
     * @return 班级年级参数
     */
    @Override
    public List<StuCls> selectStuClsList(StuCls stuCls)
    {
        return stuClsMapper.selectStuClsList(stuCls);
    }

    /**
     * 新增班级年级参数
     * 
     * @param stuCls 班级年级参数
     * @return 结果
     */
    @Override
    public int insertStuCls(StuCls stuCls)
    {
        return stuClsMapper.insertStuCls(stuCls);
    }

    /**
     * 修改班级年级参数
     * 
     * @param stuCls 班级年级参数
     * @return 结果
     */
    @Override
    public int updateStuCls(StuCls stuCls)
    {
        return stuClsMapper.updateStuCls(stuCls);
    }

    /**
     * 批量删除班级年级参数
     * 
     * @param ids 需要删除的班级年级参数主键
     * @return 结果
     */
    @Override
    public int deleteStuClsByIds(Long[] ids)
    {
        return stuClsMapper.deleteStuClsByIds(ids);
    }

    /**
     * 删除班级年级参数信息
     * 
     * @param id 班级年级参数主键
     * @return 结果
     */
    @Override
    public int deleteStuClsById(Long id)
    {
        return stuClsMapper.deleteStuClsById(id);
    }

    @Override
    public List<StuCls> selectAllClsYear() {
        return stuClsMapper.selectAllClsYear();
    }
}
