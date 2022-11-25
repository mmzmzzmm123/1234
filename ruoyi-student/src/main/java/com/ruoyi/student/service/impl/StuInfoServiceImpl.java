package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StuInfoMapper;
import com.ruoyi.student.domain.StuInfo;
import com.ruoyi.student.service.IStuInfoService;

/**
 * 学生信息Service业务层处理
 * 
 * @author wangzq
 * @date 2022-11-21
 */
@Service
public class StuInfoServiceImpl implements IStuInfoService 
{
    @Autowired
    private StuInfoMapper stuInfoMapper;

    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    @Override
    public StuInfo selectStuInfoById(Long id)
    {
        return stuInfoMapper.selectStuInfoById(id);
    }

    /**
     * 查询学生信息列表
     * 
     * @param stuInfo 学生信息
     * @return 学生信息
     */
    @DataScope(deptAlias = "t")
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
        stuInfo.setCreateTime(DateUtils.getNowDate());
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
        stuInfo.setUpdateTime(DateUtils.getNowDate());
        return stuInfoMapper.updateStuInfo(stuInfo);
    }

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStuInfoByIds(Long[] ids)
    {
        return stuInfoMapper.deleteStuInfoByIds(ids);
    }

    /**
     * 删除学生信息信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStuInfoById(Long id)
    {
        return stuInfoMapper.deleteStuInfoById(id);
    }

    @Override
    public int changeTagReturn2InSchool() {
        return stuInfoMapper.changeTagReturn2InSchool();
    }

    @Override
    public int changeTagLeave2Out() {
        return stuInfoMapper.changeTagLeave2Out();
    }
}
