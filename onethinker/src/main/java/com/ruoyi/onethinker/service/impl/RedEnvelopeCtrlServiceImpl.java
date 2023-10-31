package com.ruoyi.onethinker.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.onethinker.mapper.RedEnvelopeCtrlMapper;
import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.service.IRedEnvelopeCtrlService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

/**
 * 红包批控制Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class RedEnvelopeCtrlServiceImpl implements IRedEnvelopeCtrlService {
    @Resource
    private RedEnvelopeCtrlMapper redEnvelopeCtrlMapper;

    /**
     * 查询红包批控制
     *
     * @param id 红包批控制主键
     * @return 红包批控制
     */
    @Override
    public RedEnvelopeCtrl selectRedEnvelopeCtrlById(Long id) {
        return redEnvelopeCtrlMapper.selectRedEnvelopeCtrlById(id);
    }

    /**
     * 查询红包批控制列表
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 红包批控制
     */
    @Override
    public List<RedEnvelopeCtrl> selectRedEnvelopeCtrlList(RedEnvelopeCtrl redEnvelopeCtrl) {
        return redEnvelopeCtrlMapper.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
    }

    /**
     * 新增红包批控制
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 结果
     */
    @Override
    public int insertRedEnvelopeCtrl(RedEnvelopeCtrl redEnvelopeCtrl) {
                redEnvelopeCtrl.setCreateTime(DateUtils.getNowDate());
            return redEnvelopeCtrlMapper.insertRedEnvelopeCtrl(redEnvelopeCtrl);
    }

    /**
     * 修改红包批控制
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 结果
     */
    @Override
    public int updateRedEnvelopeCtrl(RedEnvelopeCtrl redEnvelopeCtrl) {
                redEnvelopeCtrl.setUpdateTime(DateUtils.getNowDate());
        return redEnvelopeCtrlMapper.updateRedEnvelopeCtrl(redEnvelopeCtrl);
    }

    /**
     * 批量删除红包批控制
     *
     * @param ids 需要删除的红包批控制主键
     * @return 结果
     */
    @Override
    public int deleteRedEnvelopeCtrlByIds(Long[] ids) {
        return redEnvelopeCtrlMapper.deleteRedEnvelopeCtrlByIds(ids);
    }

    /**
     * 删除红包批控制信息
     *
     * @param id 红包批控制主键
     * @return 结果
     */
    @Override
    public int deleteRedEnvelopeCtrlById(Long id) {
        return redEnvelopeCtrlMapper.deleteRedEnvelopeCtrlById(id);
    }
}
