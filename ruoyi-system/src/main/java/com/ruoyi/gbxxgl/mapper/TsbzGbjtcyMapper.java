package com.ruoyi.gbxxgl.mapper;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbjtcy;

/**
 * 家庭成员(干部管理-个人状况)Mapper接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface TsbzGbjtcyMapper {
    /**
     * 查询家庭成员(干部管理-个人状况)
     *
     * @param id 家庭成员(干部管理-个人状况)ID
     * @return 家庭成员(干部管理 - 个人状况)
     */
    public TsbzGbjtcy selectTsbzGbjtcyById(Long id);

    /**
     * 查询家庭成员(干部管理-个人状况)列表
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 家庭成员(干部管理 - 个人状况)集合
     */
    public List<TsbzGbjtcy> selectTsbzGbjtcyList(TsbzGbjtcy tsbzGbjtcy);

    /**
     * 新增家庭成员(干部管理-个人状况)
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 结果
     */
    public int insertTsbzGbjtcy(TsbzGbjtcy tsbzGbjtcy);

    /**
     * 修改家庭成员(干部管理-个人状况)
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 结果
     */
    public int updateTsbzGbjtcy(TsbzGbjtcy tsbzGbjtcy);

    /**
     * 删除家庭成员(干部管理-个人状况)
     *
     * @param id 家庭成员(干部管理-个人状况)ID
     * @return 结果
     */
    public int deleteTsbzGbjtcyById(Long id);

    /**
     * 批量删除家庭成员(干部管理-个人状况)
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzGbjtcyByIds(Long[] ids);
}
