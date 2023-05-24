package com.ruoyi.stu.service;

import java.util.List;
import com.ruoyi.stu.domain.StuCls;

/**
 * 班级年级参数Service接口
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public interface IStuClsService 
{
    /**
     * 查询班级年级参数
     * 
     * @param id 班级年级参数主键
     * @return 班级年级参数
     */
    public StuCls selectStuClsById(Long id);

    /**
     * 查询班级年级参数列表
     * 
     * @param stuCls 班级年级参数
     * @return 班级年级参数集合
     */
    public List<StuCls> selectStuClsList(StuCls stuCls);

    /**
     * 新增班级年级参数
     * 
     * @param stuCls 班级年级参数
     * @return 结果
     */
    public int insertStuCls(StuCls stuCls);

    /**
     * 修改班级年级参数
     * 
     * @param stuCls 班级年级参数
     * @return 结果
     */
    public int updateStuCls(StuCls stuCls);

    /**
     * 批量删除班级年级参数
     * 
     * @param ids 需要删除的班级年级参数主键集合
     * @return 结果
     */
    public int deleteStuClsByIds(Long[] ids);

    /**
     * 删除班级年级参数信息
     * 
     * @param id 班级年级参数主键
     * @return 结果
     */
    public int deleteStuClsById(Long id);
}
