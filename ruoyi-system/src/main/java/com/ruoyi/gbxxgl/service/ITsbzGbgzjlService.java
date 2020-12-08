package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbgzjl;

/**
 * 干部工作经历Service接口
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGbgzjlService {
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
     * 批量删除干部工作经历
     *
     * @param ids 需要删除的干部工作经历ID
     * @return 结果
     */
    public int deleteTsbzGbgzjlByIds(Long[] ids);

    /**
     * 删除干部工作经历信息
     *
     * @param id 干部工作经历ID
     * @return 结果
     */
    public int deleteTsbzGbgzjlById(Long id);
}
