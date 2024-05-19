package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.Article;
import com.onethinker.bk.domain.WeiYan;
import com.onethinker.bk.enums.WeiYanEnum;
import com.onethinker.bk.mapper.WeiYanMapper;
import com.onethinker.bk.service.IArticleService;
import com.onethinker.bk.service.IWeiYanService;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 微言Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class WeiYanServiceImpl extends ServiceImpl<WeiYanMapper, WeiYan> implements IWeiYanService {
    @Resource
    private WeiYanMapper weiYanMapper;

    @Autowired
    private IArticleService articleService;

    /**
     * 查询微言
     *
     * @param id 微言主键
     * @return 微言
     */
    @Override
    public WeiYan selectWeiYanById(Long id) {
        return weiYanMapper.selectWeiYanById(id);
    }

    /**
     * 查询微言列表
     *
     * @param weiYan 微言
     * @return 微言
     */
    @Override
    public List<WeiYan> selectWeiYanList(WeiYan weiYan, String weiYanType) {
        Assert.isTrue(Objects.nonNull(weiYan.getSource()), "来源不能为空！");
        // 是否显示未公开内容,如果请求没有携带userid，那么就以缓存中userId为主，显示未公开内容，请求有携带userId，那就需要与权限中的userId一致，才显示未公开内容
        Boolean showPrivate = false;
        if (BkConstants.WEIYAN_TYPE_FRIEND.equals(weiYanType)) {
            if (weiYan.getUserId() == null) {
                showPrivate = true;
            } else if (weiYan.getUserId().equals(SecurityUtils.getUserId())) {
                showPrivate = true;
            }
        }
        LambdaQueryWrapper<WeiYan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WeiYan::getType, weiYanType)
                .eq(WeiYan::getSource, weiYan.getSource())
                .eq(!showPrivate, WeiYan::getIsPublic, WeiYanEnum.PUBLIC.getCode())
                .eq(showPrivate, WeiYan::getUserId, SecurityUtils.getUserId())
                .orderByDesc(WeiYan::getCreateTime);


        List<WeiYan> weiYans = weiYanMapper.selectList(queryWrapper);
        return weiYans;
    }

    /**
     * 新增微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    @Override
    public int insertWeiYan(WeiYan weiYan, String weiYanType) {
        weiYan.existsParams(weiYanType);
        if (BkConstants.WEIYAN_TYPE_NEWS.equals(weiYanType)) {
            // 检查来源是否正确
            Long userId = SecurityUtils.getUserId();
            Article article = articleService.selectArticleById(weiYan.getSource());
            Assert.isTrue(Objects.nonNull(article) && article.getUserId().equals(userId), "来源有误!");
        }
        weiYan.setCreateTime(DateUtils.getNowDate());
        return weiYanMapper.insertWeiYan(weiYan);
    }

    /**
     * 修改微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    @Override
    public int updateWeiYan(WeiYan weiYan) {
        return weiYanMapper.updateWeiYan(weiYan);
    }

    /**
     * 批量删除微言
     *
     * @param ids 需要删除的微言主键
     * @return 结果
     */
    @Override
    public int deleteWeiYanByIds(Long[] ids) {
        return weiYanMapper.deleteWeiYanByIds(ids);
    }

    /**
     * 删除微言信息
     *
     * @param id 微言主键
     * @return 结果
     */
    @Override
    public int deleteWeiYanById(Long id) {
        LambdaQueryWrapper<WeiYan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WeiYan::getId, id).eq(WeiYan::getUserId, SecurityUtils.getUserId());

        return weiYanMapper.delete(queryWrapper);
    }

    @Override
    public void insertWeiYanByDataId(Long puUserId) {
        WeiYan weiYan = new WeiYan();
        weiYan.setUserId(puUserId);
        weiYan.setContent("到此一游");
        weiYan.setType(BkConstants.WEIYAN_TYPE_FRIEND);
        weiYan.setIsPublic(Boolean.TRUE);
        weiYanMapper.insertWeiYan(weiYan);
    }
}
