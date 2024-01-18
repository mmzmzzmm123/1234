package com.onethinker.bk.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.onethinker.bk.domain.Article;
import com.onethinker.bk.domain.Label;
import com.onethinker.bk.domain.Sort;
import com.onethinker.bk.domain.WebInfo;
import com.onethinker.bk.enums.CommentTypeEnum;
import com.onethinker.bk.mapper.ArticleMapper;
import com.onethinker.bk.service.*;
import com.onethinker.bk.vo.ArticleVO;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.mail.utils.MailUtil;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.ruoyi.common.constant.BkConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文章Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IWebInfoService webInfoService;

    private static final String REDIS_KEY = "article";

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private ISortService sortService;

    @Autowired
    private ICommentService commentService;

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章
     */
    @Override
    public List<Article> selectArticleList(Article article) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + JSON.toJSONString(article);
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        List<Article> articles = articleMapper.selectArticleList(article);
        if (articles.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey, articles);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return articles;
    }

    /**
     * 新增文章
     *
     * @param articleVO 文章
     * @return 结果
     */
    @Override
    public int insertArticle(ArticleVO articleVO) {
        articleVO.existsParams();
        Article article = new Article();
        BeanUtils.copyProperties(articleVO, article);
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && StringUtils.hasText(articleVO.getPassword())) {
            article.setPassword(articleVO.getPassword());
            article.setTips(articleVO.getTips());
        }
        article.setCreateTime(DateUtils.getNowDate());

        int i = articleMapper.insertArticle(article);
        // 清空文章信息
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        // 发送订阅消息
        try {
            if (articleVO.getViewStatus()) {
                // 获取用户信息
                List<PlatformUserDetail> platformUsers = platformUserDetailService.queryStatus(PlatformUser.STATE_TYPE_ENABLED);
                // 这里默认先全部
                List<String> emails = platformUsers.stream().map(PlatformUserDetail::getDataId).collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(emails)) {
                    Label label = labelService.selectLabelById(article.getLabelId());
                    String text = getSubscribeMail(label.getLabelName(), articleVO.getArticleTitle());
                    mailUtil.sendMailMessage(emails, "您有一封来自" + webInfoService.getWebInfo().getWebName() + "的回执！", text);
                }
            }
        } catch (Exception e) {
            log.error("订阅邮件发送失败：", e);
        }
        return i;
    }

    /**
     * 修改文章
     *
     * @param articleVO 文章
     * @return 结果
     */
    @Override
    public int updateArticle(ArticleVO articleVO) {
        articleVO.existsParams();
        Article article = new Article();
        BeanUtils.copyProperties(articleVO, article);
        article.setUpdateTime(DateUtils.getNowDate());
        return articleMapper.updateArticle(article);
    }

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的文章主键
     * @return 结果
     */
    @Override
    public int deleteArticleByIds(Long[] ids) {
        return articleMapper.deleteArticleByIds(ids);
    }

    /**
     * 删除文章信息
     *
     * @param id 文章主键
     * @return 结果
     */
    @Override
    public int deleteArticleById(Long id) {
        return articleMapper.deleteArticleById(id);

    }

    @Override
    public List<Article> listArticle(BaseRequestVO baseRequestVO) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(baseRequestVO.getSearchKey()), Article::getArticleTitle, baseRequestVO.getSearchKey())
                .eq(baseRequestVO.getStatus() != null && baseRequestVO.getStatus(), Article::getCommentStatus, baseRequestVO.getStatus())
                .eq(Objects.nonNull(baseRequestVO.getLabelId()), Article::getLabelId, baseRequestVO.getLabelId())
                .eq(Objects.nonNull(baseRequestVO.getSortId()), Article::getSortId, baseRequestVO.getSortId())
                .orderByDesc(Article::getCreateTime);

        List<Article> articles = articleMapper.selectList(queryWrapper);

        return articles;
    }

    @Override
    public Map<Integer, List<Article>> listSortArticle() {
        return null;

    }

    @Override
    public ArticleVO getArticleById(Long id, String password) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + id;
        if (!redisCache.hasKey(redisKey)) {
            Article article = articleMapper.selectArticleById(id);
            if (Objects.isNull(article)) {
                return null;
            }
            redisCache.setCacheObject(redisKey, article, 1, TimeUnit.DAYS);
        }
        Article article = JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), Article.class);

        if (!article.getViewStatus() && (!StringUtils.hasText(password) || !password.equals(article.getPassword()))) {
            throw new RuntimeException("密码错误" + (StringUtils.hasText(article.getTips()) ? article.getTips() : "请联系作者获取密码"));
        }
        // 更新统计数
        articleMapper.updateViewCount(id, article.getViewCount());
        article.setPassword(null);
        if (StringUtils.hasText(article.getVideoUrl())) {
            article.setVideoUrl(SecureUtil.aes(BkConstants.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(article.getVideoUrl()));
        }
        ArticleVO articleVO = buildArticleVO(article);
        return articleVO;
    }

    @Override
    public Article selectArticleById(Long source) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + source;
        if (!redisCache.hasKey(redisKey)) {
            Article article = articleMapper.selectArticleById(source);
            if (Objects.isNull(article)) {
                return null;
            }
            redisCache.setCacheObject(redisKey,article,1,TimeUnit.DAYS);
        }
        return redisCache.getCacheObject(redisKey);
    }

    private String getSubscribeMail(String labelName, String articleTitle) {
        WebInfo webInfo = webInfoService.getWebInfo();
        return String.format(mailUtil.getMailText(),
                webInfo.getWebName(),
                String.format(MailUtil.notificationMail, SecurityUtils.getUsername()),
                SecurityUtils.getUsername(),
                String.format(sysConfigService.selectConfigByKey(SysConfigKeyEnum.SUBSCRIBE_FORMAT), labelName, articleTitle),
                "",
                webInfo.getWebName());
    }

    private ArticleVO buildArticleVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);

        PlatformUserDetail platformUserDetail = platformUserDetailService.getPlatFormUserDetailByUserId(articleVO.getUserId());
        if (platformUserDetail != null && StringUtils.hasText(platformUserDetail.getUsername())) {
            articleVO.setUsername(platformUserDetail.getUsername());
        }
        // 评论数据
        if (articleVO.getCommentStatus()) {
            articleVO.setCommentCount(commentService.getCommentCount(articleVO.getId(), CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode()));
        } else {
            articleVO.setCommentCount(0);
        }

        List<Sort> sortInfo = sortService.getSortInfo();
        if (sortInfo.isEmpty()) {
            return articleVO;
        }
        for (Sort s : sortInfo) {
            if (s.getId().intValue() == articleVO.getSortId().intValue()) {
                Sort sort = new Sort();
                BeanUtils.copyProperties(s, sort);
                sort.setLabels(null);
                articleVO.setSort(sort);
                if (!CollectionUtils.isEmpty(s.getLabels())) {
                    for (int j = 0; j < s.getLabels().size(); j++) {
                        Label l = s.getLabels().get(j);
                        if (l.getId().intValue() == articleVO.getLabelId().intValue()) {
                            Label label = new Label();
                            org.springframework.beans.BeanUtils.copyProperties(l, label);
                            articleVO.setLabel(label);
                            break;
                        }
                    }
                }
                break;
            }
        }
        return articleVO;
    }
}
