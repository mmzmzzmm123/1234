package com.ruoyi.bookmark.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBookmarkTagMapper;
import com.ruoyi.bookmark.domain.SqBookmarkTag;
import com.ruoyi.bookmark.service.ISqBookmarkTagService;

/**
 * 书签标签管理Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-04
 */
@Service
public class SqBookmarkTagServiceImpl implements ISqBookmarkTagService
{



    @Autowired
    private SqBookmarkTagMapper sqBookmarkTagMapper;

    /**
     * 查询该篇文章的 所有书签
     *
     * @param
     * @return 书签_标签
     */
    @Override
    public List<Map<String,Object>> selectSqTaListById(Long bookmarkId){
        return sqBookmarkTagMapper.selectSqTaListById(bookmarkId);
    }
    /**
     * 查询书签标签管理
     *
     * @param id 书签标签管理ID
     * @return 书签标签管理
     */
    @Override
    public SqBookmarkTag selectSqBookmarkTagById(Long id)
    {
        return sqBookmarkTagMapper.selectSqBookmarkTagById(id);
    }

    /**
     * 查询书签标签管理列表
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 书签标签管理
     */
    @Override
    public List<SqBookmarkTag> selectSqBookmarkTagList(SqBookmarkTag sqBookmarkTag)
    {
        return sqBookmarkTagMapper.selectSqBookmarkTagList(sqBookmarkTag);
    }

    /**
     * 新增书签标签管理
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 结果
     */
    @Override
    public int insertSqBookmarkTag(SqBookmarkTag sqBookmarkTag)
    {
        return sqBookmarkTagMapper.insertSqBookmarkTag(sqBookmarkTag);
    }

    /**
     * 修改书签标签管理
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 结果
     */
    @Override
    public int updateSqBookmarkTag(SqBookmarkTag sqBookmarkTag)
    {
        return sqBookmarkTagMapper.updateSqBookmarkTag(sqBookmarkTag);
    }

    /**
     * 批量删除书签标签管理
     *
     * @param ids 需要删除的书签标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkTagByIds(Long[] ids)
    {
        return sqBookmarkTagMapper.deleteSqBookmarkTagByIds(ids);
    }

    /**
     * 删除书签标签管理信息
     *
     * @param id 书签标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkTagById(Long id)
    {
        return sqBookmarkTagMapper.deleteSqBookmarkTagById(id);
    }
}
