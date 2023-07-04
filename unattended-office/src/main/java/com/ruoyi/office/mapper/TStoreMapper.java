package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.vo.WxStoreListQryVo;
import com.ruoyi.office.domain.vo.WxStoreListRspVo;

/**
 * 商家用户店铺Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-29
 */
public interface TStoreMapper extends BaseMapper<TStore> {
    /**
     * 查询商家用户店铺
     *
     * @param id 商家用户店铺主键
     * @return 商家用户店铺
     */
    public TStore selectTStoreById(Long id);

    /**
     * 查询商家用户店铺列表
     *
     * @param tStore 商家用户店铺
     * @return 商家用户店铺集合
     */
    public List<TStore> selectTStoreList(TStore tStore);

    /**
     * 新增商家用户店铺
     *
     * @param tStore 商家用户店铺
     * @return 结果
     */
    public int insertTStore(TStore tStore);

    /**
     * 修改商家用户店铺
     *
     * @param tStore 商家用户店铺
     * @return 结果
     */
    public int updateTStore(TStore tStore);

    /**
     * 删除商家用户店铺
     *
     * @param id 商家用户店铺主键
     * @return 结果
     */
    public int deleteTStoreById(Long id);

    /**
     * 批量删除商家用户店铺
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStoreByIds(Long[] ids);

    List<WxStoreListRspVo> selectWxStoreList(WxStoreListQryVo wxStoreListQryVo);
}
