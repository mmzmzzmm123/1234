package com.ruoyi.goods.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.goods.mapper.GoodsClassifyMapper;
import com.ruoyi.goods.domain.GoodsClassify;
import com.ruoyi.goods.service.IGoodsClassifyService;

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
            updateDeptChildren(goodsClassify.getClassifyId(), newAncestors, oldAncestors);
        }

        goodsClassify.setUpdateTime(DateUtils.getNowDate());
        return goodsClassifyMapper.updateGoodsClassify(goodsClassify);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的分类ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<GoodsClassify> children = goodsClassifyMapper.selectChildrenClassifyById(deptId);
        for (GoodsClassify child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
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
}
