package com.ruoyi.stu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stu.mapper.StuInfoMapper;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.service.IStuInfoService;

/**
 * 学生信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@Service
public class StuInfoServiceImpl implements IStuInfoService 
{
    @Autowired
    private StuInfoMapper stuInfoMapper;

    /**
     * 查询学生信息
     * 
     * @param stuId 学生信息主键
     * @return 学生信息
     */
    @Override
    public StuInfo selectStuInfoByStuId(Long stuId)
    {
        return stuInfoMapper.selectStuInfoByStuId(stuId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param stuInfo 学生信息
     * @return 学生信息
     */
    @Override
    public List<StuInfo> selectStuInfoList(StuInfo stuInfo)
    {
        return stuInfoMapper.selectStuInfoList(stuInfo);
    }

    /**
     * 新增学生信息
     * 
     * @param stuInfo 学生信息
     * @return 结果
     */
    @Override
    public int insertStuInfo(StuInfo stuInfo)
    {
        return stuInfoMapper.insertStuInfo(stuInfo);
    }

    /**
     * 修改学生信息
     * 
     * @param stuInfo 学生信息
     * @return 结果
     */
    @Override
    public int updateStuInfo(StuInfo stuInfo)
    {
        return stuInfoMapper.updateStuInfo(stuInfo);
    }

    /**
     * 批量删除学生信息
     * 
     * @param stuIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStuInfoByStuIds(Long[] stuIds)
    {
        return stuInfoMapper.deleteStuInfoByStuIds(stuIds);
    }

    /**
     * 删除学生信息信息
     * 
     * @param stuId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStuInfoByStuId(Long stuId)
    {
        return stuInfoMapper.deleteStuInfoByStuId(stuId);
    }

    @Override
    public int selectStuInfoByStuNo(String stuNo) {
        return stuInfoMapper.selectStuInfoByStuNo(stuNo);
    }

    @Override
    public List<StuInfo> clsAndClsYear() {
        return stuInfoMapper.clsAndClsYear();
    }

    @Override
    public int selectStuNumByYear(String clsYear) {
        return stuInfoMapper.selectStuNumByYear(clsYear);
    }
}
