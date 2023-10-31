package com.ruoyi.onethinker.service;

import java.util.List;
import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;

/**
 * 红包批控制Service接口
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
public interface IRedEnvelopeCtrlService {
    /**
     * 查询红包批控制
     *
     * @param id 红包批控制主键
     * @return 红包批控制
     */
    public RedEnvelopeCtrl selectRedEnvelopeCtrlById(Long id);

    /**
     * 查询红包批控制列表
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 红包批控制集合
     */
    public List<RedEnvelopeCtrl> selectRedEnvelopeCtrlList(RedEnvelopeCtrl redEnvelopeCtrl);

    /**
     * 新增红包批控制
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 结果
     */
    public int insertRedEnvelopeCtrl(RedEnvelopeCtrl redEnvelopeCtrl);

    /**
     * 修改红包批控制
     *
     * @param redEnvelopeCtrl 红包批控制
     * @return 结果
     */
    public int updateRedEnvelopeCtrl(RedEnvelopeCtrl redEnvelopeCtrl);

    /**
     * 批量删除红包批控制
     *
     * @param ids 需要删除的红包批控制主键集合
     * @return 结果
     */
    public int deleteRedEnvelopeCtrlByIds(Long[] ids);

    /**
     * 删除红包批控制信息
     *
     * @param id 红包批控制主键
     * @return 结果
     */
    public int deleteRedEnvelopeCtrlById(Long id);
}
