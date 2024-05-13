package com.jjpt.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.RepoMapper;
import com.jjpt.business.domain.Repo;
import com.jjpt.business.service.IRepoService;

import javax.annotation.Resource;

/**
 * 题库信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@Service
public class RepoServiceImpl implements IRepoService 
{
    @Resource
    private RepoMapper repoMapper;

    /**
     * 查询题库信息
     * 
     * @param id 题库信息主键
     * @return 题库信息
     */
    @Override
    public Repo selectRepoById(String id)
    {
        return repoMapper.selectRepoById(id);
    }

    /**
     * 查询题库信息列表
     * 
     * @param repo 题库信息
     * @return 题库信息
     */
    @Override
    public List<Repo> selectRepoList(Repo repo)
    {
        return repoMapper.selectRepoList(repo);
    }

    /**
     * 新增题库信息
     * 
     * @param repo 题库信息
     * @return 结果
     */
    @Override
    public int insertRepo(Repo repo)
    {
        repo.setCreateTime(DateUtils.getNowDate());
        repo.setId(IdUtils.simpleUUID());
        return repoMapper.insertRepo(repo);
    }

    /**
     * 修改题库信息
     * 
     * @param repo 题库信息
     * @return 结果
     */
    @Override
    public int updateRepo(Repo repo)
    {
        repo.setUpdateTime(DateUtils.getNowDate());
        return repoMapper.updateRepo(repo);
    }

    /**
     * 批量删除题库信息
     * 
     * @param ids 需要删除的题库信息主键
     * @return 结果
     */
    @Override
    public int deleteRepoByIds(String[] ids)
    {
        return repoMapper.deleteRepoByIds(ids);
    }

    /**
     * 删除题库信息信息
     * 
     * @param id 题库信息主键
     * @return 结果
     */
    @Override
    public int deleteRepoById(String id)
    {
        return repoMapper.deleteRepoById(id);
    }
}
