package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbkykt;

/**
 * 科研课题(干部管理)Service接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface ITsbzGbkyktService {
    /**
     * 查询科研课题(干部管理)
     *
     * @param id 科研课题(干部管理)ID
     * @return 科研课题(干部管理)
     */
    public TsbzGbkykt selectTsbzGbkyktById(Long id);

    /**
     * 查询科研课题(干部管理)列表
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 科研课题(干部管理)集合
     */
    public List<TsbzGbkykt> selectTsbzGbkyktList(TsbzGbkykt tsbzGbkykt);

    /**
     * 新增科研课题(干部管理)
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 结果
     */
    public int insertTsbzGbkykt(TsbzGbkykt tsbzGbkykt);

    /**
     * 修改科研课题(干部管理)
     *
     * @param tsbzGbkykt 科研课题(干部管理)
     * @return 结果
     */
    public int updateTsbzGbkykt(TsbzGbkykt tsbzGbkykt);

    /**
     * 批量删除科研课题(干部管理)
     *
     * @param ids 需要删除的科研课题(干部管理)ID
     * @return 结果
     */
    public int deleteTsbzGbkyktByIds(Long[] ids);

    /**
     * 删除科研课题(干部管理)信息
     *
     * @param id 科研课题(干部管理)ID
     * @return 结果
     */
    public int deleteTsbzGbkyktById(Long id);
}
