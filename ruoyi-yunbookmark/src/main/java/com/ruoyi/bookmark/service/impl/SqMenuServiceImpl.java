package com.ruoyi.bookmark.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.service.ISqMenuService;

/**
 * 书签菜单Service业务层处理
 *
 * @author wanghao
 * @date 2020-08-06
 */
@Service
public class SqMenuServiceImpl implements ISqMenuService
{
    @Autowired
    private SqMenuMapper sqMenuMapper;




    /**
     * 查询 目录菜单的 所有父级ID
     *
     * @param menuId
     * @return 结果
     */
    @Override
    public Long[] selectBymenuidParentid(Long menuId){

        List<Long> menuIds=new ArrayList<>();

        while (!menuId.toString().equals("0")) {
            SqMenu sqMenu = sqMenuMapper.selectSqMenuById(menuId);
            if (!sqMenu.getParentId().toString().equals("0")) {
                menuIds.add(sqMenu.getParentId());
            }
            menuId = sqMenu.getParentId();
        }
        Long[] menuIdArry = menuIds.toArray(new Long[menuIds.size()]);
        return  menuIdArry;
    }

    /**
     * @auther: Wang
     * @date: 2020/08/16 20:04
     * 功能描述:查询用户的 所有书签菜单
     */
    @Override
    public List<SqMenu> selecByUserID(Long id) {
        SqMenu sqMenu=new SqMenu();
        sqMenu.setUserId(id);

        return sqMenuMapper.selectSqMenuList(sqMenu);
    }

    /**
     * 查询书签菜单
     *
     * @param menuId 书签菜单ID
     * @return 书签菜单
     */
    @Override
    public SqMenu selectSqMenuById(Long menuId)
    {
        return sqMenuMapper.selectSqMenuById(menuId);
    }

    /**
     * 查询书签菜单列表
     *
     * @param sqMenu 书签菜单
     * @return 书签菜单
     */
    @Override
    public List<SqMenu> selectSqMenuList(SqMenu sqMenu)
    {
        return sqMenuMapper.selectSqMenuList(sqMenu);
    }

    /**
     * 新增书签菜单
     *
     * @param sqMenu 书签菜单
     * @return 结果
     */
    @Override
    public int insertSqMenu(SqMenu sqMenu)
    {
        sqMenu.setCreateTime(DateUtils.getNowDate());
        return sqMenuMapper.insertSqMenu(sqMenu);
    }

    /**
     * 修改书签菜单
     *
     * @param sqMenu 书签菜单
     * @return 结果
     */
    @Override
    public int updateSqMenu(SqMenu sqMenu)
    {
        return sqMenuMapper.updateSqMenu(sqMenu);
    }

    /**
     * 批量删除书签菜单
     *
     * @param menuIds 需要删除的书签菜单ID
     * @return 结果
     */
    @Override
    public int deleteSqMenuByIds(Long[] menuIds)
    {
        return sqMenuMapper.deleteSqMenuByIds(menuIds);
    }

    /**
     * 删除书签菜单信息
     *
     * @param menuId 书签菜单ID
     * @return 结果
     */
    @Override
    public int deleteSqMenuById(Long menuId,Long userId)
    {
        return sqMenuMapper.deleteSqMenuById(menuId,userId);
    }


    /**
     * 批量减少目录下书签数量
     *
     * @param menuIds
     * @return 结果
     */
    @Override
    public int updateCountReduce(Long[] menuIds,int icount){
        return sqMenuMapper.updateCountReduce(menuIds,icount);
    }

    /**
     * 批量添加目录下书签数量
     *
     * @param menuIds
     * @return 结果
     */
    @Override
    public int updateCountAdd(Long[] menuIds,int icount){
        return sqMenuMapper.updateCountAdd(menuIds,icount);
    }
}
