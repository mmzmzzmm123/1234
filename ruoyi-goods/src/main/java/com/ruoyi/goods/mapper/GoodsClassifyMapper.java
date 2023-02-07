package com.ruoyi.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.goods.domain.GoodsClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货品分类Mapper接口
 *
 * @author lixin
 * @date 2023-02-02
 */
public interface GoodsClassifyMapper extends BaseMapper<GoodsClassify> {
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
    public GoodsClassify selectClassifyById(Long classifyId);

    /**
     * 根据ID查询所有子分类
     *
     * @param classifyId 分类ID
     * @return 分类列表
     */
    public List<GoodsClassify> selectChildrenClassifyById(Long classifyId);

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
     * 删除货品分类
     *
     * @param classifyId 货品分类主键
     * @return 结果
     */
    public int deleteGoodsClassifyByClassifyId(Long classifyId);

    /**
     * 批量删除货品分类
     *
     * @param classifyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsClassifyByClassifyIds(Long[] classifyIds);


    /**
     * 是否存在货品分类子节点
     *
     * @param classifyId 货品分类ID
     * @return 结果
     */
    public int hasChildByClassifyId(Long classifyId);

    /**
     * 修改子元素关系
     *
     * @param classifys 子元素
     * @return 结果
     */
    public int updateClassifyChildren(@Param("classifys") List<GoodsClassify> classifys);

    /**
     * 修改所在分类正常状态
     *
     * @param classifyIds 分类ID组
     */
    public void updateClassifyStatusNormal(Long[] classifyIds);

    /**
     * 查询所在层级
     *
     * @param classifyId
     * @return
     */
    public Integer selectLevel(Long classifyId);


    /**
     * 查询所在层级最大子层级
     *
     * @param classifyId
     * @return
     */
    public Integer selectLevelMax(Long classifyId);
}
