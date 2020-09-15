package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzQtjspxfaMapper;
import com.ruoyi.qtjs.domain.TsbzQtjspxfa;
import com.ruoyi.qtjs.service.ITsbzQtjspxfaService;

/**
 * 群体教师评选方案Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-15
 */
@Service
public class TsbzQtjspxfaServiceImpl implements ITsbzQtjspxfaService {
    @Autowired
    private TsbzQtjspxfaMapper tsbzQtjspxfaMapper;

    /**
     * 查询群体教师评选方案
     *
     * @param id 群体教师评选方案ID
     * @return 群体教师评选方案
     */
    @Override
    public TsbzQtjspxfa selectTsbzQtjspxfaById(Long id) {
        return tsbzQtjspxfaMapper.selectTsbzQtjspxfaById(id);
    }

    /**
     * 查询群体教师评选方案列表
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 群体教师评选方案
     */
    @Override
    public List<TsbzQtjspxfa> selectTsbzQtjspxfaList(TsbzQtjspxfa tsbzQtjspxfa) {
        return tsbzQtjspxfaMapper.selectTsbzQtjspxfaList(tsbzQtjspxfa);
    }

    /**
     * 新增群体教师评选方案
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 结果
     */
    @Override
    public int insertTsbzQtjspxfa(TsbzQtjspxfa tsbzQtjspxfa) {
        tsbzQtjspxfa.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjspxfaMapper.insertTsbzQtjspxfa(tsbzQtjspxfa);
    }

    /**
     * 修改群体教师评选方案
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 结果
     */
    @Override
    public int updateTsbzQtjspxfa(TsbzQtjspxfa tsbzQtjspxfa) {
        return tsbzQtjspxfaMapper.updateTsbzQtjspxfa(tsbzQtjspxfa);
    }

    /**
     * 批量删除群体教师评选方案
     *
     * @param ids 需要删除的群体教师评选方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfaByIds(Long[] ids) {
        return tsbzQtjspxfaMapper.deleteTsbzQtjspxfaByIds(ids);
    }

    /**
     * 删除群体教师评选方案信息
     *
     * @param id 群体教师评选方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfaById(Long id) {
        return tsbzQtjspxfaMapper.deleteTsbzQtjspxfaById(id);
    }
}
