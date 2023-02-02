package com.ruoyi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.goods.domain.GoodsClassify;

/**
 * 货品分类Service接口
 *
 * @author lixin
 * @date 2023-02-02
 */
public interface IGoodsClassifyService extends IService<GoodsClassify> {
    /**
     * 查询货品分类
     *
     * @param classifyId 货品分类主键
     * @return 货品分类
     */
    public GoodsClassify selectGoodsClassifyByClassifyId(Long classifyId);

    /**
     * 查询货品分类列表
     *
     * @param goodsClassify 货品分类
     * @return 货品分类集合
     */
    public List<GoodsClassify> selectGoodsClassifyList(GoodsClassify goodsClassify);

    /**
     * 根据货品分类ID查询信息
     *
     * @param classifyId 货品分类ID
     * @return 货品分类信息
     */
    public GoodsClassify selectclassifyById(Long classifyId);

    /**
     * 新增货品分类
     *
     * @param goodsClassify 货品分类
     * @return 结果
     */
    public int insertGoodsClassify(GoodsClassify goodsClassify);

    /**
     * 修改货品分类
     *
     * @param goodsClassify 货品分类
     * @return 结果
     */
    public int updateGoodsClassify(GoodsClassify goodsClassify);

    /**
     * 批量删除货品分类
     *
     * @param classifyIds 需要删除的货品分类主键集合
     * @return 结果
     */
    public int deleteGoodsClassifyByClassifyIds(Long[] classifyIds);

    /**
     * 删除货品分类信息
     *
     * @param classifyId 货品分类主键
     * @return 结果
     */
    public int deleteGoodsClassifyByClassifyId(Long classifyId);

    /**
     * 是否存在货品分类子节点
     *
     * @param classifyId 货品分类ID
     * @return 结果
     */
    public boolean hasChildByClassifyId(Long classifyId);
}
