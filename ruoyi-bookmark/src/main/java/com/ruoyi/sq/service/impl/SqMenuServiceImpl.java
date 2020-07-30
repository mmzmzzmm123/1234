package com.ruoyi.sq.service.impl;

import com.ruoyi.sq.domain.SqMenu;
import com.ruoyi.sq.mapper.SqMenuMapper;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.sq.service.ISqMenuService;

/**
 * 书签_菜单Service业务层处理
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@Service
public class SqMenuServiceImpl implements ISqMenuService 
{
    @Autowired
    private SqMenuMapper sqMenuMapper;

    /**
     * 查询书签_菜单
     * 
     * @param menuId 书签_菜单ID
     * @return 书签_菜单
     */
    @Override
    public SqMenu selectSqMenuById(Long menuId)
    {
        return sqMenuMapper.selectSqMenuById(menuId);
    }

    /**
     * 查询书签_菜单列表
     * 
     * @param sqMenu 书签_菜单
     * @return 书签_菜单
     */
    @Override
    public List<SqMenu> selectSqMenuList(SqMenu sqMenu)
    {
        return sqMenuMapper.selectSqMenuList(sqMenu);
    }

    /**
     * 新增书签_菜单
     * 
     * @param sqMenu 书签_菜单
     * @return 结果
     */
    @Override
    public int insertSqMenu(SqMenu sqMenu)
    {
        sqMenu.setCreateTime(DateUtils.getNowDate());
        return sqMenuMapper.insertSqMenu(sqMenu);
    }

    /**
     * 修改书签_菜单
     * 
     * @param sqMenu 书签_菜单
     * @return 结果
     */
    @Override
    public int updateSqMenu(SqMenu sqMenu)
    {
        return sqMenuMapper.updateSqMenu(sqMenu);
    }

    /**
     * 批量删除书签_菜单
     * 
     * @param menuIds 需要删除的书签_菜单ID
     * @return 结果
     */
    @Override
    public int deleteSqMenuByIds(Long[] menuIds)
    {
        return sqMenuMapper.deleteSqMenuByIds(menuIds);
    }

    /**
     * 删除书签_菜单信息
     * 
     * @param menuId 书签_菜单ID
     * @return 结果
     */
    @Override
    public int deleteSqMenuById(Long menuId)
    {
        return sqMenuMapper.deleteSqMenuById(menuId);
    }
}
