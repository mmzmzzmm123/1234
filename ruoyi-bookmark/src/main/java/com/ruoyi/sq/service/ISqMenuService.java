package com.ruoyi.sq.service;

import com.ruoyi.sq.domain.SqMenu;

import java.util.List;

/**
 * 书签_菜单Service接口
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public interface ISqMenuService 
{
    /**
     * 查询书签_菜单
     * 
     * @param menuId 书签_菜单ID
     * @return 书签_菜单
     */
    public SqMenu selectSqMenuById(Long menuId);

    /**
     * 查询书签_菜单列表
     * 
     * @param sqMenu 书签_菜单
     * @return 书签_菜单集合
     */
    public List<SqMenu> selectSqMenuList(SqMenu sqMenu);

    /**
     * 新增书签_菜单
     * 
     * @param sqMenu 书签_菜单
     * @return 结果
     */
    public int insertSqMenu(SqMenu sqMenu);

    /**
     * 修改书签_菜单
     * 
     * @param sqMenu 书签_菜单
     * @return 结果
     */
    public int updateSqMenu(SqMenu sqMenu);

    /**
     * 批量删除书签_菜单
     * 
     * @param menuIds 需要删除的书签_菜单ID
     * @return 结果
     */
    public int deleteSqMenuByIds(Long[] menuIds);

    /**
     * 删除书签_菜单信息
     * 
     * @param menuId 书签_菜单ID
     * @return 结果
     */
    public int deleteSqMenuById(Long menuId);
}
