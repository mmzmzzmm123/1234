package com.ruoyi.student.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.student.consts.StudentConstant;
import com.ruoyi.student.domain.StuStateHis;
import com.ruoyi.student.service.IStuStateHisService;
import com.ruoyi.system.service.ISysDictDataService;
import org.apache.poi.ss.formula.functions.T;
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
public class StuInfoServiceImpl<T extends StuInfo> implements IStuInfoService<T>
{
    @Autowired
    private StuInfoMapper stuInfoMapper;

    @Autowired
    private IStuStateHisService stuStateHisService;

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
        StuInfo stuInfo = new StuInfo();
        stuInfo.setStudentTag(StudentConstant.STUDENT_TAG_RETURN);
        List<StuInfo> list = stuInfoMapper.selectStuInfoList(stuInfo);
        stuStateHisService.insertHis(list);
        return stuInfoMapper.changeTagReturn2InSchool();
    }

    @Override
    public int changeTagLeave2Out() {
        StuInfo stuInfo = new StuInfo();
        stuInfo.setStudentTag(StudentConstant.STUDENT_TAG_LEVEL);
        List<StuInfo> list = stuInfoMapper.selectStuInfoList(stuInfo);
        stuStateHisService.insertHis(list);
        return stuInfoMapper.changeTagLeave2Out();
    }

    @Override
    public List<T> matchDict(List<T> stuInfoList) {
        for (StuInfo stuInfo : stuInfoList) {
            String sexName = DictUtils.getDictLabel("sys_user_sex", stuInfo.getStudentSex());
            stuInfo.setStudentSexName(sexName);
            String nationName = DictUtils.getDictLabel("nation", stuInfo.getNation());
            stuInfo.setNationName(nationName);
            String campusName = DictUtils.getDictLabel("campus", stuInfo.getCampus());
            stuInfo.setCampusName(campusName);
            String trainingLevelName = DictUtils.getDictLabel("training_level", stuInfo.getTrainingLevel());
            stuInfo.setTrainingLevelName(trainingLevelName);
            String studentTagName = DictUtils.getDictLabel("student_tag", stuInfo.getStudentTag());
            stuInfo.setStudentTagName(studentTagName);
            String accommodationName = DictUtils.getDictLabel("accommodation", stuInfo.getAccommodation());
            stuInfo.setAccommodationName(accommodationName);
            String accommodationParkName = DictUtils.getDictLabel("accommodation_park", stuInfo.getAccommodationPark());
            stuInfo.setAccommodationParkName(accommodationParkName);
            String isOnSchoolName = DictUtils.getDictLabel("sys_yes_no", stuInfo.getIsOnSchool());
            stuInfo.setIsOnSchoolName(isOnSchoolName);
            String controlLevelName = DictUtils.getDictLabel("control_level", stuInfo.getControlLevel());
            stuInfo.setControlLevelName(controlLevelName);
            String notSchoolReasonName = DictUtils.getDictLabel("not_school_reason", stuInfo.getNotSchoolReason());
            stuInfo.setNotSchoolReasonName(notSchoolReasonName);
            String placeToSchoolLevelName = DictUtils.getDictLabel("risk_level", stuInfo.getPlaceToSchoolLevel());
            stuInfo.setPlaceToSchoolLevelName(placeToSchoolLevelName);
            String placeToSchoolName = DictUtils.getDictLabel("place_to_school", stuInfo.getPlaceToSchool());
            stuInfo.setPlaceToSchoolName(placeToSchoolName);
            String placeToLeaveName = DictUtils.getDictLabel("place_to_leave", stuInfo.getPlaceToLeave());
            stuInfo.setPlaceToLeaveName(placeToLeaveName);
            String jointTypeName = DictUtils.getDictLabel("joint_type", stuInfo.getJointType());
            stuInfo.setJointTypeName(jointTypeName);
        }
        return stuInfoList;
    }
}
