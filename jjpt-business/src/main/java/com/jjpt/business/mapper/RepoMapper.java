package com.jjpt.business.mapper;

import java.util.List;
import com.jjpt.business.domain.Repo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题库信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-11
 */

public interface RepoMapper 
{
    /**
     * 查询题库信息
     * 
     * @param id 题库信息主键
     * @return 题库信息
     */
    public Repo selectRepoById(String id);

    /**
     * 查询题库信息列表
     * 
     * @param repo 题库信息
     * @return 题库信息集合
     */
    public List<Repo> selectRepoList(Repo repo);

    /**
     * 新增题库信息
     * 
     * @param repo 题库信息
     * @return 结果
     */
    public int insertRepo(Repo repo);

    /**
     * 修改题库信息
     * 
     * @param repo 题库信息
     * @return 结果
     */
    public int updateRepo(Repo repo);

    /**
     * 删除题库信息
     * 
     * @param id 题库信息主键
     * @return 结果
     */
    public int deleteRepoById(String id);

    /**
     * 批量删除题库信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepoByIds(String[] ids);
}
