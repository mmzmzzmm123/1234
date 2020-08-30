package com.ruoyi.bookmark.service;

import java.util.List;
import com.ruoyi.bookmark.domain.SqMenu;

/**
 * 书签菜单Service接口
 *
 * @author wanghao
 * @date 2020-08-06
 */
public interface ISqMenuService
{

    /**
     * 批量减少目录下书签数量  -1
     *
     * @param menuIds
     * @return 结果
     */
    public int updateCountReduce(Long[] menuIds);

    /**
     * 批量添加目录下书签数量  +1
     *
     * @param menuIds   +1
     * @return 结果
     */
    public int updateCountAdd(Long[] menuIds);
    /**
     * @auther: Wang
     * @date: 2020/08/16 20:04
     * 功能描述:查询用户的 所有书签菜单
     */
    public List<SqMenu> selecByUserID(Long id);


    /**
     * 查询书签菜单
     *
     * @param menuId 书签菜单ID
     * @return 书签菜单
     */
    public SqMenu selectSqMenuById(Long menuId);

    /**
     * 查询书签菜单列表
     *
     * @param sqMenu 书签菜单
     * @return 书签菜单集合
     */
    public List<SqMenu> selectSqMenuList(SqMenu sqMenu);

    /**
     * 新增书签菜单
     *
     * @param sqMenu 书签菜单
     * @return 结果
     */
    public int insertSqMenu(SqMenu sqMenu);

    /**
     * 修改书签菜单
     *
     * @param sqMenu 书签菜单
     * @return 结果
     */
    public int updateSqMenu(SqMenu sqMenu);

    /**
     * 批量删除书签菜单
     *
     * @param menuIds 需要删除的书签菜单ID
     * @return 结果
     */
    public int deleteSqMenuByIds(Long[] menuIds);

    /**
     * 删除书签菜单信息
     *
     * @param menuId 书签菜单ID
     * @return 结果
     */
    public int deleteSqMenuById(Long menuId,Long userId);
}
