package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.TreeHole;

/**
 * 树洞Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ITreeHoleService {
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
     * 批量删除树洞
     *
     * @param ids 需要删除的树洞主键集合
     * @return 结果
     */
    public int deleteTreeHoleByIds(Long[] ids);

    /**
     * 删除树洞信息
     *
     * @param id 树洞主键
     * @return 结果
     */
    public int deleteTreeHoleById(Long id);
}
