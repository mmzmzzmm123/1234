package com.ruoyi.onethinker.mapper;

import java.util.List;
import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.dto.RedEnvelopeCtrlDTO;

/**
 * 红包批控制Mapper接口
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
public interface RedEnvelopeCtrlMapper {
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
     * 删除红包批控制
     *
     * @param id 红包批控制主键
     * @return 结果
     */
    public int deleteRedEnvelopeCtrlById(Long id);

    /**
     * 批量删除红包批控制
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRedEnvelopeCtrlByIds(Long[] ids);

    /**
     * 更新用户状态
     * @param redEnvelopeCtrl
     * @return
     */
    int updateRedEnvelopeCtrlAndStatus(RedEnvelopeCtrlDTO redEnvelopeCtrl);
}
