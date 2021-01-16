package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyygryxMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyygryx;
import com.ruoyi.jyykhgl.service.ITsbzJyygryxService;

/**
 * 个人研修（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-16
 */
@Service
public class TsbzJyygryxServiceImpl implements ITsbzJyygryxService {
    @Autowired
    private TsbzJyygryxMapper tsbzJyygryxMapper;

    /**
     * 查询个人研修（教研员）
     *
     * @param id 个人研修（教研员）ID
     * @return 个人研修（教研员）
     */
    @Override
    public TsbzJyygryx selectTsbzJyygryxById(Long id) {
        return tsbzJyygryxMapper.selectTsbzJyygryxById(id);
    }

    /**
     * 查询个人研修（教研员）列表
     *
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 个人研修（教研员）
     */
    @Override
    public List<TsbzJyygryx> selectTsbzJyygryxList(TsbzJyygryx tsbzJyygryx) {
        return tsbzJyygryxMapper.selectTsbzJyygryxList(tsbzJyygryx);
    }

    /**
     * 新增个人研修（教研员）
     *
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyygryx(TsbzJyygryx tsbzJyygryx) {
        tsbzJyygryx.setCreateTime(DateUtils.getNowDate());
        return tsbzJyygryxMapper.insertTsbzJyygryx(tsbzJyygryx);
    }

    /**
     * 修改个人研修（教研员）
     *
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyygryx(TsbzJyygryx tsbzJyygryx) {
        return tsbzJyygryxMapper.updateTsbzJyygryx(tsbzJyygryx);
    }

    /**
     * 批量删除个人研修（教研员）
     *
     * @param ids 需要删除的个人研修（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyygryxByIds(Long[] ids) {
        return tsbzJyygryxMapper.deleteTsbzJyygryxByIds(ids);
    }

    /**
     * 删除个人研修（教研员）信息
     *
     * @param id 个人研修（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyygryxById(Long id) {
        return tsbzJyygryxMapper.deleteTsbzJyygryxById(id);
    }
}
