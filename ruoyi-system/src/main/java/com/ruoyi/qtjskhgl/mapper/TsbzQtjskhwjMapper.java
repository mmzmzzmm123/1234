package com.ruoyi.qtjskhgl.mapper;

import java.util.List;

import com.ruoyi.qtjskhgl.domain.TsbzQtjskhwj;

/**
 * 群体教师考核文件管理Mapper接口
 *
 * @author tsbz
 * @date 2021-06-03
 */
public interface TsbzQtjskhwjMapper {
    /**
     * 查询群体教师考核文件管理
     *
     * @param id 群体教师考核文件管理ID
     * @return 群体教师考核文件管理
     */
    public TsbzQtjskhwj selectTsbzQtjskhwjById(Long id);

    /**
     * 查询群体教师考核文件管理列表
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 群体教师考核文件管理集合
     */
    public List<TsbzQtjskhwj> selectTsbzQtjskhwjList(TsbzQtjskhwj tsbzQtjskhwj);

    /**
     * 新增群体教师考核文件管理
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 结果
     */
    public int insertTsbzQtjskhwj(TsbzQtjskhwj tsbzQtjskhwj);

    /**
     * 修改群体教师考核文件管理
     *
     * @param tsbzQtjskhwj 群体教师考核文件管理
     * @return 结果
     */
    public int updateTsbzQtjskhwj(TsbzQtjskhwj tsbzQtjskhwj);

    /**
     * 删除群体教师考核文件管理
     *
     * @param id 群体教师考核文件管理ID
     * @return 结果
     */
    public int deleteTsbzQtjskhwjById(Long id);

    /**
     * 批量删除群体教师考核文件管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhwjByIds(Long[] ids);
}
