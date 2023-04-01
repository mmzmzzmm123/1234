package com.geek.system.service;

import java.util.List;
import com.geek.system.domain.SysAppArticle;

/**
 * app内容管理Service接口
 * 
 * @author xuek
 * @date 2023-03-29
 */
public interface ISysAppArticleService {
    /**
     * 查询app内容管理
     * 
     * @param id app内容管理主键
     * @return app内容管理
     */
    public SysAppArticle selectSysAppArticleById(Long id);

    /**
     * 查询app内容管理列表
     * 
     * @param sysAppArticle app内容管理
     * @return app内容管理集合
     */
    public List<SysAppArticle> selectSysAppArticleList(SysAppArticle sysAppArticle);

    /**
     * 新增app内容管理
     * 
     * @param sysAppArticle app内容管理
     * @return 结果
     */
    public int insertSysAppArticle(SysAppArticle sysAppArticle);

    /**
     * 修改app内容管理
     * 
     * @param sysAppArticle app内容管理
     * @return 结果
     */
    public int updateSysAppArticle(SysAppArticle sysAppArticle);

    /**
     * 批量删除app内容管理
     * 
     * @param ids 需要删除的app内容管理主键集合
     * @return 结果
     */
    public int deleteSysAppArticleByIds(Long[] ids);

    /**
     * 删除app内容管理信息
     * 
     * @param id app内容管理主键
     * @return 结果
     */
    public int deleteSysAppArticleById(Long id);
}
