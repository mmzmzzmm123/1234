package com.ruoyi.bookmark.service;

import java.util.List;
import com.ruoyi.bookmark.domain.SqMenu;
import org.omg.CORBA.INTERNAL;
import org.springframework.transaction.annotation.Transactional;

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
     * 新增书签菜单 测试事务
     *
     * @param sqMenu 书签菜单
     * @return 结果
     */
    @Transactional
    public int insertSqMenu2(SqMenu sqMenu)throws Exception;

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


   /**
    * 添加书签目录
    *
    * @param
    * @return
    */
   public void addMenu(Long userID, String title, Long valueOf);


   /**
    *防止重复点击上传文件
    *
    * @param  state 表示某个功能
    * @param  userID 用户ID
    * @param  time key过期时间(秒)
    * @return
    */
   public Long noRepetition(String state, Long userID, Long time);


   /**
    * 根据menuId 生成向上目录串
    *
    * @param
    * @return
    */
   public String addMenuUplinkSeries(Long menuId);

    /**
     * 根据目录串查询下级的所有menuid
     *
     * @param
     * @return
     */
    public List<SqMenu> listByMenuUplinkSeriesAndMenu(String menuUplinkSeries,Long menuId);
    /**
     * 根据目录串查询下级的所有书签数量
     *
     * @param
     * @return
     */
    public int countByMenuUplinkSeriesAndMenu(String menuUplinkSeries, Long menuId);

    /**
     * 批量添加上级 目录的书签数量
     *
     * @param  menuId
     * @return
     */
    void addMenuByCountAndMenuUplinkSeries(Long menuId);

    /**
     * 批量减少上级 目录的书签数量
     *
     * @param  sqMenu
     * @return
     */
    void reduceMenuByCountAndMenuUplinkSeries(SqMenu sqMenu);

    /**
     * 查下级目录
     *
     * @param
     * @return
     */
    List<SqMenu> listByMenuId(Long userId, Long menuId);
}
