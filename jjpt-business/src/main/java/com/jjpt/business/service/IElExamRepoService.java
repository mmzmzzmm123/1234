package com.jjpt.business.service;

import java.util.List;
import com.jjpt.business.domain.ElExamRepo;
import com.jjpt.business.domain.dto.ElExamRepoExtDto;

/**
 * 考试题库Service接口
 * 
 * @author 卢亚峰
 * @date 2024-05-19
 */
public interface IElExamRepoService 
{
    /**
     * 查询考试题库
     * 
     * @param id 考试题库主键
     * @return 考试题库
     */
    public ElExamRepo selectElExamRepoById(String id);

    /**
     * 查询考试题库列表
     * 
     * @param elExamRepo 考试题库
     * @return 考试题库集合
     */
    public List<ElExamRepo> selectElExamRepoList(ElExamRepo elExamRepo);

    /**
     * 新增考试题库
     * 
     * @param elExamRepo 考试题库
     * @return 结果
     */
    public int insertElExamRepo(ElExamRepo elExamRepo);

    /**
     * 修改考试题库
     * 
     * @param elExamRepo 考试题库
     * @return 结果
     */
    public int updateElExamRepo(ElExamRepo elExamRepo);

    /**
     * 批量删除考试题库
     * 
     * @param ids 需要删除的考试题库主键集合
     * @return 结果
     */
    public int deleteElExamRepoByIds(String[] ids);

    /**
     * 删除考试题库信息
     * 
     * @param id 考试题库主键
     * @return 结果
     */
    public int deleteElExamRepoById(String id);

    void saveAll(String id, List<ElExamRepoExtDto> repoList);

}
