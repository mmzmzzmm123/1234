package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyyyjhdMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyyyjhd;
import com.ruoyi.jyykhgl.service.ITsbzJyyyjhdService;

/**
 * 研究活动（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-15
 */
@Service
public class TsbzJyyyjhdServiceImpl implements ITsbzJyyyjhdService {
    @Autowired
    private TsbzJyyyjhdMapper tsbzJyyyjhdMapper;

    /**
     * 查询研究活动（教研员）
     *
     * @param id 研究活动（教研员）ID
     * @return 研究活动（教研员）
     */
    @Override
    public TsbzJyyyjhd selectTsbzJyyyjhdById(Long id) {
        return tsbzJyyyjhdMapper.selectTsbzJyyyjhdById(id);
    }

    /**
     * 查询研究活动（教研员）列表
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 研究活动（教研员）
     */
    @Override
    public List<TsbzJyyyjhd> selectTsbzJyyyjhdList(TsbzJyyyjhd tsbzJyyyjhd) {
        return tsbzJyyyjhdMapper.selectTsbzJyyyjhdList(tsbzJyyyjhd);
    }

    /**
     * 新增研究活动（教研员）
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyyyjhd(TsbzJyyyjhd tsbzJyyyjhd) {
        tsbzJyyyjhd.setCreateTime(DateUtils.getNowDate());
        return tsbzJyyyjhdMapper.insertTsbzJyyyjhd(tsbzJyyyjhd);
    }

    /**
     * 修改研究活动（教研员）
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyyyjhd(TsbzJyyyjhd tsbzJyyyjhd) {
        return tsbzJyyyjhdMapper.updateTsbzJyyyjhd(tsbzJyyyjhd);
    }

    /**
     * 批量删除研究活动（教研员）
     *
     * @param ids 需要删除的研究活动（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyyjhdByIds(Long[] ids) {
        return tsbzJyyyjhdMapper.deleteTsbzJyyyjhdByIds(ids);
    }

    /**
     * 删除研究活动（教研员）信息
     *
     * @param id 研究活动（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyyjhdById(Long id) {
        return tsbzJyyyjhdMapper.deleteTsbzJyyyjhdById(id);
    }
}
