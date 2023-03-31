package com.geek.system.service.impl;

import java.util.List;
import com.geek.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geek.system.mapper.SysAppArticleMapper;
import com.geek.system.domain.SysAppArticle;
import com.geek.system.service.ISysAppArticleService;

/**
 * app内容管理Service业务层处理
 * 
 * @author xuek
 * @date 2023-03-29
 */
@Service
public class SysAppArticleServiceImpl implements ISysAppArticleService {
    @Autowired
    private SysAppArticleMapper sysAppArticleMapper;

    /**
     * 查询app内容管理
     * 
     * @param id app内容管理主键
     * @return app内容管理
     */
    @Override
    public SysAppArticle selectSysAppArticleById(Long id){
        return sysAppArticleMapper.selectSysAppArticleById(id);
    }

    /**
     * 查询app内容管理列表
     * 
     * @param sysAppArticle app内容管理
     * @return app内容管理
     */
    @Override
    public List<SysAppArticle> selectSysAppArticleList(SysAppArticle sysAppArticle){
        return sysAppArticleMapper.selectSysAppArticleList(sysAppArticle);
    }

    /**
     * 新增app内容管理
     * 
     * @param sysAppArticle app内容管理
     * @return 结果
     */
    @Override
    public int insertSysAppArticle(SysAppArticle sysAppArticle){
        sysAppArticle.setCreateTime(DateUtils.getNowDate());
        return sysAppArticleMapper.insertSysAppArticle(sysAppArticle);
    }

    /**
     * 修改app内容管理
     * 
     * @param sysAppArticle app内容管理
     * @return 结果
     */
    @Override
    public int updateSysAppArticle(SysAppArticle sysAppArticle){
        sysAppArticle.setUpdateTime(DateUtils.getNowDate());
        return sysAppArticleMapper.updateSysAppArticle(sysAppArticle);
    }

    /**
     * 批量删除app内容管理
     * 
     * @param ids 需要删除的app内容管理主键
     * @return 结果
     */
    @Override
    public int deleteSysAppArticleByIds(Long[] ids){
        return sysAppArticleMapper.deleteSysAppArticleByIds(ids);
    }

    /**
     * 删除app内容管理信息
     * 
     * @param id app内容管理主键
     * @return 结果
     */
    @Override
    public int deleteSysAppArticleById(Long id){
        return sysAppArticleMapper.deleteSysAppArticleById(id);
    }
}
