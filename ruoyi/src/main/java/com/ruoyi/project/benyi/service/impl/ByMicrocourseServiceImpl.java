package com.ruoyi.project.benyi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.benyi.domain.ByTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMicrocourseMapper;
import com.ruoyi.project.benyi.domain.ByMicrocourse;
import com.ruoyi.project.benyi.service.IByMicrocourseService;

/**
 * 微型课程Service业务层处理
 *
 * @author tsbz
 * @date 2021-05-13
 */
@Service
public class ByMicrocourseServiceImpl implements IByMicrocourseService {
    @Autowired
    private ByMicrocourseMapper byMicrocourseMapper;

    /**
     * 查询微型课程
     *
     * @param id 微型课程ID
     * @return 微型课程
     */
    @Override
    public ByMicrocourse selectByMicrocourseById(Long id) {
        return byMicrocourseMapper.selectByMicrocourseById(id);
    }

    /**
     * 查询微型课程列表
     *
     * @param byMicrocourse 微型课程
     * @return 微型课程
     */
    @Override
    public List<ByMicrocourse> selectByMicrocourseList(ByMicrocourse byMicrocourse) {
        return byMicrocourseMapper.selectByMicrocourseList(byMicrocourse);
    }

    /**
     * 查询微型课程列表
     *
     * @param byMicrocourse 微型课程
     * @return 微型课程集合
     */
    @Override
    public List<ByMicrocourse> selectByMicrocourseListTree(ByMicrocourse byMicrocourse){
        return byMicrocourseMapper.selectByMicrocourseListTree(byMicrocourse);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byMicrocourses 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByMicrocourse> buildMicrocourseDetailTree(List<ByMicrocourse> byMicrocourses) {
        //System.out.println("start---");
        List<ByMicrocourse> returnList = new ArrayList<ByMicrocourse>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByMicrocourse item : byMicrocourses) {
            tempList.add(item.getId());
        }
        for (Iterator<ByMicrocourse> iterator = byMicrocourses.iterator(); iterator.hasNext(); ) {
            ByMicrocourse item = (ByMicrocourse) iterator.next();
            //System.out.println("test==="+!tempList.contains(item.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(item.getParentId())) {
                recursionFn(byMicrocourses, item);
                returnList.add(item);
            }
        }
        if (returnList.isEmpty()) {
            returnList = byMicrocourses;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byMicrocourses 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMicrocourseTreeSelect(List<ByMicrocourse> byMicrocourses) {
        List<ByMicrocourse> byMicrocourseTrees = buildMicrocourseDetailTree(byMicrocourses);
        return byMicrocourseTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByMicrocourse> list, ByMicrocourse t) {
        // 得到子节点列表
        List<ByMicrocourse> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByMicrocourse tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<ByMicrocourse> it = childList.iterator();
                while (it.hasNext()) {
                    ByMicrocourse n = (ByMicrocourse) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByMicrocourse> getChildList(List<ByMicrocourse> list, ByMicrocourse t) {
        List<ByMicrocourse> tlist = new ArrayList<ByMicrocourse>();
        Iterator<ByMicrocourse> it = list.iterator();
        while (it.hasNext()) {
            ByMicrocourse n = (ByMicrocourse) it.next();
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
    private boolean hasChild(List<ByMicrocourse> list, ByMicrocourse t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    @Override
    public int insertByMicrocourse(ByMicrocourse byMicrocourse) {
        byMicrocourse.setCreateTime(DateUtils.getNowDate());
        return byMicrocourseMapper.insertByMicrocourse(byMicrocourse);
    }

    /**
     * 修改微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    @Override
    public int updateByMicrocourse(ByMicrocourse byMicrocourse) {
        return byMicrocourseMapper.updateByMicrocourse(byMicrocourse);
    }

    /**
     * 批量删除微型课程
     *
     * @param ids 需要删除的微型课程ID
     * @return 结果
     */
    @Override
    public int deleteByMicrocourseByIds(Long[] ids) {
        return byMicrocourseMapper.deleteByMicrocourseByIds(ids);
    }

    /**
     * 删除微型课程信息
     *
     * @param id 微型课程ID
     * @return 结果
     */
    @Override
    public int deleteByMicrocourseById(Long id) {
        return byMicrocourseMapper.deleteByMicrocourseById(id);
    }
}
