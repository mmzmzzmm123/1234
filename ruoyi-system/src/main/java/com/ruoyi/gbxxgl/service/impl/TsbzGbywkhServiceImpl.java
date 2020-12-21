package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbywkhMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbywkh;
import com.ruoyi.gbxxgl.service.ITsbzGbywkhService;

/**
 * 业务考核(干部管理-评价与考核)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbywkhServiceImpl implements ITsbzGbywkhService {
    @Autowired
    private TsbzGbywkhMapper tsbzGbywkhMapper;

    /**
     * 查询业务考核(干部管理-评价与考核)
     *
     * @param id 业务考核(干部管理-评价与考核)ID
     * @return 业务考核(干部管理 - 评价与考核)
     */
    @Override
    public TsbzGbywkh selectTsbzGbywkhById(Long id) {
        return tsbzGbywkhMapper.selectTsbzGbywkhById(id);
    }

    /**
     * 查询业务考核(干部管理-评价与考核)列表
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 业务考核(干部管理 - 评价与考核)
     */
    @Override
    public List<TsbzGbywkh> selectTsbzGbywkhList(TsbzGbywkh tsbzGbywkh) {
        return tsbzGbywkhMapper.selectTsbzGbywkhList(tsbzGbywkh);
    }

    /**
     * 新增业务考核(干部管理-评价与考核)
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int insertTsbzGbywkh(TsbzGbywkh tsbzGbywkh) {
        tsbzGbywkh.setCreateTime(DateUtils.getNowDate());
        return tsbzGbywkhMapper.insertTsbzGbywkh(tsbzGbywkh);
    }

    /**
     * 修改业务考核(干部管理-评价与考核)
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int updateTsbzGbywkh(TsbzGbywkh tsbzGbywkh) {
        return tsbzGbywkhMapper.updateTsbzGbywkh(tsbzGbywkh);
    }

    /**
     * 批量删除业务考核(干部管理-评价与考核)
     *
     * @param ids 需要删除的业务考核(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbywkhByIds(Long[] ids) {
        return tsbzGbywkhMapper.deleteTsbzGbywkhByIds(ids);
    }

    /**
     * 删除业务考核(干部管理-评价与考核)信息
     *
     * @param id 业务考核(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbywkhById(Long id) {
        return tsbzGbywkhMapper.deleteTsbzGbywkhById(id);
    }
}
