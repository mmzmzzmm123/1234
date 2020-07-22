package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByMath;

/**
 * 游戏数学Mapper接口
 *
 * @author tsbz
 * @date 2020-07-20
 */
public interface ByMathMapper
{
    /**
     * 查询游戏数学
     *
     * @param id 游戏数学ID
     * @return 游戏数学
     */
    public ByMath selectByMathById(Long id);

    /**
     * 查询游戏数学列表
     *
     * @param byMath 游戏数学
     * @return 游戏数学集合
     */
    public List<ByMath> selectByMathList(ByMath byMath);

    /**
     * 查询游戏数学树
     *
     * @param byMath 游戏数学
     * @return 游戏数学树集合
     */
    public List<ByMath> selectByMathListTree(ByMath byMath);

    /**
     * 新增游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    public int insertByMath(ByMath byMath);

    /**
     * 修改游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    public int updateByMath(ByMath byMath);

    /**
     * 删除游戏数学
     *
     * @param id 游戏数学ID
     * @return 结果
     */
    public int deleteByMathById(Long id);

    /**
     * 批量删除游戏数学
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByMathByIds(Long[] ids);
}
