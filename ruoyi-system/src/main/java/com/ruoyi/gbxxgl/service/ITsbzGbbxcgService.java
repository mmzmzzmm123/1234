package com.ruoyi.gbxxgl.service;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbbxcg;

/**
 * 办学成果(干部管理-评价与考核)Service接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface ITsbzGbbxcgService {
    /**
     * 查询办学成果(干部管理-评价与考核)
     *
     * @param id 办学成果(干部管理-评价与考核)ID
     * @return 办学成果(干部管理 - 评价与考核)
     */
    public TsbzGbbxcg selectTsbzGbbxcgById(Long id);

    /**
     * 查询办学成果(干部管理-评价与考核)列表
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 办学成果(干部管理 - 评价与考核)集合
     */
    public List<TsbzGbbxcg> selectTsbzGbbxcgList(TsbzGbbxcg tsbzGbbxcg);

    /**
     * 新增办学成果(干部管理-评价与考核)
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 结果
     */
    public int insertTsbzGbbxcg(TsbzGbbxcg tsbzGbbxcg);

    /**
     * 修改办学成果(干部管理-评价与考核)
     *
     * @param tsbzGbbxcg 办学成果(干部管理-评价与考核)
     * @return 结果
     */
    public int updateTsbzGbbxcg(TsbzGbbxcg tsbzGbbxcg);

    /**
     * 批量删除办学成果(干部管理-评价与考核)
     *
     * @param ids 需要删除的办学成果(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbbxcgByIds(Long[] ids);

    /**
     * 删除办学成果(干部管理-评价与考核)信息
     *
     * @param id 办学成果(干部管理-评价与考核)ID
     * @return 结果
     */
    public int deleteTsbzGbbxcgById(Long id);
}
