package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.Article;
import com.onethinker.bk.domain.Comment;
import com.onethinker.bk.enums.CommentTypeEnum;
import com.onethinker.bk.mapper.CommentMapper;
import com.onethinker.bk.service.IArticleService;
import com.onethinker.bk.service.ICommentService;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.CommentVO;
import com.onethinker.mail.utils.MailUtil;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.ruoyi.common.constant.BkConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文章评论Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    public static final String REDIS_KEY = "comment";

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
     * @param commentVO 文章评论
     * @return 结果
     */
    @Override
    public int insertComment(CommentVO commentVO) {
        commentVO.existsPrams();
        Article article = null;
        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
            article = articleService.selectArticleById(commentVO.getSource());
            Assert.isTrue(!ObjectUtils.isEmpty(article), "文章不存在");
            Assert.isTrue(article.getCommentStatus(), "评论功能已关闭");
        }
        Comment comment = new Comment();
        comment.setSource(commentVO.getSource());
        comment.setType(commentVO.getType());
        comment.setCommentContent(commentVO.getCommentContent());
        comment.setParentCommentId(commentVO.getParentCommentId());
        comment.setFloorCommentId(commentVO.getFloorCommentId());
        comment.setParentUserId(commentVO.getParentUserId());
        comment.setUserId(SecurityUtils.getUserId());
        if (StringUtils.hasText(commentVO.getCommentInfo())) {
            comment.setCommentInfo(commentVO.getCommentInfo());
        }
        int i = commentMapper.insertComment(comment);

        try {
            mailUtil.sendCommentMail(commentVO, article, this);
        } catch (Exception e) {
            log.error("发送评论邮件失败：", e);
        }
        // 清除相关缓存信息
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + commentVO.getSource() + "*");
        redisCache.deleteObject(keys);
        return i;
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

        Comment comment = commentMapper.selectCommentById(id);
        Assert.isTrue(Objects.nonNull(comment), "文章评论信息为空");
        int i = commentMapper.deleteCommentById(id);
        // 删除缓存
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + comment.getSource() + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public Integer getCommentCount(Long source, String type) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + source + "_" + type;
        if (!redisCache.hasKey(redisKey)) {
            LambdaQueryChainWrapper<Comment> queryWrapper = new LambdaQueryChainWrapper<>(commentMapper);
            Integer count = queryWrapper.eq(Comment::getSource, source).eq(Comment::getType, type).count();
            redisCache.setCacheObject(redisKey, count, 1, TimeUnit.DAYS);
        }
        return redisCache.getCacheObject(redisKey);
    }

    @Override
    public BaseRequestVO listComment(BaseRequestVO baseRequestVO) {
        baseRequestVO.existsParams();

        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(baseRequestVO.getCommentType())) {
            Article article = articleService.selectArticleById(baseRequestVO.getSource());
            Assert.isTrue(Objects.nonNull(article) && !article.getCommentStatus(), "评论功能已关闭！");
        }

        if (baseRequestVO.getFloorCommentId() == null) {
            Comment comment = new Comment();
            comment.setSource(baseRequestVO.getSource());
            comment.setType(baseRequestVO.getCommentType());
            comment.setParentCommentId(BkConstants.FIRST_COMMENT);
            List<Comment> comments = selectCommentList(comment);
            ;
            if (CollectionUtils.isEmpty(comments)) {
                return baseRequestVO;
            }

            List<CommentVO> commentVOs = comments.stream().map(c -> {
                CommentVO commentVO = buildCommentVO(c);
                Page page = new Page(1, 5);
                lambdaQuery().eq(Comment::getSource, baseRequestVO.getSource()).eq(Comment::getType, baseRequestVO.getCommentType()).eq(Comment::getFloorCommentId, c.getId()).orderByAsc(Comment::getCreateTime).page(page);
                List<Comment> childComments = page.getRecords();
                if (childComments != null) {
                    List<CommentVO> ccVO = childComments.stream().map(cc -> buildCommentVO(cc)).collect(Collectors.toList());
                    page.setRecords(ccVO);
                }
                commentVO.setChildComments(page);
                return commentVO;
            }).collect(Collectors.toList());
//            baseRequestVO.setRecords(commentVOs);
        } else {
            Comment comment = new Comment();
            comment.setSource(baseRequestVO.getSource());
            comment.setType(baseRequestVO.getCommentType());
            comment.setParentCommentId(baseRequestVO.getFloorCommentId());
            List<Comment> childComments = selectCommentList(comment);
            ;
            if (CollectionUtils.isEmpty(childComments)) {
                return baseRequestVO;
            }
            List<CommentVO> ccVO = childComments.stream().map(cc -> buildCommentVO(cc)).collect(Collectors.toList());
//            baseRequestVO.setRecords(ccVO);
        }
        return baseRequestVO;
    }

    private CommentVO buildCommentVO(Comment c) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(c, commentVO);

        PlatformUserDetail user = platformUserDetailService.getPlatFormUserDetailByUserId(commentVO.getUserId());
        if (user != null) {
            commentVO.setAvatar(user.getAvatarUrl());
            commentVO.setUsername(user.getUsername());
        }

        if (commentVO.getParentUserId() != null) {
            PlatformUserDetail u = platformUserDetailService.getPlatFormUserDetailByUserId(commentVO.getParentUserId());
            if (u != null) {
                commentVO.setParentUsername(u.getUsername());
            }
        }
        return commentVO;
    }
}
