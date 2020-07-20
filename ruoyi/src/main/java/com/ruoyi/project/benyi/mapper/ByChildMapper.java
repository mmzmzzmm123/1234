package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChild;

/**
 * 幼儿信息Mapper接口
 *
 * @author tsbz
 * @date 2020-07-20
 */
public interface ByChildMapper {
    /**
     * 查询幼儿信息
     *
     * @param id 幼儿信息ID
     * @return 幼儿信息
     */
    public ByChild selectByChildById(Long id);

    /**
     * 查询幼儿信息列表
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息集合
     */
    public List<ByChild> selectByChildList(ByChild byChild);

    /**
     * 新增幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    public int insertByChild(ByChild byChild);

    /**
     * 修改幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    public int updateByChild(ByChild byChild);

    /**
     * 删除幼儿信息
     *
     * @param id 幼儿信息ID
     * @return 结果
     */
    public int deleteByChildById(Long id);

    /**
     * 批量删除幼儿信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByChildByIds(Long[] ids);
}
