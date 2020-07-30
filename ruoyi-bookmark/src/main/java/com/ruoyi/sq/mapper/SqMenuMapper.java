package com.ruoyi.sq.mapper;

import java.util.List;
import com.ruoyi.sq.domain.SqMenu;

/**
 * 书签_菜单Mapper接口
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public interface SqMenuMapper 
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
     * 删除书签_菜单
     * 
     * @param menuId 书签_菜单ID
     * @return 结果
     */
    public int deleteSqMenuById(Long menuId);

    /**
     * 批量删除书签_菜单
     * 
     * @param menuIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSqMenuByIds(Long[] menuIds);
}
