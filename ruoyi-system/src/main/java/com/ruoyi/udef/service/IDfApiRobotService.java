package com.ruoyi.udef.service;

import java.util.List;

import com.ruoyi.udef.domain.DfApiRobot;

/**
 * 机器人Service接口
 *
 * @author ruoyi
 * @date 2022-06-08
 */
public interface IDfApiRobotService {
    /**
     * 查询机器人
     *
     * @param id 机器人主键
     * @return 机器人
     */
    public DfApiRobot selectDfApiRobotById(Long id);

    /**
     * 查询机器人列表
     *
     * @param dfApiRobot 机器人
     * @return 机器人集合
     */
    public List<DfApiRobot> selectDfApiRobotList(DfApiRobot dfApiRobot);

    /**
     * 新增机器人
     *
     * @param dfApiRobot 机器人
     * @return 结果
     */
    public int insertDfApiRobot(DfApiRobot dfApiRobot);

    /**
     * 修改机器人
     *
     * @param dfApiRobot 机器人
     * @return 结果
     */
    public int updateDfApiRobot(DfApiRobot dfApiRobot);

    /**
     * 批量删除机器人
     *
     * @param ids 需要删除的机器人主键集合
     * @return 结果
     */
    public int deleteDfApiRobotByIds(Long[] ids);

    /**
     * 删除机器人信息
     *
     * @param id 机器人主键
     * @return 结果
     */
    public int deleteDfApiRobotById(Long id);


    /**
     * 操作机器人
     * @param id
     * @param opt
     * @return
     */
    public int optOnRobot(Long id, String opt);
}
