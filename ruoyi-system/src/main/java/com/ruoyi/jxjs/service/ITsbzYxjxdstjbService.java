package com.ruoyi.jxjs.service;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzYxjxdstjb;

/**
 * 优秀见习导师推荐Service接口
 *
 * @author tsbz
 * @date 2021-07-03
 */
public interface ITsbzYxjxdstjbService {
    /**
     * 查询优秀见习导师推荐
     *
     * @param id 优秀见习导师推荐ID
     * @return 优秀见习导师推荐
     */
    public TsbzYxjxdstjb selectTsbzYxjxdstjbById(Long id);

    /**
     * 查询优秀见习导师推荐列表
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 优秀见习导师推荐集合
     */
    public List<TsbzYxjxdstjb> selectTsbzYxjxdstjbList(TsbzYxjxdstjb tsbzYxjxdstjb);


    /**
     * 查询优秀见习导师推荐列表
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 优秀见习导师推荐集合
     */
    public List<TsbzYxjxdstjb> selectTsbzYxjxdstjbListExport(TsbzYxjxdstjb tsbzYxjxdstjb);

    /**
     * 新增优秀见习导师推荐
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 结果
     */
    public int insertTsbzYxjxdstjb(TsbzYxjxdstjb tsbzYxjxdstjb);

    /**
     * 修改优秀见习导师推荐
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 结果
     */
    public int updateTsbzYxjxdstjb(TsbzYxjxdstjb tsbzYxjxdstjb);

    /**
     * 批量删除优秀见习导师推荐
     *
     * @param ids 需要删除的优秀见习导师推荐ID
     * @return 结果
     */
    public int deleteTsbzYxjxdstjbByIds(Long[] ids);

    /**
     * 删除优秀见习导师推荐信息
     *
     * @param id 优秀见习导师推荐ID
     * @return 结果
     */
    public int deleteTsbzYxjxdstjbById(Long id);
}
