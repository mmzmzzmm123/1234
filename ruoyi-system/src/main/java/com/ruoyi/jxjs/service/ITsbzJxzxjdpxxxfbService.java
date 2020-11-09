package com.ruoyi.jxjs.service;

import java.util.List;
import com.ruoyi.jxjs.domain.TsbzJxzxjdpxxxfb;

/**
 * 基地培训信息发布Service接口
 * 
 * @author ruoyi
 * @date 2020-11-09
 */
public interface ITsbzJxzxjdpxxxfbService 
{
    /**
     * 查询基地培训信息发布
     * 
     * @param id 基地培训信息发布ID
     * @return 基地培训信息发布
     */
    public TsbzJxzxjdpxxxfb selectTsbzJxzxjdpxxxfbById(Long id);

    /**
     * 查询基地培训信息发布列表
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 基地培训信息发布集合
     */
    public List<TsbzJxzxjdpxxxfb> selectTsbzJxzxjdpxxxfbList(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb);

    /**
     * 新增基地培训信息发布
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 结果
     */
    public int insertTsbzJxzxjdpxxxfb(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb);

    /**
     * 修改基地培训信息发布
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 结果
     */
    public int updateTsbzJxzxjdpxxxfb(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb);

    /**
     * 批量删除基地培训信息发布
     * 
     * @param ids 需要删除的基地培训信息发布ID
     * @return 结果
     */
    public int deleteTsbzJxzxjdpxxxfbByIds(Long[] ids);

    /**
     * 删除基地培训信息发布信息
     * 
     * @param id 基地培训信息发布ID
     * @return 结果
     */
    public int deleteTsbzJxzxjdpxxxfbById(Long id);
}
