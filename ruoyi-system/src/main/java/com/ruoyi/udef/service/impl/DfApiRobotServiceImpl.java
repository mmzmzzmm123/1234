package com.ruoyi.udef.service.impl;

import java.util.List;

import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.udef.domain.DfEscapeSchedule;
import com.ruoyi.udef.mapper.DfApiRobotMapper;
import com.ruoyi.udef.domain.DfApiRobot;
import com.ruoyi.udef.service.IDfApiRobotService;

import static com.ruoyi.udef.Konst.*;

/**
 * 机器人Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class DfApiRobotServiceImpl implements IDfApiRobotService {
    @Autowired
    private DfApiRobotMapper dfApiRobotMapper;

    @Autowired
    private RobotServiceImpl robotService;

    /**
     * 查询机器人
     *
     * @param id 机器人主键
     * @return 机器人
     */
    @Override
    public DfApiRobot selectDfApiRobotById(Long id) {
        return dfApiRobotMapper.selectDfApiRobotById(id);
    }

    /**
     * 查询机器人列表
     *
     * @param dfApiRobot 机器人
     * @return 机器人
     */
    @Override
    public List<DfApiRobot> selectDfApiRobotList(DfApiRobot dfApiRobot) {
        return dfApiRobotMapper.selectDfApiRobotList(dfApiRobot);
    }

    /**
     * 新增机器人
     *
     * @param dfApiRobot 机器人
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDfApiRobot(DfApiRobot dfApiRobot) {
        dfApiRobot.setCreateTime(DateUtils.getNowDate());
        int rows = dfApiRobotMapper.insertDfApiRobot(dfApiRobot);
        insertDfEscapeSchedule(dfApiRobot);
        return rows;
    }

    /**
     * 修改机器人
     *
     * @param dfApiRobot 机器人
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDfApiRobot(DfApiRobot dfApiRobot) {
        dfApiRobot.setUpdateTime(DateUtils.getNowDate());
        dfApiRobotMapper.deleteDfEscapeScheduleByRobotId(dfApiRobot.getId());
        insertDfEscapeSchedule(dfApiRobot);
        return dfApiRobotMapper.updateDfApiRobot(dfApiRobot);
    }

    /**
     * 批量删除机器人
     *
     * @param ids 需要删除的机器人主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDfApiRobotByIds(Long[] ids) {
        dfApiRobotMapper.deleteDfEscapeScheduleByRobotIds(ids);
        return dfApiRobotMapper.deleteDfApiRobotByIds(ids);
    }

    /**
     * 删除机器人信息
     *
     * @param id 机器人主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDfApiRobotById(Long id) {
        dfApiRobotMapper.deleteDfEscapeScheduleByRobotId(id);
        return dfApiRobotMapper.deleteDfApiRobotById(id);
    }

    @Override
    public int optOnRobot(Long id, String opt) {
        if(opt.equals(R_STATUS_INIT)){//初始化机器人
            robotService.initRobotById(id);
        } else if(opt.equals(R_STATUS_STOPPED)){//启动机器人
            DfApiRobot robot = dfApiRobotMapper.selectDfApiRobotById(id);
            dfApiRobotMapper.updateDfApiRobot(robot.setStatus(R_STATUS_RUNNING));
        } else if(opt.equals(R_STATUS_RUNNING)){//停止机器人
            DfApiRobot robot = dfApiRobotMapper.selectDfApiRobotById(id);
            dfApiRobotMapper.updateDfApiRobot(robot.setStatus(R_STATUS_STOPPED));
        } else {
            throw new CustomException("暂不支持操作！");
        }
        return 1;
    }

    /**
     * 新增计划信息
     *
     * @param dfApiRobot 机器人对象
     */
    public void insertDfEscapeSchedule(DfApiRobot dfApiRobot) {
        List<DfEscapeSchedule> dfEscapeScheduleList = dfApiRobot.getDfEscapeScheduleList();
        Long id = dfApiRobot.getId();
        if(StringUtils.isNotNull(dfEscapeScheduleList)){
            List<DfEscapeSchedule> list = new ArrayList<DfEscapeSchedule>();
            for (DfEscapeSchedule dfEscapeSchedule : dfEscapeScheduleList) {
                dfEscapeSchedule.setRobotId(id);
                list.add(dfEscapeSchedule);
            }
            if(list.size() > 0){
                dfApiRobotMapper.batchDfEscapeSchedule(list);
            }
        }
    }
}
