package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDictDataService;
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

    @Autowired
    private ISysDictDataService sysDictDataService;

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

    @Override
    public List<StuInfo> matchDict(List<StuInfo> stuInfoList) {
        for (StuInfo stuInfo : stuInfoList) {
            String nationName = sysDictDataService.selectDictLabel("nation", stuInfo.getNation());
            stuInfo.setNationName(nationName);
            String campusName = sysDictDataService.selectDictLabel("campus", stuInfo.getCampus());
            stuInfo.setCampusName(campusName);
            String trainingLevelName = sysDictDataService.selectDictLabel("training_level", stuInfo.getTrainingLevel());
            stuInfo.setTrainingLevelName(trainingLevelName);
            String studentTagName = sysDictDataService.selectDictLabel("student_tag", stuInfo.getStudentTag());
            stuInfo.setStudentTagName(studentTagName);
            String accommodationName = sysDictDataService.selectDictLabel("accommodation", stuInfo.getAccommodation());
            stuInfo.setAccommodationName(accommodationName);
            String accommodationParkName = sysDictDataService.selectDictLabel("accommodation_park", stuInfo.getAccommodationPark());
            stuInfo.setAccommodationParkName(accommodationParkName);
            String isOnSchoolName = sysDictDataService.selectDictLabel("sys_yes_no", stuInfo.getIsOnSchool());
            stuInfo.setIsOnSchoolName(isOnSchoolName);
            String controlLevelName = sysDictDataService.selectDictLabel("control_level", stuInfo.getControlLevel());
            stuInfo.setControlLevelName(controlLevelName);
            String notSchoolReasonName = sysDictDataService.selectDictLabel("not_school_reason", stuInfo.getNotSchoolReason());
            stuInfo.setNotSchoolReasonName(notSchoolReasonName);
            String placeToSchoolLevelName = sysDictDataService.selectDictLabel("risk_level", stuInfo.getPlaceToSchoolLevel());
            stuInfo.setPlaceToSchoolLevelName(placeToSchoolLevelName);
            String placeToSchoolName = sysDictDataService.selectDictLabel("place_to_school", stuInfo.getPlaceToSchool());
            stuInfo.setPlaceToSchoolName(placeToSchoolName);
            String placeToLeaveName = sysDictDataService.selectDictLabel("place_to_leave", stuInfo.getPlaceToLeave());
            stuInfo.setPlaceToLeaveName(placeToLeaveName);
            String jointTypeName = sysDictDataService.selectDictLabel("joint_type", stuInfo.getJointType());
            stuInfo.setJointTypeName(jointTypeName);
        }
        return stuInfoList;
    }
}
