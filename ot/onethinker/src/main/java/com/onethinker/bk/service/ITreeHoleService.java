package com.onethinker.bk.service;

import com.onethinker.bk.domain.TreeHole;

import java.util.List;

/**
 * 树洞Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ITreeHoleService {

    /**
     * 新增树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    int insertTreeHole(TreeHole treeHole, String ipAddr);

    /**
     * 修改树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    int updateTreeHole(TreeHole treeHole);

    /**
     * 删除树洞信息
     *
     * @param id 树洞主键
     * @return 结果
     */
    int deleteTreeHoleById(Long id);

    /**
     * 查询树洞信息
     *
     * @return
     */
    List<TreeHole> listTreeHole();
}
