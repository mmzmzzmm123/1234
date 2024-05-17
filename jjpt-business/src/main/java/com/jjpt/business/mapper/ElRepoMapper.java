package com.jjpt.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jjpt.business.domain.ElRepo;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 题库管理Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-16
 */
@MapperScan
public interface ElRepoMapper extends BaseMapper<ElRepo>
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
    public List<ElRepo> selectElRepoList(ElRepo elRepo);

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
     * 删除题库管理
     * 
     * @param id 题库管理主键
     * @return 结果
     */
    public int deleteElRepoById(String id);

    /**
     * 批量删除题库管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElRepoByIds(String[] ids);
}
