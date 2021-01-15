package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyydwjsMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyydwjs;
import com.ruoyi.jyykhgl.service.ITsbzJyydwjsService;

/**
 * 队伍建设（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-15
 */
@Service
public class TsbzJyydwjsServiceImpl implements ITsbzJyydwjsService {
    @Autowired
    private TsbzJyydwjsMapper tsbzJyydwjsMapper;

    /**
     * 查询队伍建设（教研员）
     *
     * @param id 队伍建设（教研员）ID
     * @return 队伍建设（教研员）
     */
    @Override
    public TsbzJyydwjs selectTsbzJyydwjsById(Long id) {
        return tsbzJyydwjsMapper.selectTsbzJyydwjsById(id);
    }

    /**
     * 查询队伍建设（教研员）列表
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 队伍建设（教研员）
     */
    @Override
    public List<TsbzJyydwjs> selectTsbzJyydwjsList(TsbzJyydwjs tsbzJyydwjs) {
        return tsbzJyydwjsMapper.selectTsbzJyydwjsList(tsbzJyydwjs);
    }

    /**
     * 新增队伍建设（教研员）
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyydwjs(TsbzJyydwjs tsbzJyydwjs) {
        tsbzJyydwjs.setCreateTime(DateUtils.getNowDate());
        return tsbzJyydwjsMapper.insertTsbzJyydwjs(tsbzJyydwjs);
    }

    /**
     * 修改队伍建设（教研员）
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyydwjs(TsbzJyydwjs tsbzJyydwjs) {
        return tsbzJyydwjsMapper.updateTsbzJyydwjs(tsbzJyydwjs);
    }

    /**
     * 批量删除队伍建设（教研员）
     *
     * @param ids 需要删除的队伍建设（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyydwjsByIds(Long[] ids) {
        return tsbzJyydwjsMapper.deleteTsbzJyydwjsByIds(ids);
    }

    /**
     * 删除队伍建设（教研员）信息
     *
     * @param id 队伍建设（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyydwjsById(Long id) {
        return tsbzJyydwjsMapper.deleteTsbzJyydwjsById(id);
    }
}
