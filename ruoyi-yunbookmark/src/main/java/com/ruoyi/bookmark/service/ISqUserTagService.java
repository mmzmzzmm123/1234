package com.ruoyi.bookmark.service;

import java.util.List;
import java.util.Map;

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
     * 批量减少用戶 标签引用的 数量 -1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    public int updateCountReduce(Long[] tags,Long userId);

    /**
     * 批量添加 用戶 标签引用的 数量 +1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    public int updateCountAdd(Long[] tags,Long userId);




    /**
     *通过用户userID查用户的所有标签
     *
     *
     */

    public List<Map<String,Object>> selectSqUserTagByUserId(Long userId);

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

    /**
     * 删除标签管理信息
     *
     * @param ids 书签标签ID串
     * @param userId 用户ID
     * @return 结果
     */
    public int userRemoveByid(Long[] ids, Long userId);


    /**
     * 用户添加标签
     *
     * @param  sqUserTag
     * @return int
     */
    int insertSqUserTagAdd(SqUserTag sqUserTag);

    /**
     * 用户修改标签
     *
     * @param  sqUserTag
     * @return int
     */
    int updateSqUserTagEdit(SqUserTag sqUserTag);
    /**
     * 查询用户 是否已经有此标签
     *
     * @param  name
     * @param  userId
     * @return int
     */
    int selectCountByName(String name,Long userId);
}
