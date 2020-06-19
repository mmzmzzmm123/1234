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
import com.ruoyi.project.benyi.mapper.ByDayFlowDetailMapper;
import com.ruoyi.project.benyi.domain.ByDayFlowDetail;
import com.ruoyi.project.benyi.service.IByDayFlowDetailService;

/**
 * 一日流程Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-07
 */
@Service
public class ByDayFlowDetailServiceImpl implements IByDayFlowDetailService 
{
    @Autowired
    private ByDayFlowDetailMapper byDayFlowDetailMapper;

    /**
     * 查询一日流程
     * 
     * @param id 一日流程ID
     * @return 一日流程
     */
    @Override
    public ByDayFlowDetail selectByDayFlowDetailById(Long id)
    {
        return byDayFlowDetailMapper.selectByDayFlowDetailById(id);
    }

    /**
     * 查询一日流程列表
     * 
     * @param byDayFlowDetail 一日流程
     * @return 一日流程
     */
    @Override
    public List<ByDayFlowDetail> selectByDayFlowDetailList(ByDayFlowDetail byDayFlowDetail)
    {
        return byDayFlowDetailMapper.selectByDayFlowDetailList(byDayFlowDetail);
    }

    /**
     * 查询一日流程列表树
     *
     * @param byDayFlowDetail 一日流程
     * @return 一日流程树集合
     */
    @Override
    public List<ByDayFlowDetail> selectByDayFlowDetailListTree(ByDayFlowDetail byDayFlowDetail)
    {
        return byDayFlowDetailMapper.selectByDayFlowDetailListTree(byDayFlowDetail);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byDayFlowDetails 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByDayFlowDetail> buildDayFlowDetailTree(List<ByDayFlowDetail> byDayFlowDetails) {
        System.out.println("start---");
        List<ByDayFlowDetail> returnList = new ArrayList<ByDayFlowDetail>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByDayFlowDetail byDayFlowDetail : byDayFlowDetails)
        {
            tempList.add(byDayFlowDetail.getId());
        }
        for (Iterator<ByDayFlowDetail> iterator = byDayFlowDetails.iterator(); iterator.hasNext();)
        {
            ByDayFlowDetail byDayFlowDetail = (ByDayFlowDetail) iterator.next();
            System.out.println("test==="+!tempList.contains(byDayFlowDetail.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(byDayFlowDetail.getParentId()))
            {
                recursionFn(byDayFlowDetails, byDayFlowDetail);
                returnList.add(byDayFlowDetail);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = byDayFlowDetails;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byDayFlowDetails 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDayFlowDetailTreeSelect(List<ByDayFlowDetail> byDayFlowDetails)
    {
        List<ByDayFlowDetail> dayFlowDetailTrees = buildDayFlowDetailTree(byDayFlowDetails);
        return dayFlowDetailTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByDayFlowDetail> list, ByDayFlowDetail t)
    {
        // 得到子节点列表
        List<ByDayFlowDetail> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByDayFlowDetail tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<ByDayFlowDetail> it = childList.iterator();
                while (it.hasNext())
                {
                    ByDayFlowDetail n = (ByDayFlowDetail) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByDayFlowDetail> getChildList(List<ByDayFlowDetail> list, ByDayFlowDetail t)
    {
        List<ByDayFlowDetail> tlist = new ArrayList<ByDayFlowDetail>();
        Iterator<ByDayFlowDetail> it = list.iterator();
        while (it.hasNext())
        {
            ByDayFlowDetail n = (ByDayFlowDetail) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue())
            {
                //System.out.println("parentid="+n.getParentId().longValue()+"---"+t.getId().longValue());
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ByDayFlowDetail> list, ByDayFlowDetail t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    @Override
    public int insertByDayFlowDetail(ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setCreateTime(DateUtils.getNowDate());
        return byDayFlowDetailMapper.insertByDayFlowDetail(byDayFlowDetail);
    }

    /**
     * 修改一日流程
     * 
     * @param byDayFlowDetail 一日流程
     * @return 结果
     */
    @Override
    public int updateByDayFlowDetail(ByDayFlowDetail byDayFlowDetail)
    {
        byDayFlowDetail.setUpdateTime(DateUtils.getNowDate());
        return byDayFlowDetailMapper.updateByDayFlowDetail(byDayFlowDetail);
    }

    /**
     * 批量删除一日流程
     * 
     * @param ids 需要删除的一日流程ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowDetailByIds(Long[] ids)
    {
        return byDayFlowDetailMapper.deleteByDayFlowDetailByIds(ids);
    }

    /**
     * 删除一日流程信息
     * 
     * @param id 一日流程ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowDetailById(Long id)
    {
        return byDayFlowDetailMapper.deleteByDayFlowDetailById(id);
    }
}
