package com.ruoyi.bookmark.mapper;

import java.util.List;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.common.mybatisMapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 书签菜单Mapper接口
 *
 * @author wanghao
 * @date 2020-08-06
 */
public interface SqMenuMapper extends MyMapper<SqMenu>
{

    /**
     * 查询用户的所有目录菜单
     *
     * @param userid 用户userid
     * @return 结果
     */
    public  List<SqMenu> selecByUserID(Long userid);

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
     * 删除书签菜单信息
     *
     * @param menuId 书签目录菜单emnuID
     * @param userId 用户userID
     * @return 结果
     */
    public int deleteSqMenuById(@Param("menuId")Long menuId,@Param("userId")Long userId);

    /**
     * 批量删除书签菜单
     *
     * @param menuIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSqMenuByIds(Long[] menuIds);

    /**
     * 批量减少目录下书签数量
     *
     * @param menuIds 目录menuId串
     * @param icount 减少数量
     * @return 结果
     */
    public int updateCountReduce(@Param("menuIds")Long[] menuIds,@Param("icount")int icount);

    /**
     * 批量添加目录下书签数量
     *
     * @param menuIds 目录menuId串
     * @param icount 添加数量
     * @return 结果
     */
    public int updateCountAdd(@Param("menuIds")Long[] menuIds,@Param("icount")int icount);


    /**
     *添加书签目录
     *
     * @param  userID
     * @param  title
     * @param  parentId
     * @return
     */
    public void addMenu(@Param("userId")Long userID, @Param("title")String title, @Param("parentId")Long parentId);

    /**
     * 查询空目录串的目录
     *
     * @param
     * @return
     */
    public List<SqMenu> selectbymenuUplinkSeries();

    /**
     * 根据目录串查询下级的所有menuid
     *
     * @param menuUplinkSeries 目录串
     * @param menuId 为null表示不包含menuid
     * @return
     */
    public List<SqMenu> listByMenuUplinkSeriesAndMenu(@Param("menuUplinkSeries")String menuUplinkSeries, @Param("menuId")Long menuId);

    /**
     * 根据目录串查询下级的所有书签数量
     *
     * @param menuUplinkSeries 目录串
     * @param menuId 为null表示不包含menuid
     * @return
     */
    public int countByMenuUplinkSeriesAndMenu(@Param("menuUplinkSeries")String menuUplinkSeries, @Param("menuId")Long menuId);



}
