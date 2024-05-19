package com.jjpt.business.service;

import java.util.List;
import com.jjpt.business.domain.ElRepo;
import com.jjpt.business.domain.dto.ElRepoDto;

/**
 * 题库管理Service接口
 * 
 * @author ruoyi
 * @date 2024-05-16
 */
public interface IElRepoService 
{
    /**
     * 查询题库管理
     * 
     * @param id 题库管理主键
     * @return 题库管理
     */
    public ElRepo selectElRepoById(String id);

    /**
     * 查询题库管理列表
     * 
     * @param elRepo 题库管理
     * @return 题库管理集合
     */
    public List<ElRepoDto> selectElRepoList(ElRepo elRepo);

    /**
     * 新增题库管理
     * 
     * @param elRepo 题库管理
     * @return 结果
     */
    public int insertElRepo(ElRepo elRepo);

    /**
     * 修改题库管理
     * 
     * @param elRepo 题库管理
     * @return 结果
     */
    public int updateElRepo(ElRepo elRepo);

    /**
     * 批量删除题库管理
     * 
     * @param ids 需要删除的题库管理主键集合
     * @return 结果
     */
    public int deleteElRepoByIds(String[] ids);

    /**
     * 删除题库管理信息
     * 
     * @param id 题库管理主键
     * @return 结果
     */
    public int deleteElRepoById(String id);
}
