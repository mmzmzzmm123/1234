package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByTheme;

/**
 * 主题整合Mapper接口
 *
 * @author tsbz
 * @date 2020-07-01
 */
public interface ByThemeMapper {
    /**
     * 查询主题整合
     *
     * @param id 主题整合ID
     * @return 主题整合
     */
    public ByTheme selectByThemeById(Long id);

    /**
     * 查询主题整合列表
     *
     * @param byTheme 主题整合
     * @return 主题整合集合
     */
    public List<ByTheme> selectByThemeList(ByTheme byTheme);

    /**
     * 查询一日流程树
     *
     * @param byTheme 一日流程
     * @return 一日流程树集合
     */
    public List<ByTheme> selectByThemeListTree(ByTheme byTheme);

    /**
     * 新增主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    public int insertByTheme(ByTheme byTheme);

    /**
     * 修改主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    public int updateByTheme(ByTheme byTheme);

    /**
     * 删除主题整合
     *
     * @param id 主题整合ID
     * @return 结果
     */
    public int deleteByThemeById(Long id);

    /**
     * 批量删除主题整合
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByThemeByIds(Long[] ids);

    /**
     * 查询主题整合列表
     *
     * @param ids 主题整合
     * @return 主题整合集合
     */
    public List<ByTheme> selectByThemeByIds(Long[] ids);

    /**
     * 查询主题整合列表
     *
     * @param ids 主题整合
     * @return 主题整合集合
     */
    public List<ByTheme> selectByThemeByActivityIds(Long[] ids);
}
