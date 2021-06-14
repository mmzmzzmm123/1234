package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhshMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhsh;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhshService;

/**
 * 群体教师考核审核过程Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjskhshServiceImpl implements ITsbzQtjskhshService {
    @Autowired
    private TsbzQtjskhshMapper tsbzQtjskhshMapper;

    /**
     * 查询群体教师考核审核过程
     *
     * @param id 群体教师考核审核过程ID
     * @return 群体教师考核审核过程
     */
    @Override
    public TsbzQtjskhsh selectTsbzQtjskhshById(Long id) {
        return tsbzQtjskhshMapper.selectTsbzQtjskhshById(id);
    }

    /**
     * 查询群体教师考核审核过程列表
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 群体教师考核审核过程
     */
    @Override
    @DataScope(deptAlias = "c")
    public List<TsbzQtjskhsh> selectTsbzQtjskhshList(TsbzQtjskhsh tsbzQtjskhsh) {
        return tsbzQtjskhshMapper.selectTsbzQtjskhshList(tsbzQtjskhsh);
    }

    /**
     * 新增群体教师考核审核过程
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhsh(TsbzQtjskhsh tsbzQtjskhsh) {
        tsbzQtjskhsh.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhshMapper.insertTsbzQtjskhsh(tsbzQtjskhsh);
    }

    /**
     * 修改群体教师考核审核过程
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhsh(TsbzQtjskhsh tsbzQtjskhsh) {
        return tsbzQtjskhshMapper.updateTsbzQtjskhsh(tsbzQtjskhsh);
    }

    /**
     * 批量删除群体教师考核审核过程
     *
     * @param ids 需要删除的群体教师考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhshByIds(Long[] ids) {
        return tsbzQtjskhshMapper.deleteTsbzQtjskhshByIds(ids);
    }

    /**
     * 删除群体教师考核审核过程信息
     *
     * @param id 群体教师考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhshById(Long id) {
        return tsbzQtjskhshMapper.deleteTsbzQtjskhshById(id);
    }
}
