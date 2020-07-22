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
import com.ruoyi.project.benyi.mapper.ByMathMapper;
import com.ruoyi.project.benyi.domain.ByMath;
import com.ruoyi.project.benyi.service.IByMathService;

/**
 * 游戏数学Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-20
 */
@Service
public class ByMathServiceImpl implements IByMathService {
    @Autowired
    private ByMathMapper byMathMapper;

    /**
     * 查询游戏数学
     *
     * @param id 游戏数学ID
     * @return 游戏数学
     */
    @Override
    public ByMath selectByMathById(Long id) {
        return byMathMapper.selectByMathById(id);
    }

    /**
     * 查询游戏数学列表
     *
     * @param byMath 游戏数学
     * @return 游戏数学
     */
    @Override
    public List<ByMath> selectByMathList(ByMath byMath) {
        return byMathMapper.selectByMathList(byMath);
    }


    /**
     * 查询游戏数学树列表
     *
     * @param byMath 游戏数学树
     * @return 游戏数学树
     */
    @Override
    public List<ByMath> selectByMathListTree(ByMath byMath) {
        return byMathMapper.selectByMathListTree(byMath);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byMaths 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByMath> buildMathDetailTree(List<ByMath> byMaths) {
        //System.out.println("start---");
        List<ByMath> returnList = new ArrayList<ByMath>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByMath item : byMaths) {
            tempList.add(item.getId());
        }
        for (Iterator<ByMath> iterator = byMaths.iterator(); iterator.hasNext(); ) {
            ByMath item = (ByMath) iterator.next();
            //System.out.println("test==="+!tempList.contains(byDayFlowDetail.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(item.getParentId())) {
                recursionFn(byMaths, item);
                returnList.add(item);
            }
        }
        if (returnList.isEmpty()) {
            returnList = byMaths;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byMaths 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMathTreeSelect(List<ByMath> byMaths) {
        List<ByMath> byMathTrees = buildMathDetailTree(byMaths);
        return byMathTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByMath> list, ByMath t) {
        // 得到子节点列表
        List<ByMath> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByMath tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<ByMath> it = childList.iterator();
                while (it.hasNext()) {
                    ByMath n = (ByMath) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByMath> getChildList(List<ByMath> list, ByMath t) {
        List<ByMath> tlist = new ArrayList<ByMath>();
        Iterator<ByMath> it = list.iterator();
        while (it.hasNext()) {
            ByMath n = (ByMath) it.next();
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
    private boolean hasChild(List<ByMath> list, ByMath t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    @Override
    public int insertByMath(ByMath byMath) {
        byMath.setCreateTime(DateUtils.getNowDate());
        return byMathMapper.insertByMath(byMath);
    }

    /**
     * 修改游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    @Override
    public int updateByMath(ByMath byMath) {
        return byMathMapper.updateByMath(byMath);
    }

    /**
     * 批量删除游戏数学
     *
     * @param ids 需要删除的游戏数学ID
     * @return 结果
     */
    @Override
    public int deleteByMathByIds(Long[] ids) {
        return byMathMapper.deleteByMathByIds(ids);
    }

    /**
     * 删除游戏数学信息
     *
     * @param id 游戏数学ID
     * @return 结果
     */
    @Override
    public int deleteByMathById(Long id) {
        return byMathMapper.deleteByMathById(id);
    }
}
