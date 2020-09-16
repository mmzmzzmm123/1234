package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzQtjspxjgMapper;
import com.ruoyi.qtjs.domain.TsbzQtjspxjg;
import com.ruoyi.qtjs.service.ITsbzQtjspxjgService;

/**
 * 群体教师评选结果Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@Service
public class TsbzQtjspxjgServiceImpl implements ITsbzQtjspxjgService {
    @Autowired
    private TsbzQtjspxjgMapper tsbzQtjspxjgMapper;

    /**
     * 查询群体教师评选结果
     *
     * @param id 群体教师评选结果ID
     * @return 群体教师评选结果
     */
    @Override
    public TsbzQtjspxjg selectTsbzQtjspxjgById(Long id) {
        return tsbzQtjspxjgMapper.selectTsbzQtjspxjgById(id);
    }

    /**
     * 查询群体教师评选结果列表
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 群体教师评选结果
     */
    @Override
    public List<TsbzQtjspxjg> selectTsbzQtjspxjgList(TsbzQtjspxjg tsbzQtjspxjg) {
        return tsbzQtjspxjgMapper.selectTsbzQtjspxjgList(tsbzQtjspxjg);
    }

    /**
     * 新增群体教师评选结果
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 结果
     */
    @Override
    public int insertTsbzQtjspxjg(TsbzQtjspxjg tsbzQtjspxjg) {
        tsbzQtjspxjg.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjspxjgMapper.insertTsbzQtjspxjg(tsbzQtjspxjg);
    }

    /**
     * 修改群体教师评选结果
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 结果
     */
    @Override
    public int updateTsbzQtjspxjg(TsbzQtjspxjg tsbzQtjspxjg) {
        return tsbzQtjspxjgMapper.updateTsbzQtjspxjg(tsbzQtjspxjg);
    }

    /**
     * 批量删除群体教师评选结果
     *
     * @param ids 需要删除的群体教师评选结果ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxjgByIds(Long[] ids) {
        return tsbzQtjspxjgMapper.deleteTsbzQtjspxjgByIds(ids);
    }

    /**
     * 删除群体教师评选结果信息
     *
     * @param id 群体教师评选结果ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxjgById(Long id) {
        return tsbzQtjspxjgMapper.deleteTsbzQtjspxjgById(id);
    }
}
