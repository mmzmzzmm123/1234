package com.onethinker.onethinker.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.onethinker.mapper.SubscribeMsgSendResultMapper;
import com.onethinker.onethinker.domain.SubscribeMsgSendResult;
import com.onethinker.onethinker.service.ISubscribeMsgSendResultService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 订阅消息发送结果Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
@Service
@Log4j2
public class SubscribeMsgSendResultServiceImpl extends ServiceImpl<SubscribeMsgSendResultMapper,SubscribeMsgSendResult> implements ISubscribeMsgSendResultService {
    @Resource
    private SubscribeMsgSendResultMapper subscribeMsgSendResultMapper;

    /**
     * 查询订阅消息发送结果
     *
     * @param id 订阅消息发送结果主键
     * @return 订阅消息发送结果
     */
    @Override
    public SubscribeMsgSendResult selectSubscribeMsgSendResultById(Long id) {
        return subscribeMsgSendResultMapper.selectSubscribeMsgSendResultById(id);
    }

    /**
     * 查询订阅消息发送结果列表
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 订阅消息发送结果
     */
    @Override
    public List<SubscribeMsgSendResult> selectSubscribeMsgSendResultList(SubscribeMsgSendResult subscribeMsgSendResult) {
        return subscribeMsgSendResultMapper.selectSubscribeMsgSendResultList(subscribeMsgSendResult);
    }

    /**
     * 新增订阅消息发送结果
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 结果
     */
    @Override
    public int insertSubscribeMsgSendResult(SubscribeMsgSendResult subscribeMsgSendResult) {
                subscribeMsgSendResult.setCreateTime(DateUtils.getNowDate());
            return subscribeMsgSendResultMapper.insertSubscribeMsgSendResult(subscribeMsgSendResult);
    }

    /**
     * 修改订阅消息发送结果
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 结果
     */
    @Override
    public int updateSubscribeMsgSendResult(SubscribeMsgSendResult subscribeMsgSendResult) {
                subscribeMsgSendResult.setUpdateTime(DateUtils.getNowDate());
        return subscribeMsgSendResultMapper.updateSubscribeMsgSendResult(subscribeMsgSendResult);
    }

    /**
     * 批量删除订阅消息发送结果
     *
     * @param ids 需要删除的订阅消息发送结果主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeMsgSendResultByIds(Long[] ids) {
        return subscribeMsgSendResultMapper.deleteSubscribeMsgSendResultByIds(ids);
    }

    /**
     * 删除订阅消息发送结果信息
     *
     * @param id 订阅消息发送结果主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeMsgSendResultById(Long id) {
        return subscribeMsgSendResultMapper.deleteSubscribeMsgSendResultById(id);
    }
}
