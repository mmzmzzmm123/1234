package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbkyktMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbkykt;
import com.ruoyi.gbxxgl.service.ITsbzGbkyktService;

/**
 * 科研课题(干部管理)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbkyktServiceImpl implements ITsbzGbkyktService {
    @Autowired
    private TsbzGbkyktMapper tsbzGbkyktMapper;

    /**
     * 查询科研课题(干部管理)
     *
     * @param id 科研课题(干部管理)ID
     * @return 科研课题(干部管理)
     */
    @Override
    public TsbzGbkykt selectTsbzGbkyktById(Long id) {
        return tsbzGbkyktMapper.selectTsbzGbkyktById(id);
    }

    /**
     * 查询科研课题(干部管理)列表
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 科研课题(干部管理)
     */
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<TsbzGbkykt> selectTsbzGbkyktList(TsbzGbkykt tsbzGbkykt) {
        return tsbzGbkyktMapper.selectTsbzGbkyktList(tsbzGbkykt);
    }

    /**
     * 新增科研课题(干部管理)
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 结果
     */
    @Override
    public int insertTsbzGbkykt(TsbzGbkykt tsbzGbkykt) {
        tsbzGbkykt.setCreateTime(DateUtils.getNowDate());
        return tsbzGbkyktMapper.insertTsbzGbkykt(tsbzGbkykt);
    }

    /**
     * 修改科研课题(干部管理)
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 结果
     */
    @Override
    public int updateTsbzGbkykt(TsbzGbkykt tsbzGbkykt) {
        return tsbzGbkyktMapper.updateTsbzGbkykt(tsbzGbkykt);
    }

    /**
     * 批量删除科研课题(干部管理)
     *
     * @param ids 需要删除的科研课题(干部管理)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbkyktByIds(Long[] ids) {
        return tsbzGbkyktMapper.deleteTsbzGbkyktByIds(ids);
    }

    /**
     * 删除科研课题(干部管理)信息
     *
     * @param id 科研课题(干部管理)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbkyktById(Long id) {
        return tsbzGbkyktMapper.deleteTsbzGbkyktById(id);
    }
}
