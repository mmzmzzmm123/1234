package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbryjlMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbryjl;
import com.ruoyi.gbxxgl.service.ITsbzGbryjlService;

/**
 * 荣誉奖励(干部管理)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbryjlServiceImpl implements ITsbzGbryjlService {
    @Autowired
    private TsbzGbryjlMapper tsbzGbryjlMapper;

    /**
     * 查询荣誉奖励(干部管理)
     *
     * @param id 荣誉奖励(干部管理)ID
     * @return 荣誉奖励(干部管理)
     */
    @Override
    public TsbzGbryjl selectTsbzGbryjlById(Long id) {
        return tsbzGbryjlMapper.selectTsbzGbryjlById(id);
    }

    /**
     * 查询荣誉奖励(干部管理)列表
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 荣誉奖励(干部管理)
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "b")
    public List<TsbzGbryjl> selectTsbzGbryjlList(TsbzGbryjl tsbzGbryjl) {
        return tsbzGbryjlMapper.selectTsbzGbryjlList(tsbzGbryjl);
    }

    /**
     * 新增荣誉奖励(干部管理)
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 结果
     */
    @Override
    public int insertTsbzGbryjl(TsbzGbryjl tsbzGbryjl) {
        tsbzGbryjl.setCreateTime(DateUtils.getNowDate());
        return tsbzGbryjlMapper.insertTsbzGbryjl(tsbzGbryjl);
    }

    /**
     * 修改荣誉奖励(干部管理)
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 结果
     */
    @Override
    public int updateTsbzGbryjl(TsbzGbryjl tsbzGbryjl) {
        return tsbzGbryjlMapper.updateTsbzGbryjl(tsbzGbryjl);
    }

    /**
     * 批量删除荣誉奖励(干部管理)
     *
     * @param ids 需要删除的荣誉奖励(干部管理)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbryjlByIds(Long[] ids) {
        return tsbzGbryjlMapper.deleteTsbzGbryjlByIds(ids);
    }

    /**
     * 删除荣誉奖励(干部管理)信息
     *
     * @param id 荣誉奖励(干部管理)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbryjlById(Long id) {
        return tsbzGbryjlMapper.deleteTsbzGbryjlById(id);
    }
}
