package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbpxjl;

/**
 * 干部培训经历Service接口
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGbpxjlService {
    /**
     * 查询干部培训经历
     *
     * @param id 干部培训经历ID
     * @return 干部培训经历
     */
    public TsbzGbpxjl selectTsbzGbpxjlById(Long id);

    /**
     * 查询干部培训经历列表
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 干部培训经历集合
     */
    public List<TsbzGbpxjl> selectTsbzGbpxjlList(TsbzGbpxjl tsbzGbpxjl);

    /**
     * 新增干部培训经历
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 结果
     */
    public int insertTsbzGbpxjl(TsbzGbpxjl tsbzGbpxjl);

    /**
     * 修改干部培训经历
     *
     * @param tsbzGbpxjl 干部培训经历
     * @return 结果
     */
    public int updateTsbzGbpxjl(TsbzGbpxjl tsbzGbpxjl);

    /**
     * 批量删除干部培训经历
     *
     * @param ids 需要删除的干部培训经历ID
     * @return 结果
     */
    public int deleteTsbzGbpxjlByIds(Long[] ids);

    /**
     * 删除干部培训经历信息
     *
     * @param id 干部培训经历ID
     * @return 结果
     */
    public int deleteTsbzGbpxjlById(Long id);
}
