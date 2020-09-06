package com.ruoyi.bookmark.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.bookmark.domain.SqTag;

/**
 * 书签_标签Service接口
 *
 * @author ruoyi
 * @date 2020-09-04
 */
public interface ISqTagService
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
     * 批量删除书签_标签
     *
     * @param ids 需要删除的书签_标签ID
     * @return 结果
     */
    public int deleteSqTagByIds(Long[] ids);

    /**
     * 删除书签_标签信息
     *
     * @param id 书签_标签ID
     * @return 结果
     */
    public int deleteSqTagById(Long id);

    /**
     * @auther: Wang
     * @date: 2020/09/06 15:06
     * 功能描述:修改书签 标签检测传入的标签是否 需要添加
     */
    public Map<String,Object> addtag(String tagName, Long userId);
}
