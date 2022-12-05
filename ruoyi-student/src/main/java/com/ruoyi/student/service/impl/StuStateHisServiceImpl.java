package com.ruoyi.student.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.student.domain.StuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StuStateHisMapper;
import com.ruoyi.student.domain.StuStateHis;
import com.ruoyi.student.service.IStuStateHisService;

/**
 * 学生历史状态Service业务层处理
 * 
 * @author wangzq
 * @date 2022-12-05
 */
@Service
public class StuStateHisServiceImpl implements IStuStateHisService 
{
    @Autowired
    private StuStateHisMapper stuStateHisMapper;

    /**
     * 查询学生历史状态
     * 
     * @param id 学生历史状态主键
     * @return 学生历史状态
     */
    @Override
    public StuStateHis selectStuStateHisById(Long id)
    {
        return stuStateHisMapper.selectStuStateHisById(id);
    }

    /**
     * 查询学生历史状态列表
     * 
     * @param stuStateHis 学生历史状态
     * @return 学生历史状态
     */
    @Override
    public List<StuStateHis> selectStuStateHisList(StuStateHis stuStateHis)
    {
        return stuStateHisMapper.selectStuStateHisList(stuStateHis);
    }

    /**
     * 新增学生历史状态
     * 
     * @param stuStateHis 学生历史状态
     * @return 结果
     */
    @Override
    public int insertStuStateHis(StuStateHis stuStateHis)
    {
        stuStateHis.setCreateTime(DateUtils.getNowDate());
        return stuStateHisMapper.insertStuStateHis(stuStateHis);
    }

    /**
     * 修改学生历史状态
     * 
     * @param stuStateHis 学生历史状态
     * @return 结果
     */
    @Override
    public int updateStuStateHis(StuStateHis stuStateHis)
    {
        stuStateHis.setUpdateTime(DateUtils.getNowDate());
        return stuStateHisMapper.updateStuStateHis(stuStateHis);
    }

    /**
     * 批量删除学生历史状态
     * 
     * @param ids 需要删除的学生历史状态主键
     * @return 结果
     */
    @Override
    public int deleteStuStateHisByIds(Long[] ids)
    {
        return stuStateHisMapper.deleteStuStateHisByIds(ids);
    }

    /**
     * 删除学生历史状态信息
     * 
     * @param id 学生历史状态主键
     * @return 结果
     */
    @Override
    public int deleteStuStateHisById(Long id)
    {
        return stuStateHisMapper.deleteStuStateHisById(id);
    }

    @Override
    public int insertHis(List<StuInfo> list) {
        if (list == null) {
            return 0;
        }
        StuStateHis stuStateHis = new StuStateHis();
        for (StuInfo stu : list) {
            stuStateHis.setStudentId(stu.getStudentId());
            stuStateHis.setHisState(stu.getStudentTag());
            stuStateHis.setHisStateDate(stu.getUpdateTime());
            if (stuStateHis.getHisStateDate() == null) {
                stuStateHis.setHisStateDate(DateUtils.addDays(new Date(), -1));
            }
            stuStateHisMapper.insertStuStateHis(stuStateHis);
        }
        return list.size();
    }
}
