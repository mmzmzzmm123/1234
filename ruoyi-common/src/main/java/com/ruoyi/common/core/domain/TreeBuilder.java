package com.ruoyi.common.core.domain;

import com.ruoyi.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author likun5
 */
public class TreeBuilder<T extends TreeEntity>
{

    /**
     * 构建前端所需要下拉树结构
     *
     * @param treeNodes 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildTreeSelect(List<T> treeNodes, Function<T, Long> getPkMethod, Function<T, TreeSelect> toTreeSelectMethod)
    {
        List<T> deptTrees = buildTree(treeNodes, getPkMethod);
        return deptTrees.stream().map(toTreeSelectMethod).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param treeNodes 部门列表
     * @return 树结构列表
     */
    public List<T> buildTree(List<T> treeNodes, Function<T, Long> getPkMethod)
    {
        List<T> returnList = new ArrayList<T>();
        List<Long> tempList = new ArrayList<Long>();
        for (T dept : treeNodes)
        {
            tempList.add(getPkMethod.apply(dept));
        }
        for (Iterator<T> iterator = treeNodes.iterator(); iterator.hasNext(); )
        {
            T dept = (T) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(treeNodes, dept, getPkMethod);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = treeNodes;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    public void recursionFn(List<T> list, T t, Function<T, Long> getPkMethod)
    {
        // 得到子节点列表
        List<T> childList = getChildList(list, t, getPkMethod);
        t.setChildren(childList);
        for (T tChild : childList)
        {
            if (hasChild(list, tChild, getPkMethod))
            {
                recursionFn(list, tChild, getPkMethod);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<T> getChildList(List<T> list, T t, Function<T, Long> getPkMethod)
    {
        List<T> tlist = new ArrayList<T>();
        Iterator<T> it = list.iterator();
        while (it.hasNext())
        {
            T n = (T) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == getPkMethod.apply(t).longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<T> list, T t, Function<T, Long> getPkMethod)
    {
        return getChildList(list, t, getPkMethod).size() > 0 ? true : false;
    }

}
