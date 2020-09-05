package com.ruoyi.bookmark.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.common.mybatisMapper.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 标签管理Mapper接口
 *
 * @author wang
 * @date 2020-09-04
 */
public interface SqUserTagMapper  extends MyMapper<SqUserTag>
{

    /**
     * 批量减少用戶 标签引用的 数量 -1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    public int updateCountReduce(@Param("tags")Long[] tags, @Param("userId")Long userId);

    /**
     * 批量添加 用戶 标签引用的 数量 +1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    public int updateCountAdd(@Param("tags")Long[] tags, @Param("userId")Long userId);



    /**
     *通过用户userID查用户的所有标签
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
     * 删除标签管理
     *
     * @param id 标签管理ID
     * @return 结果
     */
    public int deleteSqUserTagById(Long id);

    /**
     * 批量删除标签管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSqUserTagByIds(Long[] ids);
}
