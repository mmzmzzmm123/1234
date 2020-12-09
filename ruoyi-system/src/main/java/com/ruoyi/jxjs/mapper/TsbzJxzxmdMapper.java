package com.ruoyi.jxjs.mapper;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxzxmd;

/**
 * 见习之星名单Mapper接口
 *
 * @author ruoyi
 * @date 2020-08-23
 */
public interface TsbzJxzxmdMapper {
    /**
     * 查询见习之星名单
     *
     * @param id 见习之星名单ID
     * @return 见习之星名单
     */
    public TsbzJxzxmd selectTsbzJxzxmdById(Long id);

    /**
     * 查询见习之星名单
     *
     * @param nf 见习之星年份
     * @return 见习之星名单
     */
    public TsbzJxzxmd selectMaxZsbhByNf(String nf);

    /**
     * 查询见习之星名单列表
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 见习之星名单集合
     */
    public List<TsbzJxzxmd> selectTsbzJxzxmdList(TsbzJxzxmd tsbzJxzxmd);

    /**
     * 新增见习之星名单
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 结果
     */
    public int insertTsbzJxzxmd(TsbzJxzxmd tsbzJxzxmd);

    /**
     * 修改见习之星名单
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 结果
     */
    public int updateTsbzJxzxmd(TsbzJxzxmd tsbzJxzxmd);

    /**
     * 删除见习之星名单
     *
     * @param id 见习之星名单ID
     * @return 结果
     */
    public int deleteTsbzJxzxmdById(Long id);

    /**
     * 批量删除见习之星名单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxmdByIds(Long[] ids);

    /**
     * 查询见习之星名单列表
     *
     * @param tsbzJxzxmd 见习之星名单
     * @return 见习之星名单集合
     */
    public List<TsbzJxzxmd> selectTsbzJxzxmdKhjdList(TsbzJxzxmd tsbzJxzxmd);
}
