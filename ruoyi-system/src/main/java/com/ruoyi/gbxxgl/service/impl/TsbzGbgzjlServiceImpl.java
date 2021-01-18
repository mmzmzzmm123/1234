package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbgzjlMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbgzjl;
import com.ruoyi.gbxxgl.service.ITsbzGbgzjlService;

/**
 * 干部工作经历Service业务层处理
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGbgzjlServiceImpl implements ITsbzGbgzjlService {
    @Autowired
    private TsbzGbgzjlMapper tsbzGbgzjlMapper;

    /**
     * 查询干部工作经历
     *
     * @param id 干部工作经历ID
     * @return 干部工作经历
     */
    @Override
    public TsbzGbgzjl selectTsbzGbgzjlById(Long id) {
        return tsbzGbgzjlMapper.selectTsbzGbgzjlById(id);
    }

    /**
     * 查询干部工作经历列表
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 干部工作经历
     */
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<TsbzGbgzjl> selectTsbzGbgzjlList(TsbzGbgzjl tsbzGbgzjl) {
        return tsbzGbgzjlMapper.selectTsbzGbgzjlList(tsbzGbgzjl);
    }

    /**
     * 新增干部工作经历
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 结果
     */
    @Override
    public int insertTsbzGbgzjl(TsbzGbgzjl tsbzGbgzjl) {
        tsbzGbgzjl.setCreateTime(DateUtils.getNowDate());
        return tsbzGbgzjlMapper.insertTsbzGbgzjl(tsbzGbgzjl);
    }

    /**
     * 修改干部工作经历
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 结果
     */
    @Override
    public int updateTsbzGbgzjl(TsbzGbgzjl tsbzGbgzjl) {
        return tsbzGbgzjlMapper.updateTsbzGbgzjl(tsbzGbgzjl);
    }

    /**
     * 批量删除干部工作经历
     *
     * @param ids 需要删除的干部工作经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbgzjlByIds(Long[] ids) {
        return tsbzGbgzjlMapper.deleteTsbzGbgzjlByIds(ids);
    }

    /**
     * 删除干部工作经历信息
     *
     * @param id 干部工作经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbgzjlById(Long id) {
        return tsbzGbgzjlMapper.deleteTsbzGbgzjlById(id);
    }
}
