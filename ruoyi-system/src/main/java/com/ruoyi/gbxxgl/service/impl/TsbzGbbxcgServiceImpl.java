package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbbxcgMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbbxcg;
import com.ruoyi.gbxxgl.service.ITsbzGbbxcgService;

/**
 * 办学成果(干部管理-评价与考核)Service业务层处理
 *
 * @author gbxxgl
 * @date 2020-12-21
 */
@Service
public class TsbzGbbxcgServiceImpl implements ITsbzGbbxcgService {
    @Autowired
    private TsbzGbbxcgMapper tsbzGbbxcgMapper;

    /**
     * 查询办学成果(干部管理-评价与考核)
     *
     * @param id 办学成果(干部管理-评价与考核)ID
     * @return 办学成果(干部管理 - 评价与考核)
     */
    @Override
    public TsbzGbbxcg selectTsbzGbbxcgById(Long id) {
        return tsbzGbbxcgMapper.selectTsbzGbbxcgById(id);
    }

    /**
     * 查询办学成果(干部管理-评价与考核)列表
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 办学成果(干部管理 - 评价与考核)
     */
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<TsbzGbbxcg> selectTsbzGbbxcgList(TsbzGbbxcg tsbzGbbxcg) {
        return tsbzGbbxcgMapper.selectTsbzGbbxcgList(tsbzGbbxcg);
    }

    /**
     * 新增办学成果(干部管理-评价与考核)
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int insertTsbzGbbxcg(TsbzGbbxcg tsbzGbbxcg) {
        tsbzGbbxcg.setCreateTime(DateUtils.getNowDate());
        return tsbzGbbxcgMapper.insertTsbzGbbxcg(tsbzGbbxcg);
    }

    /**
     * 修改办学成果(干部管理-评价与考核)
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 结果
     */
    @Override
    public int updateTsbzGbbxcg(TsbzGbbxcg tsbzGbbxcg) {
        return tsbzGbbxcgMapper.updateTsbzGbbxcg(tsbzGbbxcg);
    }

    /**
     * 批量删除办学成果(干部管理-评价与考核)
     *
     * @param ids 需要删除的办学成果(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbbxcgByIds(Long[] ids) {
        return tsbzGbbxcgMapper.deleteTsbzGbbxcgByIds(ids);
    }

    /**
     * 删除办学成果(干部管理-评价与考核)信息
     *
     * @param id 办学成果(干部管理-评价与考核)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbbxcgById(Long id) {
        return tsbzGbbxcgMapper.deleteTsbzGbbxcgById(id);
    }
}
