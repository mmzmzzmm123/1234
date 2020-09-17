package com.ruoyi.qtjskhgl.service;

import java.util.List;

import com.ruoyi.qtjskhgl.domain.TsbzQtjskhsh;

/**
 * 群体教师考核审核过程Service接口
 *
 * @author ruoyi
 * @date 2020-09-17
 */
public interface ITsbzQtjskhshService {
    /**
     * 查询群体教师考核审核过程
     *
     * @param id 群体教师考核审核过程ID
     * @return 群体教师考核审核过程
     */
    public TsbzQtjskhsh selectTsbzQtjskhshById(Long id);

    /**
     * 查询群体教师考核审核过程列表
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 群体教师考核审核过程集合
     */
    public List<TsbzQtjskhsh> selectTsbzQtjskhshList(TsbzQtjskhsh tsbzQtjskhsh);

    /**
     * 新增群体教师考核审核过程
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 结果
     */
    public int insertTsbzQtjskhsh(TsbzQtjskhsh tsbzQtjskhsh);

    /**
     * 修改群体教师考核审核过程
     *
     * @param tsbzQtjskhsh 群体教师考核审核过程
     * @return 结果
     */
    public int updateTsbzQtjskhsh(TsbzQtjskhsh tsbzQtjskhsh);

    /**
     * 批量删除群体教师考核审核过程
     *
     * @param ids 需要删除的群体教师考核审核过程ID
     * @return 结果
     */
    public int deleteTsbzQtjskhshByIds(Long[] ids);

    /**
     * 删除群体教师考核审核过程信息
     *
     * @param id 群体教师考核审核过程ID
     * @return 结果
     */
    public int deleteTsbzQtjskhshById(Long id);
}
