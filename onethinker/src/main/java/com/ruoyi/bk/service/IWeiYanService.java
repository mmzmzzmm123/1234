package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.WeiYan;

/**
 * 微言Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IWeiYanService {
    /**
     * 查询微言
     *
     * @param id 微言主键
     * @return 微言
     */
    public WeiYan selectWeiYanById(Long id);

    /**
     * 查询微言列表
     *
     * @param weiYan 微言
     * @return 微言集合
     */
    public List<WeiYan> selectWeiYanList(WeiYan weiYan);

    /**
     * 新增微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    public int insertWeiYan(WeiYan weiYan);

    /**
     * 修改微言
     *
     * @param weiYan 微言
     * @return 结果
     */
    public int updateWeiYan(WeiYan weiYan);

    /**
     * 批量删除微言
     *
     * @param ids 需要删除的微言主键集合
     * @return 结果
     */
    public int deleteWeiYanByIds(Long[] ids);

    /**
     * 删除微言信息
     *
     * @param id 微言主键
     * @return 结果
     */
    public int deleteWeiYanById(Long id);
}
