package com.ruoyi.ufreight.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ufreight.mapper.ShelvesMapper;
import com.ruoyi.ufreight.domain.Shelves;
import com.ruoyi.ufreight.service.IShelvesService;

/**
 * 仓库货架Service业务层处理
 * 
 * @author derek
 * @date 2023-11-20
 */
@Service
public class ShelvesServiceImpl implements IShelvesService 
{
    @Autowired
    private ShelvesMapper shelvesMapper;

    /**
     * 查询仓库货架
     * 
     * @param shelfId 仓库货架主键
     * @return 仓库货架
     */
    @Override
    public Shelves selectShelvesByShelfId(Long shelfId)
    {
        return shelvesMapper.selectShelvesByShelfId(shelfId);
    }

    /**
     * 查询仓库货架列表
     * 
     * @param shelves 仓库货架
     * @return 仓库货架
     */
    @Override
    public List<Shelves> selectShelvesList(Shelves shelves)
    {
        return shelvesMapper.selectShelvesList(shelves);
    }

    /**
     * 新增仓库货架
     * 
     * @param shelves 仓库货架
     * @return 结果
     */
    @Override
    public int insertShelves(Shelves shelves)
    {
        shelves.setCreateTime(DateUtils.getNowDate());
        return shelvesMapper.insertShelves(shelves);
    }

    /**
     * 修改仓库货架
     * 
     * @param shelves 仓库货架
     * @return 结果
     */
    @Override
    public int updateShelves(Shelves shelves)
    {
        shelves.setUpdateTime(DateUtils.getNowDate());
        return shelvesMapper.updateShelves(shelves);
    }

    /**
     * 批量删除仓库货架
     * 
     * @param shelfIds 需要删除的仓库货架主键
     * @return 结果
     */
    @Override
    public int deleteShelvesByShelfIds(Long[] shelfIds)
    {
        return shelvesMapper.deleteShelvesByShelfIds(shelfIds);
    }

    /**
     * 删除仓库货架信息
     * 
     * @param shelfId 仓库货架主键
     * @return 结果
     */
    @Override
    public int deleteShelvesByShelfId(Long shelfId)
    {
        return shelvesMapper.deleteShelvesByShelfId(shelfId);
    }
}
