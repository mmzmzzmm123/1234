package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGuazhijl;

/**
 * 干部挂职经历Service接口
 *
 * @author ruoyi
 * @date 2020-12-08
 */
public interface ITsbzGuazhijlService {
    /**
     * 查询干部挂职经历
     *
     * @param id 干部挂职经历ID
     * @return 干部挂职经历
     */
    public TsbzGuazhijl selectTsbzGuazhijlById(Long id);

    /**
     * 查询干部挂职经历列表
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 干部挂职经历集合
     */
    public List<TsbzGuazhijl> selectTsbzGuazhijlList(TsbzGuazhijl tsbzGuazhijl);

    /**
     * 新增干部挂职经历
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 结果
     */
    public int insertTsbzGuazhijl(TsbzGuazhijl tsbzGuazhijl);

    /**
     * 修改干部挂职经历
     *
     * @param tsbzGuazhijl 干部挂职经历
     * @return 结果
     */
    public int updateTsbzGuazhijl(TsbzGuazhijl tsbzGuazhijl);

    /**
     * 批量删除干部挂职经历
     *
     * @param ids 需要删除的干部挂职经历ID
     * @return 结果
     */
    public int deleteTsbzGuazhijlByIds(Long[] ids);

    /**
     * 删除干部挂职经历信息
     *
     * @param id 干部挂职经历ID
     * @return 结果
     */
    public int deleteTsbzGuazhijlById(Long id);
}
