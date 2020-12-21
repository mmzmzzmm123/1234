package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbndkh;

/**
 * 年度考核(干部管理-评价与考核)Service接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface ITsbzGbndkhService {
    /**
     * 查询年度考核(干部管理-评价与考核)
     *
     * @param id 年度考核(干部管理-评价与考核)ID
     * @return 年度考核(干部管理 - 评价与考核)
     */
    public TsbzGbndkh selectTsbzGbndkhById(Long id);

    /**
     * 查询年度考核(干部管理-评价与考核)列表
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 年度考核(干部管理 - 评价与考核)集合
     */
    public List<TsbzGbndkh> selectTsbzGbndkhList(TsbzGbndkh tsbzGbndkh);

    /**
     * 新增年度考核(干部管理-评价与考核)
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 结果
     */
    public int insertTsbzGbndkh(TsbzGbndkh tsbzGbndkh);

    /**
     * 修改年度考核(干部管理-评价与考核)
     *
     * @param tsbzGbndkh 年度考核(干部管理-评价与考核)
     * @return 结果
     */
    public int updateTsbzGbndkh(TsbzGbndkh tsbzGbndkh);

    /**
     * 批量删除年度考核(干部管理-评价与考核)
     *
     * @param ids 需要删除的年度考核(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbndkhByIds(Long[] ids);

    /**
     * 删除年度考核(干部管理-评价与考核)信息
     *
     * @param id 年度考核(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbndkhById(Long id);
}
