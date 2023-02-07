package com.ruoyi.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.goods.domain.GoodsClassify;
import com.ruoyi.goods.mapper.GoodsClassifyMapper;
import com.ruoyi.goods.service.IGoodsClassifyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 货品分类Service业务层处理
 *
 * @author lixin
 * @date 2023-02-02
 */
@Service
@AllArgsConstructor
public class GoodsClassifyServiceImpl extends ServiceImpl<GoodsClassifyMapper, GoodsClassify> implements IGoodsClassifyService {
    private GoodsClassifyMapper goodsClassifyMapper;

    /**
     * 查询货品分类
     *
     * @param classifyId 货品分类主键
     * @return 货品分类
     */
    @Override
    public GoodsClassify selectGoodsClassifyByClassifyId(Long classifyId) {
        return goodsClassifyMapper.selectGoodsClassifyByClassifyId(classifyId);
    }

    /**
     * 查询货品分类列表
     *
     * @param goodsClassify 货品分类
     * @return 货品分类
     */
    @Override
    public List<GoodsClassify> selectGoodsClassifyList(GoodsClassify goodsClassify) {
        return goodsClassifyMapper.selectGoodsClassifyList(goodsClassify);
    }

    @Override
    public GoodsClassify selectclassifyById(Long classifyId) {
        return goodsClassifyMapper.selectClassifyById(classifyId);
    }


    /**
     * 新增货品分类
     *
     * @param goodsClassify 货品分类
     * @return 结果
     */
    @Override
    public int insertGoodsClassify(GoodsClassify goodsClassify) {
        GoodsClassify info = null;
        if (goodsClassify.getParentId() != null && goodsClassify.getParentId() > 0) {
            info = goodsClassifyMapper.selectClassifyById(goodsClassify.getParentId());

        }
        if (info == null) {
            goodsClassify.setAncestors("0");
        } else {
            goodsClassify.setAncestors(info.getAncestors() + "," + goodsClassify.getParentId());
        }

        goodsClassify.setCreateTime(DateUtils.getNowDate());
        return goodsClassifyMapper.insertGoodsClassify(goodsClassify);
    }

    /**
     * 修改货品分类
     *
     * @param goodsClassify 货品分类
     * @return 结果
     */
    @Override
    public int updateGoodsClassify(GoodsClassify goodsClassify) {

        GoodsClassify newParentClassify = goodsClassifyMapper.selectClassifyById(goodsClassify.getParentId());
        GoodsClassify oldClassify = goodsClassifyMapper.selectClassifyById(goodsClassify.getClassifyId());
        if (StringUtils.isNotNull(oldClassify)) {
            String newAncestors = "0";
            if (goodsClassify.getParentId() != null && goodsClassify.getParentId() > 0) {
                newAncestors = newParentClassify.getAncestors() + "," + newParentClassify.getClassifyId();
            }
            String oldAncestors = oldClassify.getAncestors();
            goodsClassify.setAncestors(newAncestors);
            updateDeptChildren(goodsClassify.getClassifyId(), newAncestors, oldAncestors, goodsClassify.getStatus());
        }

        goodsClassify.setUpdateTime(DateUtils.getNowDate());
        int result = goodsClassifyMapper.updateGoodsClassify(goodsClassify);

        if (UserConstants.NORMAL.equals(goodsClassify.getStatus()) && StringUtils.isNotEmpty(goodsClassify.getAncestors())
                && !StringUtils.equals("0", goodsClassify.getAncestors())) {
            // 如果该分类是启用状态，则启用该分类的所有上级分类
            updateParentClassifyStatusNormal(goodsClassify);
        }
        return result;
    }

    /**
     * 修改子元素关系
     *
     * @param ClassifyId   被修改的分类ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     * @param status       状态
     */
    public void updateDeptChildren(Long ClassifyId, String newAncestors, String oldAncestors, String status) {
        List<GoodsClassify> children = goodsClassifyMapper.selectChildrenClassifyById(ClassifyId);
        for (GoodsClassify child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            child.setStatus(status);
        }
        if (children.size() > 0) {
            goodsClassifyMapper.updateClassifyChildren(children);
        }
    }

    /**
     * 批量删除货品分类
     *
     * @param classifyIds 需要删除的货品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsClassifyByClassifyIds(Long[] classifyIds) {
        return goodsClassifyMapper.deleteGoodsClassifyByClassifyIds(classifyIds);
    }

    /**
     * 删除货品分类信息
     *
     * @param classifyId 货品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsClassifyByClassifyId(Long classifyId) {
        return goodsClassifyMapper.deleteGoodsClassifyByClassifyId(classifyId);
    }


    /**
     * 是否存在货品分类子节点
     *
     * @param classifyId 货品分类ID
     * @return 结果
     */
    @Override
    public boolean hasChildByClassifyId(Long classifyId) {
        int result = goodsClassifyMapper.hasChildByClassifyId(classifyId);
        return result > 0;
    }

    /**
     * 查询所在层级
     *
     * @param classifyId
     * @return
     */
    @Override
    public Integer selectLevel(Long classifyId) {
        return goodsClassifyMapper.selectLevel(classifyId);
    }


    /**
     * 查询所在层级最大子层级
     *
     * @param classifyId
     * @return
     */
    @Override
    public Integer selectLevelMax(Long classifyId) {
        return goodsClassifyMapper.selectLevelMax(classifyId);
    }


    /**
     * 修改该分类的父级部门状态
     *
     * @param goodsClassify 当前分类
     */
    private void updateParentClassifyStatusNormal(GoodsClassify goodsClassify) {
        String ancestors = goodsClassify.getAncestors();
        Long[] classifyIds = Convert.toLongArray(ancestors);
        goodsClassifyMapper.updateClassifyStatusNormal(classifyIds);
    }
}
