package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbxxjl;

/**
 * 干部学习经历Service接口
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGbxxjlService {
    /**
     * 查询干部学习经历
     *
     * @param id 干部学习经历ID
     * @return 干部学习经历
     */
    public TsbzGbxxjl selectTsbzGbxxjlById(Long id);

    /**
     * 查询干部学习经历列表
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 干部学习经历集合
     */
    public List<TsbzGbxxjl> selectTsbzGbxxjlList(TsbzGbxxjl tsbzGbxxjl);

    /**
     * 新增干部学习经历
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 结果
     */
    public int insertTsbzGbxxjl(TsbzGbxxjl tsbzGbxxjl);

    /**
     * 修改干部学习经历
     *
     * @param tsbzGbxxjl 干部学习经历
     * @return 结果
     */
    public int updateTsbzGbxxjl(TsbzGbxxjl tsbzGbxxjl);

    /**
     * 批量删除干部学习经历
     *
     * @param ids 需要删除的干部学习经历ID
     * @return 结果
     */
    public int deleteTsbzGbxxjlByIds(Long[] ids);

    /**
     * 删除干部学习经历信息
     *
     * @param id 干部学习经历ID
     * @return 结果
     */
    public int deleteTsbzGbxxjlById(Long id);
}
