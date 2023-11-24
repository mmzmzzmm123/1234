package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TMqttMsg;

/**
 * mqtt消息发送接收Mapper接口
 *
 * @author ruoyi
 * @date 2023-11-20
 */
public interface TMqttMsgMapper extends BaseMapper<TMqttMsg> {
    /**
     * 查询mqtt消息发送接收
     *
     * @param id mqtt消息发送接收主键
     * @return mqtt消息发送接收
     */
    public TMqttMsg selectTMqttMsgById(Long id);

    /**
     * 查询mqtt消息发送接收列表
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return mqtt消息发送接收集合
     */
    public List<TMqttMsg> selectTMqttMsgList(TMqttMsg tMqttMsg);

    /**
     * 新增mqtt消息发送接收
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return 结果
     */
    public int insertTMqttMsg(TMqttMsg tMqttMsg);

    /**
     * 修改mqtt消息发送接收
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return 结果
     */
    public int updateTMqttMsg(TMqttMsg tMqttMsg);

    /**
     * 删除mqtt消息发送接收
     *
     * @param id mqtt消息发送接收主键
     * @return 结果
     */
    public int deleteTMqttMsgById(Long id);

    /**
     * 批量删除mqtt消息发送接收
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTMqttMsgByIds(Long[] ids);
}
