package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGuazhijlMapper;
import com.ruoyi.gbxxgl.domain.TsbzGuazhijl;
import com.ruoyi.gbxxgl.service.ITsbzGuazhijlService;

/**
 * 干部挂职经历Service业务层处理
 *
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGuazhijlServiceImpl implements ITsbzGuazhijlService {
    @Autowired
    private TsbzGuazhijlMapper tsbzGuazhijlMapper;

    /**
     * 查询干部挂职经历
     *
     * @param id 干部挂职经历ID
     * @return 干部挂职经历
     */
    @Override
    public TsbzGuazhijl selectTsbzGuazhijlById(Long id) {
        return tsbzGuazhijlMapper.selectTsbzGuazhijlById(id);
    }

    /**
     * 查询干部挂职经历列表
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 干部挂职经历
     */
    @Override
    public List<TsbzGuazhijl> selectTsbzGuazhijlList(TsbzGuazhijl tsbzGuazhijl) {
        return tsbzGuazhijlMapper.selectTsbzGuazhijlList(tsbzGuazhijl);
    }

    /**
     * 新增干部挂职经历
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 结果
     */
    @Override
    public int insertTsbzGuazhijl(TsbzGuazhijl tsbzGuazhijl) {
        tsbzGuazhijl.setCreateTime(DateUtils.getNowDate());
        return tsbzGuazhijlMapper.insertTsbzGuazhijl(tsbzGuazhijl);
    }

    /**
     * 修改干部挂职经历
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 结果
     */
    @Override
    public int updateTsbzGuazhijl(TsbzGuazhijl tsbzGuazhijl) {
        return tsbzGuazhijlMapper.updateTsbzGuazhijl(tsbzGuazhijl);
    }

    /**
     * 批量删除干部挂职经历
     *
     * @param ids 需要删除的干部挂职经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGuazhijlByIds(Long[] ids) {
        return tsbzGuazhijlMapper.deleteTsbzGuazhijlByIds(ids);
    }

    /**
     * 删除干部挂职经历信息
     *
     * @param id 干部挂职经历ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGuazhijlById(Long id) {
        return tsbzGuazhijlMapper.deleteTsbzGuazhijlById(id);
    }
}
