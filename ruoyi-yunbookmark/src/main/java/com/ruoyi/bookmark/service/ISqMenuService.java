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
     * 查询 目录菜单的 所有下级MenuId
     *
     * @param menuId 目录ID
     * @return 结果
     */
    public Long[] selectBymenuidsubordinateid(Long menuId);

 /**
     * 查询 目录菜单的 所有父级ID
     *
     * @param menuId 目录ID
     * @return 结果
     */
    public Long[] selectBymenuidParentid(Long menuId);

    /**
     * 批量减少目录下书签数量
     *
     * @param menuIds 目录menuId串
     * @param icount 减少数量
     * @return 结果
     */
    public int updateCountReduce(Long[] menuIds,int icount);

    /**
     * 批量添加目录下书签数量
     *
     * @param menuIds 目录menuId串
     * @param icount 添加数量
     * @return 结果
     */
    public int updateCountAdd(Long[] menuIds,int icount);

    /**
     * 查询用户的所有目录菜单
     *
     * @param userid 用户userid
     * @return 结果
     */
    public List<SqMenu> selecByUserID(Long userid);


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
     * @param menuId 书签目录菜单emnuID
     * @param userId 用户userID
     * @return 结果
     */
    public int deleteSqMenuById(Long menuId,Long userId);
}
