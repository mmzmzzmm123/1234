package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbndkhMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbndkh;
import com.ruoyi.gbxxgl.service.ITsbzGbndkhService;

/**
 * 年度考核(干部管理-评价与考核)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbndkhServiceImpl implements ITsbzGbndkhService {
    @Autowired
    private TsbzGbndkhMapper tsbzGbndkhMapper;

    /**
     * 查询年度考核(干部管理-评价与考核)
     *
     * @param id 年度考核(干部管理-评价与考核)ID
     * @return 年度考核(干部管理 - 评价与考核)
     */
    @Override
    public TsbzGbndkh selectTsbzGbndkhById(Long id) {
        return tsbzGbndkhMapper.selectTsbzGbndkhById(id);
    }

    /**
     * 查询年度考核(干部管理-评价与考核)列表
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 年度考核(干部管理 - 评价与考核)
     */
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<TsbzGbndkh> selectTsbzGbndkhList(TsbzGbndkh tsbzGbndkh) {
        return tsbzGbndkhMapper.selectTsbzGbndkhList(tsbzGbndkh);
    }

    /**
     * 新增年度考核(干部管理-评价与考核)
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int insertTsbzGbndkh(TsbzGbndkh tsbzGbndkh) {
        tsbzGbndkh.setCreateTime(DateUtils.getNowDate());
        return tsbzGbndkhMapper.insertTsbzGbndkh(tsbzGbndkh);
    }

    /**
     * 修改年度考核(干部管理-评价与考核)
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int updateTsbzGbndkh(TsbzGbndkh tsbzGbndkh) {
        return tsbzGbndkhMapper.updateTsbzGbndkh(tsbzGbndkh);
    }

    /**
     * 批量删除年度考核(干部管理-评价与考核)
     *
     * @param ids 需要删除的年度考核(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbndkhByIds(Long[] ids) {
        return tsbzGbndkhMapper.deleteTsbzGbndkhByIds(ids);
    }

    /**
     * 删除年度考核(干部管理-评价与考核)信息
     *
     * @param id 年度考核(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbndkhById(Long id) {
        return tsbzGbndkhMapper.deleteTsbzGbndkhById(id);
    }
}
