package com.ruoyi.project.benyi.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.benyi.domain.ByMicrocourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAdmissioncourseMapper;
import com.ruoyi.project.benyi.domain.ByAdmissioncourse;
import com.ruoyi.project.benyi.service.IByAdmissioncourseService;

/**
 * 入学准备课程Service业务层处理
 *
 * @author tsbz
 * @date 2021-10-17
 */
@Service
public class ByAdmissioncourseServiceImpl implements IByAdmissioncourseService {
    @Autowired
    private ByAdmissioncourseMapper byAdmissioncourseMapper;

    /**
     * 查询入学准备课程
     *
     * @param id 入学准备课程ID
     * @return 入学准备课程
     */
    @Override
    public ByAdmissioncourse selectByAdmissioncourseById(Long id) {
        return byAdmissioncourseMapper.selectByAdmissioncourseById(id);
    }

    /**
     * 查询入学准备课程列表
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 入学准备课程
     */
    @Override
    public List<ByAdmissioncourse> selectByAdmissioncourseList(ByAdmissioncourse byAdmissioncourse) {
        return byAdmissioncourseMapper.selectByAdmissioncourseList(byAdmissioncourse);
    }

    /**
     * 查询入学准备课程列表
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 入学准备课程程集合
     */
    public List<ByAdmissioncourse> selectByAdmissioncourseListTree(ByAdmissioncourse byAdmissioncourse){
        return byAdmissioncourseMapper.selectByAdmissioncourseListTree(byAdmissioncourse);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byAdmissioncourses 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByAdmissioncourse> buildByAdmissioncourseDetailTree(List<ByAdmissioncourse> byAdmissioncourses) {
        //System.out.println("start---");
        List<ByAdmissioncourse> returnList = new ArrayList<ByAdmissioncourse>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByAdmissioncourse item : byAdmissioncourses) {
            tempList.add(item.getId());
        }
        for (Iterator<ByAdmissioncourse> iterator = byAdmissioncourses.iterator(); iterator.hasNext(); ) {
            ByAdmissioncourse item = (ByAdmissioncourse) iterator.next();
            //System.out.println("test==="+!tempList.contains(item.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(item.getParentId())) {
                recursionFn(byAdmissioncourses, item);
                returnList.add(item);
            }
        }
        if (returnList.isEmpty()) {
            returnList = byAdmissioncourses;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byAdmissioncourses 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildByAdmissioncourseTreeSelect(List<ByAdmissioncourse> byAdmissioncourses) {
        List<ByAdmissioncourse> byAdmissioncourseTrees = buildByAdmissioncourseDetailTree(byAdmissioncourses);
        return byAdmissioncourseTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByAdmissioncourse> list, ByAdmissioncourse t) {
        // 得到子节点列表
        List<ByAdmissioncourse> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByAdmissioncourse tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<ByAdmissioncourse> it = childList.iterator();
                while (it.hasNext()) {
                    ByAdmissioncourse n = (ByAdmissioncourse) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByAdmissioncourse> getChildList(List<ByAdmissioncourse> list, ByAdmissioncourse t) {
        List<ByAdmissioncourse> tlist = new ArrayList<ByAdmissioncourse>();
        Iterator<ByAdmissioncourse> it = list.iterator();
        while (it.hasNext()) {
            ByAdmissioncourse n = (ByAdmissioncourse) it.next();
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
    private boolean hasChild(List<ByAdmissioncourse> list, ByAdmissioncourse t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    /**
     * 新增入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    @Override
    public int insertByAdmissioncourse(ByAdmissioncourse byAdmissioncourse) {
        byAdmissioncourse.setCreateTime(DateUtils.getNowDate());
        return byAdmissioncourseMapper.insertByAdmissioncourse(byAdmissioncourse);
    }

    /**
     * 修改入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    @Override
    public int updateByAdmissioncourse(ByAdmissioncourse byAdmissioncourse) {
        return byAdmissioncourseMapper.updateByAdmissioncourse(byAdmissioncourse);
    }

    /**
     * 批量删除入学准备课程
     *
     * @param ids 需要删除的入学准备课程ID
     * @return 结果
     */
    @Override
    public int deleteByAdmissioncourseByIds(Long[] ids) {
        return byAdmissioncourseMapper.deleteByAdmissioncourseByIds(ids);
    }

    /**
     * 删除入学准备课程信息
     *
     * @param id 入学准备课程ID
     * @return 结果
     */
    @Override
    public int deleteByAdmissioncourseById(Long id) {
        return byAdmissioncourseMapper.deleteByAdmissioncourseById(id);
    }
}
