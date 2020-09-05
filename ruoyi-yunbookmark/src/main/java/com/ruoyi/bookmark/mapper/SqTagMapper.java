package com.ruoyi.bookmark.mapper;

import java.util.List;
import com.ruoyi.bookmark.domain.SqTag;

/**
 * 书签_标签Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-04
 */
public interface SqTagMapper
{


    /**
     * 查询书签_标签
     *
     * @param id 书签_标签ID
     * @return 书签_标签
     */
    public SqTag selectSqTagById(Long id);

    /**
     * 查询书签_标签列表
     *
     * @param sqTag 书签_标签
     * @return 书签_标签集合
     */
    public List<SqTag> selectSqTagList(SqTag sqTag);

    /**
     * 新增书签_标签
     *
     * @param sqTag 书签_标签
     * @return 结果
     */
    public int insertSqTag(SqTag sqTag);

    /**
     * 修改书签_标签
     *
     * @param sqTag 书签_标签
     * @return 结果
     */
    public int updateSqTag(SqTag sqTag);

    /**
     * 删除书签_标签
     *
     * @param id 书签_标签ID
     * @return 结果
     */
    public int deleteSqTagById(Long id);

    /**
     * 批量删除书签_标签
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSqTagByIds(Long[] ids);
}
