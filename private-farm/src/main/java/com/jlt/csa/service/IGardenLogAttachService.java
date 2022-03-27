package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.GardenLogAttach;

/**
 * 菜地日志附件Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface IGardenLogAttachService 
{
    /**
     * 查询菜地日志附件
     * 
     * @param id 菜地日志附件主键
     * @return 菜地日志附件
     */
    public GardenLogAttach selectGardenLogAttachById(Long id);

    /**
     * 查询菜地日志附件列表
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 菜地日志附件集合
     */
    public List<GardenLogAttach> selectGardenLogAttachList(GardenLogAttach gardenLogAttach);

    /**
     * 新增菜地日志附件
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 结果
     */
    public int insertGardenLogAttach(GardenLogAttach gardenLogAttach);

    /**
     * 修改菜地日志附件
     * 
     * @param gardenLogAttach 菜地日志附件
     * @return 结果
     */
    public int updateGardenLogAttach(GardenLogAttach gardenLogAttach);

    /**
     * 批量删除菜地日志附件
     * 
     * @param ids 需要删除的菜地日志附件主键集合
     * @return 结果
     */
    public int deleteGardenLogAttachByIds(Long[] ids);

    /**
     * 删除菜地日志附件信息
     * 
     * @param id 菜地日志附件主键
     * @return 结果
     */
    public int deleteGardenLogAttachById(Long id);
}
