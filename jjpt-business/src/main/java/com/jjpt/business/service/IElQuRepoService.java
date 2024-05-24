package com.jjpt.business.service;

import com.jjpt.business.domain.ElQuRepo;

import java.util.List;


/**
 * 试题题库关联Service接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface IElQuRepoService 
{
    /**
     * 查询试题题库关联
     * 
     * @param id 试题题库关联主键
     * @return 试题题库关联
     */
    public ElQuRepo selectElQuRepoById(String id);

    /**
     * 查询试题题库关联列表
     * 
     * @param elQuRepo 试题题库关联
     * @return 试题题库关联集合
     */
    public List<ElQuRepo> selectElQuRepoList(ElQuRepo elQuRepo);

    /**
     * 新增试题题库关联
     * 
     * @param elQuRepo 试题题库关联
     * @return 结果
     */
    public int insertElQuRepo(ElQuRepo elQuRepo);

    /**
     * 修改试题题库关联
     * 
     * @param elQuRepo 试题题库关联
     * @return 结果
     */
    public int updateElQuRepo(ElQuRepo elQuRepo);

    /**
     * 批量删除试题题库关联
     * 
     * @param ids 需要删除的试题题库关联主键集合
     * @return 结果
     */
    public int deleteElQuRepoByIds(String[] ids);

    /**
     * 删除试题题库关联信息
     * 
     * @param id 试题题库关联主键
     * @return 结果
     */
    public int deleteElQuRepoById(String id);

    void saveAll(String quId, Integer quType, List<String> repoIds);
}
