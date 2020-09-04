package com.ruoyi.bookmark.service;

import java.util.List;
import com.ruoyi.bookmark.domain.SqUserTag;

/**
 * 标签管理Service接口
 * 
 * @author wang
 * @date 2020-09-04
 */
public interface ISqUserTagService 
{
    /**
     * 查询标签管理
     * 
     * @param id 标签管理ID
     * @return 标签管理
     */
    public SqUserTag selectSqUserTagById(Long id);

    /**
     * 查询标签管理列表
     * 
     * @param sqUserTag 标签管理
     * @return 标签管理集合
     */
    public List<SqUserTag> selectSqUserTagList(SqUserTag sqUserTag);

    /**
     * 新增标签管理
     * 
     * @param sqUserTag 标签管理
     * @return 结果
     */
    public int insertSqUserTag(SqUserTag sqUserTag);

    /**
     * 修改标签管理
     * 
     * @param sqUserTag 标签管理
     * @return 结果
     */
    public int updateSqUserTag(SqUserTag sqUserTag);

    /**
     * 批量删除标签管理
     * 
     * @param ids 需要删除的标签管理ID
     * @return 结果
     */
    public int deleteSqUserTagByIds(Long[] ids);

    /**
     * 删除标签管理信息
     * 
     * @param id 标签管理ID
     * @return 结果
     */
    public int deleteSqUserTagById(Long id);
}
