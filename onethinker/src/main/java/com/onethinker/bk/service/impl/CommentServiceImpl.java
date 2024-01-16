package com.onethinker.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.CommentMapper;
import com.onethinker.bk.domain.Comment;
import com.onethinker.bk.service.ICommentService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 文章评论Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 查询文章评论
     *
     * @param id 文章评论主键
     * @return 文章评论
     */
    @Override
    public Comment selectCommentById(Long id) {
        return commentMapper.selectCommentById(id);
    }

    /**
     * 查询文章评论列表
     *
     * @param comment 文章评论
     * @return 文章评论
     */
    @Override
    public List<Comment> selectCommentList(Comment comment) {
        return commentMapper.selectCommentList(comment);
    }

    /**
     * 新增文章评论
     *
     * @param comment 文章评论
     * @return 结果
     */
    @Override
    public int insertComment(Comment comment) {
                comment.setCreateTime(DateUtils.getNowDate());
            return commentMapper.insertComment(comment);
    }

    /**
     * 修改文章评论
     *
     * @param comment 文章评论
     * @return 结果
     */
    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    /**
     * 批量删除文章评论
     *
     * @param ids 需要删除的文章评论主键
     * @return 结果
     */
    @Override
    public int deleteCommentByIds(Long[] ids) {
        return commentMapper.deleteCommentByIds(ids);
    }

    /**
     * 删除文章评论信息
     *
     * @param id 文章评论主键
     * @return 结果
     */
    @Override
    public int deleteCommentById(Long id) {
        return commentMapper.deleteCommentById(id);
    }
}
