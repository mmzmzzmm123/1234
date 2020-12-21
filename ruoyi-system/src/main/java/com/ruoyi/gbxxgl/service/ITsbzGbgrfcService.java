package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbgrfc;

/**
 * 个人风采(干部管理-个人状况)Service接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface ITsbzGbgrfcService {
    /**
     * 查询个人风采(干部管理-个人状况)
     *
     * @param id 个人风采(干部管理-个人状况)ID
     * @return 个人风采(干部管理 - 个人状况)
     */
    public TsbzGbgrfc selectTsbzGbgrfcById(Long id);

    /**
     * 查询个人风采(干部管理-个人状况)列表
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 个人风采(干部管理 - 个人状况)集合
     */
    public List<TsbzGbgrfc> selectTsbzGbgrfcList(TsbzGbgrfc tsbzGbgrfc);

    /**
     * 新增个人风采(干部管理-个人状况)
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 结果
     */
    public int insertTsbzGbgrfc(TsbzGbgrfc tsbzGbgrfc);

    /**
     * 修改个人风采(干部管理-个人状况)
     *
     * @param tsbzGbgrfc 个人风采(干部管理-个人状况)
     * @return 结果
     */
    public int updateTsbzGbgrfc(TsbzGbgrfc tsbzGbgrfc);

    /**
     * 批量删除个人风采(干部管理-个人状况)
     *
     * @param ids 需要删除的个人风采(干部管理-个人状况)ID
     * @return 结果
     */
    public int deleteTsbzGbgrfcByIds(Long[] ids);

    /**
     * 删除个人风采(干部管理-个人状况)信息
     *
     * @param id 个人风采(干部管理-个人状况)ID
     * @return 结果
     */
    public int deleteTsbzGbgrfcById(Long id);
}
