package com.ruoyi.gbxxgl.mapper;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbgzjl;

/**
 * 干部工作经历Mapper接口
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public interface TsbzGbgzjlMapper {
    /**
     * 查询干部工作经历
     *
     * @param id 干部工作经历ID
     * @return 干部工作经历
     */
    public TsbzGbgzjl selectTsbzGbgzjlById(Long id);

    /**
     * 查询干部工作经历列表
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 干部工作经历集合
     */
    public List<TsbzGbgzjl> selectTsbzGbgzjlList(TsbzGbgzjl tsbzGbgzjl);

    /**
     * 新增干部工作经历
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 结果
     */
    public int insertTsbzGbgzjl(TsbzGbgzjl tsbzGbgzjl);

    /**
     * 修改干部工作经历
     *
     * @param tsbzGbgzjl 干部工作经历
     * @return 结果
     */
    public int updateTsbzGbgzjl(TsbzGbgzjl tsbzGbgzjl);

    /**
     * 删除干部工作经历
     *
     * @param id 干部工作经历ID
     * @return 结果
     */
    public int deleteTsbzGbgzjlById(Long id);

    /**
     * 批量删除干部工作经历
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzGbgzjlByIds(Long[] ids);
}
