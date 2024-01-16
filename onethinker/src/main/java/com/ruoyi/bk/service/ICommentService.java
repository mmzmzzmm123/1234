package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.Comment;

/**
 * 文章评论Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ICommentService {
    /**
     * 查询文章评论
     *
     * @param id 文章评论主键
     * @return 文章评论
     */
    public Comment selectCommentById(Long id);

    /**
     * 查询文章评论列表
     *
     * @param comment 文章评论
     * @return 文章评论集合
     */
    public List<Comment> selectCommentList(Comment comment);

    /**
     * 新增文章评论
     *
     * @param comment 文章评论
     * @return 结果
     */
    public int insertComment(Comment comment);

    /**
     * 修改文章评论
     *
     * @param comment 文章评论
     * @return 结果
     */
    public int updateComment(Comment comment);

    /**
     * 批量删除文章评论
     *
     * @param ids 需要删除的文章评论主键集合
     * @return 结果
     */
    public int deleteCommentByIds(Long[] ids);

    /**
     * 删除文章评论信息
     *
     * @param id 文章评论主键
     * @return 结果
     */
    public int deleteCommentById(Long id);
}
