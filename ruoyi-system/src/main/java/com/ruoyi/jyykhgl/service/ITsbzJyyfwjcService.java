package com.ruoyi.jyykhgl.service;

import java.util.List;

import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;
import com.ruoyi.jyykhgl.domain.TsbzJyyfwjc;

/**
 * 服务基层（教研员）Service接口
 *
 * @author tsbz
 * @date 2021-01-16
 */
public interface ITsbzJyyfwjcService {
    /**
     * 查询服务基层（教研员）
     *
     * @param id 服务基层（教研员）ID
     * @return 服务基层（教研员）
     */
    public TsbzJyyfwjc selectTsbzJyyfwjcById(Long id);

    /**
     * 查询服务基层（教研员）列表
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 服务基层（教研员）集合
     */
    public List<TsbzJyyfwjc> selectTsbzJyyfwjcList(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 新增服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    public int insertTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 修改服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    public int updateTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 批量删除服务基层（教研员）
     *
     * @param ids 需要删除的服务基层（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyfwjcByIds(Long[] ids);

    /**
     * 删除服务基层（教研员）信息
     *
     * @param id 服务基层（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyfwjcById(Long id);

    /**
     * 导入数据
     *
     * @param tsbzJyyfwjcList 用户数据列表
     * @param userId
     * @return 结果
     */
    public String importJyyFwjc(List<TsbzJyyfwjc> tsbzJyyfwjcList,Long userId);
}
