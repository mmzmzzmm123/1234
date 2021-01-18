package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbpxjlMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbpxjl;
import com.ruoyi.gbxxgl.service.ITsbzGbpxjlService;

/**
 * 干部培训经历Service业务层处理
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGbpxjlServiceImpl implements ITsbzGbpxjlService {
    @Autowired
    private TsbzGbpxjlMapper tsbzGbpxjlMapper;

    /**
     * 查询干部培训经历
     *
     * @param id 干部培训经历ID
     * @return 干部培训经历
     */
    @Override
    public TsbzGbpxjl selectTsbzGbpxjlById(Long id) {
        return tsbzGbpxjlMapper.selectTsbzGbpxjlById(id);
    }

    /**
     * 查询干部培训经历列表
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 干部培训经历
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "b")
    public List<TsbzGbpxjl> selectTsbzGbpxjlList(TsbzGbpxjl tsbzGbpxjl) {
        return tsbzGbpxjlMapper.selectTsbzGbpxjlList(tsbzGbpxjl);
    }

    /**
     * 新增干部培训经历
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 结果
     */
    @Override
    public int insertTsbzGbpxjl(TsbzGbpxjl tsbzGbpxjl) {
        tsbzGbpxjl.setCreateTime(DateUtils.getNowDate());
        return tsbzGbpxjlMapper.insertTsbzGbpxjl(tsbzGbpxjl);
    }

    /**
     * 修改干部培训经历
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 结果
     */
    @Override
    public int updateTsbzGbpxjl(TsbzGbpxjl tsbzGbpxjl) {
        return tsbzGbpxjlMapper.updateTsbzGbpxjl(tsbzGbpxjl);
    }

    /**
     * 批量删除干部培训经历
     *
     * @param ids 需要删除的干部培训经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbpxjlByIds(Long[] ids) {
        return tsbzGbpxjlMapper.deleteTsbzGbpxjlByIds(ids);
    }

    /**
     * 删除干部培训经历信息
     *
     * @param id 干部培训经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbpxjlById(Long id) {
        return tsbzGbpxjlMapper.deleteTsbzGbpxjlById(id);
    }
}
