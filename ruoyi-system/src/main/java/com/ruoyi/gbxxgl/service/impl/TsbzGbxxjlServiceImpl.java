package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbxxjlMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbxxjl;
import com.ruoyi.gbxxgl.service.ITsbzGbxxjlService;

/**
 * 干部学习经历Service业务层处理
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGbxxjlServiceImpl implements ITsbzGbxxjlService {
    @Autowired
    private TsbzGbxxjlMapper tsbzGbxxjlMapper;

    /**
     * 查询干部学习经历
     *
     * @param id 干部学习经历ID
     * @return 干部学习经历
     */
    @Override
    public TsbzGbxxjl selectTsbzGbxxjlById(Long id) {
        return tsbzGbxxjlMapper.selectTsbzGbxxjlById(id);
    }

    /**
     * 查询干部学习经历列表
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 干部学习经历
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "b")
    public List<TsbzGbxxjl> selectTsbzGbxxjlList(TsbzGbxxjl tsbzGbxxjl) {
        return tsbzGbxxjlMapper.selectTsbzGbxxjlList(tsbzGbxxjl);
    }

    /**
     * 新增干部学习经历
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 结果
     */
    @Override
    public int insertTsbzGbxxjl(TsbzGbxxjl tsbzGbxxjl) {
        tsbzGbxxjl.setCreateTime(DateUtils.getNowDate());
        return tsbzGbxxjlMapper.insertTsbzGbxxjl(tsbzGbxxjl);
    }

    /**
     * 修改干部学习经历
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 结果
     */
    @Override
    public int updateTsbzGbxxjl(TsbzGbxxjl tsbzGbxxjl) {
        return tsbzGbxxjlMapper.updateTsbzGbxxjl(tsbzGbxxjl);
    }

    /**
     * 批量删除干部学习经历
     *
     * @param ids 需要删除的干部学习经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbxxjlByIds(Long[] ids) {
        return tsbzGbxxjlMapper.deleteTsbzGbxxjlByIds(ids);
    }

    /**
     * 删除干部学习经历信息
     *
     * @param id 干部学习经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbxxjlById(Long id) {
        return tsbzGbxxjlMapper.deleteTsbzGbxxjlById(id);
    }
}
