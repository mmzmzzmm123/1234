package com.ruoyi.project.benyi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeMapper;
import com.ruoyi.project.benyi.domain.ByTheme;
import com.ruoyi.project.benyi.service.IByThemeService;

/**
 * 主题整合Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-01
 */
@Service
public class ByThemeServiceImpl implements IByThemeService {
    @Autowired
    private ByThemeMapper byThemeMapper;

    /**
     * 查询主题整合
     *
     * @param id 主题整合ID
     * @return 主题整合
     */
    @Override
    public ByTheme selectByThemeById(Long id) {
        return byThemeMapper.selectByThemeById(id);
    }

    /**
     * 查询主题整合列表
     *
     * @param byTheme 主题整合
     * @return 主题整合
     */
    @Override
    public List<ByTheme> selectByThemeList(ByTheme byTheme) {
        return byThemeMapper.selectByThemeList(byTheme);
    }

    /**
     * 查询一日流程列表树
     *
     * @param byTheme 一日流程
     * @return 一日流程树集合
     */
    @Override
    public List<ByTheme> selectByThemeListTree(ByTheme byTheme) {
        return byThemeMapper.selectByThemeListTree(byTheme);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byThemes 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByTheme> buildThemeDetailTree(List<ByTheme> byThemes) {
        //System.out.println("start---");
        List<ByTheme> returnList = new ArrayList<ByTheme>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByTheme item : byThemes) {
            tempList.add(item.getId());
        }
        for (Iterator<ByTheme> iterator = byThemes.iterator(); iterator.hasNext(); ) {
            ByTheme item = (ByTheme) iterator.next();
            //System.out.println("test==="+!tempList.contains(item.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(item.getParentId())) {
                recursionFn(byThemes, item);
                returnList.add(item);
            }
        }
        if (returnList.isEmpty()) {
            returnList = byThemes;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byThemes 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildThemeTreeSelect(List<ByTheme> byThemes) {
        List<ByTheme> byThemeTrees = buildThemeDetailTree(byThemes);
        return byThemeTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByTheme> list, ByTheme t) {
        // 得到子节点列表
        List<ByTheme> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByTheme tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<ByTheme> it = childList.iterator();
                while (it.hasNext()) {
                    ByTheme n = (ByTheme) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByTheme> getChildList(List<ByTheme> list, ByTheme t) {
        List<ByTheme> tlist = new ArrayList<ByTheme>();
        Iterator<ByTheme> it = list.iterator();
        while (it.hasNext()) {
            ByTheme n = (ByTheme) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                //System.out.println("parentid="+n.getParentId().longValue()+"---"+t.getId().longValue());
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ByTheme> list, ByTheme t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    @Override
    public int insertByTheme(ByTheme byTheme) {
        byTheme.setCreateTime(DateUtils.getNowDate());
        return byThemeMapper.insertByTheme(byTheme);
    }

    /**
     * 修改主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    @Override
    public int updateByTheme(ByTheme byTheme) {
        return byThemeMapper.updateByTheme(byTheme);
    }

    /**
     * 批量删除主题整合
     *
     * @param ids 需要删除的主题整合ID
     * @return 结果
     */
    @Override
    public int deleteByThemeByIds(Long[] ids) {
        return byThemeMapper.deleteByThemeByIds(ids);
    }

    /**
     * 删除主题整合信息
     *
     * @param id 主题整合ID
     * @return 结果
     */
    @Override
    public int deleteByThemeById(Long id) {
        return byThemeMapper.deleteByThemeById(id);
    }
}
