package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbywkh;

/**
 * 业务考核(干部管理-评价与考核)Service接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface ITsbzGbywkhService {
    /**
     * 查询业务考核(干部管理-评价与考核)
     *
     * @param id 业务考核(干部管理-评价与考核)ID
     * @return 业务考核(干部管理 - 评价与考核)
     */
    public TsbzGbywkh selectTsbzGbywkhById(Long id);

    /**
     * 查询业务考核(干部管理-评价与考核)列表
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 业务考核(干部管理 - 评价与考核)集合
     */
    public List<TsbzGbywkh> selectTsbzGbywkhList(TsbzGbywkh tsbzGbywkh);

    /**
     * 新增业务考核(干部管理-评价与考核)
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 结果
     */
    public int insertTsbzGbywkh(TsbzGbywkh tsbzGbywkh);

    /**
     * 修改业务考核(干部管理-评价与考核)
     *
     * @param tsbzGbywkh 业务考核(干部管理-评价与考核)
     * @return 结果
     */
    public int updateTsbzGbywkh(TsbzGbywkh tsbzGbywkh);

    /**
     * 批量删除业务考核(干部管理-评价与考核)
     *
     * @param ids 需要删除的业务考核(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbywkhByIds(Long[] ids);

    /**
     * 删除业务考核(干部管理-评价与考核)信息
     *
     * @param id 业务考核(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbywkhById(Long id);
}
