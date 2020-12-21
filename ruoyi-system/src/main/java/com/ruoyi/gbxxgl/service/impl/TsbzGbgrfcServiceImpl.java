package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbgrfcMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbgrfc;
import com.ruoyi.gbxxgl.service.ITsbzGbgrfcService;

/**
 * 个人风采(干部管理-个人状况)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbgrfcServiceImpl implements ITsbzGbgrfcService {
    @Autowired
    private TsbzGbgrfcMapper tsbzGbgrfcMapper;

    /**
     * 查询个人风采(干部管理-个人状况)
     *
     * @param id 个人风采(干部管理-个人状况)ID
     * @return 个人风采(干部管理 - 个人状况)
     */
    @Override
    public TsbzGbgrfc selectTsbzGbgrfcById(Long id) {
        return tsbzGbgrfcMapper.selectTsbzGbgrfcById(id);
    }

    /**
     * 查询个人风采(干部管理-个人状况)列表
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 个人风采(干部管理 - 个人状况)
     */
    @Override
    public List<TsbzGbgrfc> selectTsbzGbgrfcList(TsbzGbgrfc tsbzGbgrfc) {
        return tsbzGbgrfcMapper.selectTsbzGbgrfcList(tsbzGbgrfc);
    }

    /**
     * 新增个人风采(干部管理-个人状况)
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 结果
     */
    @Override
    public int insertTsbzGbgrfc(TsbzGbgrfc tsbzGbgrfc) {
        tsbzGbgrfc.setCreateTime(DateUtils.getNowDate());
        return tsbzGbgrfcMapper.insertTsbzGbgrfc(tsbzGbgrfc);
    }

    /**
     * 修改个人风采(干部管理-个人状况)
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 结果
     */
    @Override
    public int updateTsbzGbgrfc(TsbzGbgrfc tsbzGbgrfc) {
        return tsbzGbgrfcMapper.updateTsbzGbgrfc(tsbzGbgrfc);
    }

    /**
     * 批量删除个人风采(干部管理-个人状况)
     *
     * @param ids 需要删除的个人风采(干部管理-个人状况)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbgrfcByIds(Long[] ids) {
        return tsbzGbgrfcMapper.deleteTsbzGbgrfcByIds(ids);
    }

    /**
     * 删除个人风采(干部管理-个人状况)信息
     *
     * @param id 个人风采(干部管理-个人状况)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbgrfcById(Long id) {
        return tsbzGbgrfcMapper.deleteTsbzGbgrfcById(id);
    }
}
