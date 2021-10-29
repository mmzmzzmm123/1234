package com.ruoyi.bookmark.mapper;

import java.util.List;

import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.common.mybatisMapper.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 书签_标签Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-04
 */
public interface SqTagMapper extends MyMapper<SqTag>
{




   /**
    * 通过标签名字查看是否存在
    *
    * @param name
    * @return 书签_标签集合
    */
    public List<SqTag> selectCountByName(String name);
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


    /**
     * 批量修改对应书签的 标签
     *
     * @param tagId 修改前引用的ID
     * @param toTagId 修改标签后引用的ID
     * @param userId 修改前引用的ID
     * @return 结果
     */
    int updateBookmarkTagIdByTagId(@Param("tagId")Long tagId, @Param("toTagId")Long toTagId, @Param("userId")Long userId);

    /**
     * @auther: Wang
     * @date: 2021/10/29 18:13
     * 功能描述:刪除标签
     */
    int delteTagByidAndUserId(@Param("id")Long id, @Param("userId")Long userId);
}
