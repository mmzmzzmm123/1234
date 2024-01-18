package com.onethinker.bk.service;

import com.onethinker.bk.domain.Comment;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.CommentVO;

import java.util.List;

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
    Comment selectCommentById(Long id);

    /**
     * 查询文章评论列表
     *
     * @param comment 文章评论
     * @return 文章评论集合
     */
    List<Comment> selectCommentList(Comment comment);

    /**
     * 新增文章评论
     *
     * @param commentVO 文章评论
     * @return 结果
     */
    int insertComment(CommentVO commentVO);

    /**
     * 修改文章评论
     *
     * @param comment 文章评论
     * @return 结果
     */
    int updateComment(Comment comment);

    /**
     * 批量删除文章评论
     *
     * @param ids 需要删除的文章评论主键集合
     * @return 结果
     */
    int deleteCommentByIds(Long[] ids);

    /**
     * 删除文章评论信息
     *
     * @param id 文章评论主键
     * @return 结果
     */
    int deleteCommentById(Long id);

    /**
     * 获取文章统计数
     * @param id
     * @param code
     * @return
     */
    Integer getCommentCount(Long id, String code);

    /**
     * 查询评论信息
     * @param baseRequestVO
     * @return
     */
    BaseRequestVO listComment(BaseRequestVO baseRequestVO);
}
