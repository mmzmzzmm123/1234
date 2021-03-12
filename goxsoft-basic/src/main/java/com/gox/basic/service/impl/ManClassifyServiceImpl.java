package com.gox.basic.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gox.common.core.domain.entity.SysMenu;
import com.gox.common.plugin.SnowIdUtils;
import com.gox.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.ManClassifyMapper;
import com.gox.basic.domain.ManClassify;
import com.gox.basic.service.IManClassifyService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分类管理Service业务层处理
 *
 * @author gox
 * @date 2021-03-09
 */
@Service
public class ManClassifyServiceImpl implements IManClassifyService {
    @Autowired
    private ManClassifyMapper manClassifyMapper;
    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 查询分类管理
     *
     * @param id 分类管理ID
     * @return 分类管理
     */
    @Override
    public ManClassify selectManClassifyById(Long id) {
        return manClassifyMapper.selectManClassifyById(id);
    }

    /**
     * 查询分类管理列表
     *
     * @param manClassify 分类管理
     * @return 分类管理
     */
    @Override
    public List<ManClassify> selectManClassifyList(ManClassify manClassify) {
        return manClassifyMapper.selectManClassifyList(manClassify);
    }

    /**
     * 新增分类管理
     *
     * @param manClassify 分类管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertManClassify(ManClassify manClassify) {
        manClassify.setId(SnowIdUtils.uniqueLong());
        manClassifyMapper.insertManClassify(manClassify);
        List<SysMenu> list = transferMenus(manClassify);
        sysMenuService.insertMenuBatch(list);
        return 1;
    }

    /**
     * 修改分类管理
     *
     * @param manClassify 分类管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int updateManClassify(ManClassify manClassify) {
        manClassifyMapper.updateManClassify(manClassify);
        SysMenu m = new SysMenu();
        m.setClassifyId(manClassify.getId());
        List<SysMenu> menus = sysMenuService.selectMenuList(m);
        List<SysMenu> ms = transferMenus(manClassify);
        Long id = menus.stream().filter(menu ->menu.getMenuName().contains("数据采集")).findFirst().orElse(new SysMenu()).getMenuId();
        ms.get(0).setMenuId(id);
        id = menus.stream().filter(menu -> menu.getMenuName().contains("数据管理")).findFirst().orElse(new SysMenu()).getMenuId();
        ms.get(1).setMenuId(id);
        ms.get(2).setMenuId(menus.stream().filter(menu -> menu.getMenuName().contains("鉴定管理")).findFirst().orElse(new SysMenu()).getMenuId());
        ms.get(3).setMenuId(menus.stream().filter(menu -> menu.getMenuName().contains("模板管理")).findFirst().orElse(new SysMenu()).getMenuId());
        sysMenuService.updateMenuBatch(ms);
        return 1;
    }

    /**
     * 批量删除分类管理
     *
     * @param ids 需要删除的分类管理ID
     * @return 结果
     */
    @Override

    public int deleteManClassifyByIds(Long[] ids) {
        return manClassifyMapper.deleteManClassifyByIds(ids);
    }

    /**
     * 删除分类管理信息
     *
     * @param id 分类管理ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int deleteManClassifyById(Long id) {
        manClassifyMapper.deleteManClassifyById(id);
        sysMenuService.deleteMenuByClassifyId(id);
        return 1;
    }

    /**
     * 1.采集库 audit/index 案卷 audit/archives
     * 2.管理库 man/index man/archives
     * 3.鉴定管理 identify/index identify/archives
     * 4.模板设置 templates/index
     *
     * @param classify
     * @return
     */
    @Override
    public List<SysMenu> transferMenus(ManClassify classify) {
        List<SysMenu> list = sysMenuService.selectMenuList(1L);
        SysMenu menu = new SysMenu();
        menu.setMenuType(classify.getType());
        menu.setOrderNum(String.valueOf(classify.getSort()));
        menu.setMenuName(classify.getName());
        menu.setClassifyId(classify.getId());
        menu.setIcon(classify.getIcon());
        menu.setPerms(classify.getPerms());
        menu.setIsFrame("1");
        menu.setIsCache("0");
        menu.setVisible("0");
        menu.setStatus("0");
        SysMenu m1 = ObjectUtil.cloneByStream(menu);
        m1.setDataFlag("数据采集");
        SysMenu mtop1 = list.stream().filter(m -> m.getMenuName().equals("数据采集")).findFirst().orElse(new SysMenu());
        m1.setParentId(getParentId(classify.getParentName(), list, mtop1));
        SysMenu m2 = ObjectUtil.cloneByStream(menu);
        m2.setDataFlag("数据管理");
        SysMenu mtop2 = list.stream().filter(m -> m.getMenuName().equals("数据管理")).findFirst().orElse(new SysMenu());
        m2.setParentId(getParentId(classify.getParentName(), list, mtop2));
        SysMenu m3 = ObjectUtil.cloneByStream(menu);
        m3.setDataFlag("鉴定管理");
        SysMenu mtop3 = list.stream().filter(m -> m.getMenuName().equals("鉴定管理")).findFirst().orElse(new SysMenu());
        m3.setParentId(getParentId(classify.getParentName(), list, mtop3));
        SysMenu m4 = ObjectUtil.cloneByStream(menu);
        m4.setDataFlag("模板管理");
        m1.setMenuName(classify.getName());
        m2.setMenuName(classify.getName());
        m3.setMenuName(classify.getName());
        m4.setMenuName(classify.getName());
        m1.setPath(classify.getPath());
        m2.setPath(classify.getPath());
        m3.setPath(classify.getPath());
        m4.setPath(classify.getPath());
        SysMenu mtop4 = list.stream().filter(m -> m.getMenuName().equals("模板管理")).findFirst().orElse(new SysMenu());
        m4.setParentId(getParentId(classify.getParentName(), list, mtop4));
        if ("C".equals(classify.getType())) {
            m1.setPath(classify.getPath()+"/"+classify.getId());
            m2.setPath(classify.getPath()+"/"+classify.getId());
            m3.setPath(classify.getPath()+"/"+classify.getId());
            //数据采集
            if (classify.getArchivalType()) {
                //文件
                m1.setComponent("system/audit/index");
                m2.setComponent("system/man/index");
                m3.setComponent("system/identify/index");
            } else {
                m1.setComponent("system/audit/archives");
                m2.setComponent("system/man/archives");
                m3.setComponent("system/identify/archives");
            }
            m4.setPath("" + classify.getId());
            m4.setComponent("system/json/index");
        }
        return new ArrayList<>(Arrays.asList(m1, m2, m3, m4));
    }

    /**
     * 获取对应父id
     * @param menus 菜单列表
     * @param type  数据采集 数据管理
     * @return 对应parentId
     * 第一层
     */
    public Long getParentId(String pn, List<SysMenu> menus, SysMenu type) {
        if (pn.equals("——")){
            return type.getMenuId();
        }
        List<SysMenu> list = menus.stream().filter(m -> m.getMenuName().equals(pn) && m.getDataFlag().equals(type.getMenuName())).collect(Collectors.toList());
        return list.get(0).getMenuId();
    }
}
