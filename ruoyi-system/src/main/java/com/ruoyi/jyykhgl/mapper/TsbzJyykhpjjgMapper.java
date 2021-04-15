package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyykhpjjg;

/**
 * 考核评价结果Mapper接口
 *
 * @author tsbz
 * @date 2021-04-16
 */
public interface TsbzJyykhpjjgMapper {
    /**
     * 查询考核评价结果
     *
     * @param id 考核评价结果ID
     * @return 考核评价结果
     */
    public TsbzJyykhpjjg selectTsbzJyykhpjjgById(Long id);

    /**
     * 查询考核评价结果列表
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 考核评价结果集合
     */
    public List<TsbzJyykhpjjg> selectTsbzJyykhpjjgList(TsbzJyykhpjjg tsbzJyykhpjjg);

    /**
     * 新增考核评价结果
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 结果
     */
    public int insertTsbzJyykhpjjg(TsbzJyykhpjjg tsbzJyykhpjjg);

    /**
     * 修改考核评价结果
     *
     * @param tsbzJyykhpjjg 考核评价结果
     * @return 结果
     */
    public int updateTsbzJyykhpjjg(TsbzJyykhpjjg tsbzJyykhpjjg);

    /**
     * 删除考核评价结果
     *
     * @param id 考核评价结果ID
     * @return 结果
     */
    public int deleteTsbzJyykhpjjgById(Long id);

    /**
     * 批量删除考核评价结果
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyykhpjjgByIds(Long[] ids);
}
