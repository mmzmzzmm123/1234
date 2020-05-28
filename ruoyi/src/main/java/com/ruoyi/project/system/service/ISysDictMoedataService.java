package com.ruoyi.project.system.service;

import java.util.List;
import com.ruoyi.project.system.domain.SysDictMoedata;

/**
 * 多级字典管理Service接口
 * 
 * @author tsbz
 * @date 2020-05-28
 */
public interface ISysDictMoedataService 
{
    /**
     * 查询多级字典管理
     * 
     * @param id 多级字典管理ID
     * @return 多级字典管理
     */
    public SysDictMoedata selectSysDictMoedataById(Long id);

    /**
     * 查询多级字典管理列表
     * 
     * @param sysDictMoedata 多级字典管理
     * @return 多级字典管理集合
     */
    public List<SysDictMoedata> selectSysDictMoedataList(SysDictMoedata sysDictMoedata);

    /**
     * 新增多级字典管理
     * 
     * @param sysDictMoedata 多级字典管理
     * @return 结果
     */
    public int insertSysDictMoedata(SysDictMoedata sysDictMoedata);

    /**
     * 修改多级字典管理
     * 
     * @param sysDictMoedata 多级字典管理
     * @return 结果
     */
    public int updateSysDictMoedata(SysDictMoedata sysDictMoedata);

    /**
     * 批量删除多级字典管理
     * 
     * @param ids 需要删除的多级字典管理ID
     * @return 结果
     */
    public int deleteSysDictMoedataByIds(Long[] ids);

    /**
     * 删除多级字典管理信息
     * 
     * @param id 多级字典管理ID
     * @return 结果
     */
    public int deleteSysDictMoedataById(Long id);
}
