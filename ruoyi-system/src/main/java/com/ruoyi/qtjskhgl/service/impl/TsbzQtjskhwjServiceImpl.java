package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhwjMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhwj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhwjService;

/**
 * 群体教师考核文件管理Service业务层处理
 *
 * @author tsbz
 * @date 2021-06-03
 */
@Service
public class TsbzQtjskhwjServiceImpl implements ITsbzQtjskhwjService {
    @Autowired
    private TsbzQtjskhwjMapper tsbzQtjskhwjMapper;

    /**
     * 查询群体教师考核文件管理
     *
     * @param id 群体教师考核文件管理ID
     * @return 群体教师考核文件管理
     */
    @Override
    public TsbzQtjskhwj selectTsbzQtjskhwjById(Long id) {
        return tsbzQtjskhwjMapper.selectTsbzQtjskhwjById(id);
    }

    /**
     * 查询群体教师考核文件管理列表
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 群体教师考核文件管理
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<TsbzQtjskhwj> selectTsbzQtjskhwjList(TsbzQtjskhwj tsbzQtjskhwj) {
        return tsbzQtjskhwjMapper.selectTsbzQtjskhwjList(tsbzQtjskhwj);
    }

    /**
     * 新增群体教师考核文件管理
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhwj(TsbzQtjskhwj tsbzQtjskhwj) {
        tsbzQtjskhwj.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhwjMapper.insertTsbzQtjskhwj(tsbzQtjskhwj);
    }

    /**
     * 修改群体教师考核文件管理
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhwj(TsbzQtjskhwj tsbzQtjskhwj) {
        return tsbzQtjskhwjMapper.updateTsbzQtjskhwj(tsbzQtjskhwj);
    }

    /**
     * 批量删除群体教师考核文件管理
     *
     * @param ids 需要删除的群体教师考核文件管理ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhwjByIds(Long[] ids) {
        return tsbzQtjskhwjMapper.deleteTsbzQtjskhwjByIds(ids);
    }

    /**
     * 删除群体教师考核文件管理信息
     *
     * @param id 群体教师考核文件管理ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhwjById(Long id) {
        return tsbzQtjskhwjMapper.deleteTsbzQtjskhwjById(id);
    }
}
