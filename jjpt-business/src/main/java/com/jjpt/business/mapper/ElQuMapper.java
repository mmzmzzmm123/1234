package com.jjpt.business.mapper;

import com.jjpt.business.domain.ElQu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 试题管理Mapper接口
 * 
 * @author 卢亚峰
 * @date 2024-05-17
 */
public interface ElQuMapper 
{
    /**
     * 查询试题管理
     * 
     * @param id 试题管理主键
     * @return 试题管理
     */
    public ElQu selectElQuById(String id);

    /**
     * 查询试题管理列表
     * 
     * @param elQu 试题管理
     * @return 试题管理集合
     */
    public List<ElQu> selectElQuList(ElQu elQu);

    /**
     * 新增试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    public int insertElQu(ElQu elQu);

    /**
     * 修改试题管理
     * 
     * @param elQu 试题管理
     * @return 结果
     */
    public int updateElQu(ElQu elQu);

    /**
     * 删除试题管理
     * 
     * @param id 试题管理主键
     * @return 结果
     */
    public int deleteElQuById(String id);

    /**
     * 批量删除试题管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElQuByIds(String[] ids);

    List<ElQu> listByRandom(@Param("repoId") String repoId,
                          @Param("quType") Integer quType,
                          @Param("excludes") List<String> excludes,
                          @Param("size") Integer size);
}
