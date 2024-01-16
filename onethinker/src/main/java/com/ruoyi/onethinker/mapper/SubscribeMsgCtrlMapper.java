package com.ruoyi.onethinker.mapper;

import java.util.List;
import com.ruoyi.onethinker.domain.SubscribeMsgCtrl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 订阅消息控制Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
public interface SubscribeMsgCtrlMapper extends BaseMapper<SubscribeMsgCtrl>{
    /**
     * 查询订阅消息控制
     *
     * @param id 订阅消息控制主键
     * @return 订阅消息控制
     */
    public SubscribeMsgCtrl selectSubscribeMsgCtrlById(Long id);

    /**
     * 查询订阅消息控制列表
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 订阅消息控制集合
     */
    public List<SubscribeMsgCtrl> selectSubscribeMsgCtrlList(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 新增订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    public int insertSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 修改订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    public int updateSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 删除订阅消息控制
     *
     * @param id 订阅消息控制主键
     * @return 结果
     */
    public int deleteSubscribeMsgCtrlById(Long id);

    /**
     * 批量删除订阅消息控制
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubscribeMsgCtrlByIds(Long[] ids);
}
