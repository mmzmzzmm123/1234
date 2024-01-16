package com.onethinker.bk.mapper;

import java.util.List;
import com.onethinker.bk.domain.TreeHole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 树洞Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface TreeHoleMapper extends BaseMapper<TreeHole>{
    /**
     * 查询树洞
     *
     * @param id 树洞主键
     * @return 树洞
     */
    public TreeHole selectTreeHoleById(Long id);

    /**
     * 查询树洞列表
     *
     * @param treeHole 树洞
     * @return 树洞集合
     */
    public List<TreeHole> selectTreeHoleList(TreeHole treeHole);

    /**
     * 新增树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    public int insertTreeHole(TreeHole treeHole);

    /**
     * 修改树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    public int updateTreeHole(TreeHole treeHole);

    /**
     * 删除树洞
     *
     * @param id 树洞主键
     * @return 结果
     */
    public int deleteTreeHoleById(Long id);

    /**
     * 批量删除树洞
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTreeHoleByIds(Long[] ids);
}
