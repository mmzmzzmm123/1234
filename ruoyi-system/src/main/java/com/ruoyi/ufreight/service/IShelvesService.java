package com.ruoyi.ufreight.service;

import java.util.List;
import com.ruoyi.ufreight.domain.Shelves;

/**
 * 仓库货架Service接口
 * 
 * @author derek
 * @date 2023-11-20
 */
public interface IShelvesService 
{
    /**
     * 查询仓库货架
     * 
     * @param shelfId 仓库货架主键
     * @return 仓库货架
     */
    public Shelves selectShelvesByShelfId(Long shelfId);

    /**
     * 查询仓库货架列表
     * 
     * @param shelves 仓库货架
     * @return 仓库货架集合
     */
    public List<Shelves> selectShelvesList(Shelves shelves);

    /**
     * 新增仓库货架
     * 
     * @param shelves 仓库货架
     * @return 结果
     */
    public int insertShelves(Shelves shelves);

    /**
     * 修改仓库货架
     * 
     * @param shelves 仓库货架
     * @return 结果
     */
    public int updateShelves(Shelves shelves);

    /**
     * 批量删除仓库货架
     * 
     * @param shelfIds 需要删除的仓库货架主键集合
     * @return 结果
     */
    public int deleteShelvesByShelfIds(Long[] shelfIds);

    /**
     * 删除仓库货架信息
     * 
     * @param shelfId 仓库货架主键
     * @return 结果
     */
    public int deleteShelvesByShelfId(Long shelfId);
}
