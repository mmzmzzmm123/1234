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
import com.ruoyi.project.benyi.mapper.ByAssessmentcontentMapper;
import com.ruoyi.project.benyi.domain.ByAssessmentcontent;
import com.ruoyi.project.benyi.service.IByAssessmentcontentService;

/**
 * 评估内容Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-09
 */
@Service
public class ByAssessmentcontentServiceImpl implements IByAssessmentcontentService {
    @Autowired
    private ByAssessmentcontentMapper byAssessmentcontentMapper;

    /**
     * 查询评估内容
     *
     * @param id 评估内容ID
     * @return 评估内容
     */
    @Override
    public ByAssessmentcontent selectByAssessmentcontentById(Long id) {
        return byAssessmentcontentMapper.selectByAssessmentcontentById(id);
    }

    /**
     * 查询评估内容列表
     *
     * @param byAssessmentcontent 评估内容
     * @return 评估内容
     */
    @Override
    public List<ByAssessmentcontent> selectByAssessmentcontentList(ByAssessmentcontent byAssessmentcontent) {
        return byAssessmentcontentMapper.selectByAssessmentcontentList(byAssessmentcontent);
    }

    /**
     * 查询评估内容列表
     *
     * @param byAssessmentcontent 评估内容
     * @return 评估内容集合
     */
    @Override
    public List<ByAssessmentcontent> selectByAssessmentcontentstudyList(ByAssessmentcontent byAssessmentcontent){
        return byAssessmentcontentMapper.selectByAssessmentcontentstudyList(byAssessmentcontent);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byAssessmentcontents 部门列表
     * @return 树结构列表
     */
    @Override
    public List<ByAssessmentcontent> buildbyAssessmentcontentTree(List<ByAssessmentcontent> byAssessmentcontents)
    {
        List<ByAssessmentcontent> returnList = new ArrayList<ByAssessmentcontent>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByAssessmentcontent byAssessmentcontent : byAssessmentcontents)
        {
            tempList.add(byAssessmentcontent.getId());
        }
        for (Iterator<ByAssessmentcontent> iterator = byAssessmentcontents.iterator(); iterator.hasNext();)
        {
            ByAssessmentcontent byAssessmentcontent = (ByAssessmentcontent) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(byAssessmentcontent.getParentId()))
            {
                recursionFn(byAssessmentcontents, byAssessmentcontent);
                returnList.add(byAssessmentcontent);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = byAssessmentcontents;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildByAssessmentcontentTreeSelect(List<ByAssessmentcontent> byAssessmentcontents) {
        List<ByAssessmentcontent> byAssessmentcontentTrees = buildbyAssessmentcontentTree(byAssessmentcontents);
        return byAssessmentcontentTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 新增评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    @Override
    public int insertByAssessmentcontent(ByAssessmentcontent byAssessmentcontent) {
        byAssessmentcontent.setCreateTime(DateUtils.getNowDate());
        return byAssessmentcontentMapper.insertByAssessmentcontent(byAssessmentcontent);
    }

    /**
     * 修改评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    @Override
    public int updateByAssessmentcontent(ByAssessmentcontent byAssessmentcontent) {
        return byAssessmentcontentMapper.updateByAssessmentcontent(byAssessmentcontent);
    }

    /**
     * 是否存在子节点
     *
     * @param id 内容ID
     * @return 结果
     */
    @Override
    public boolean hasChildByAssessmentcontentId(Long id) {
        int result = byAssessmentcontentMapper.hasChildByAssessmentcontentId(id);
        return result > 0 ? true : false;
    }

    /**
     * 批量删除评估内容
     *
     * @param ids 需要删除的评估内容ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentcontentByIds(Long[] ids) {
        return byAssessmentcontentMapper.deleteByAssessmentcontentByIds(ids);
    }

    /**
     * 删除评估内容信息
     *
     * @param id 评估内容ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentcontentById(Long id) {
        return byAssessmentcontentMapper.deleteByAssessmentcontentById(id);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByAssessmentcontent> list, ByAssessmentcontent t)
    {
        // 得到子节点列表
        List<ByAssessmentcontent> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByAssessmentcontent tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<ByAssessmentcontent> it = childList.iterator();
                while (it.hasNext())
                {
                    ByAssessmentcontent n = (ByAssessmentcontent) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByAssessmentcontent> getChildList(List<ByAssessmentcontent> list, ByAssessmentcontent t)
    {
        List<ByAssessmentcontent> tlist = new ArrayList<ByAssessmentcontent>();
        Iterator<ByAssessmentcontent> it = list.iterator();
        while (it.hasNext())
        {
            ByAssessmentcontent n = (ByAssessmentcontent) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ByAssessmentcontent> list, ByAssessmentcontent t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 节点元素个数
     *
     * @param byAssessmentcontent 内容ID
     * @return 结果
     */
    @Override
    public int selectCountElement(ByAssessmentcontent byAssessmentcontent){
        return byAssessmentcontentMapper.selectCountElement(byAssessmentcontent);
    }

    /**
     * 节点元素个数根据child
     *
     * @param byAssessmentcontent 内容ID
     * @return 结果
     */
    @Override
    public int selectCountElementByChild(ByAssessmentcontent byAssessmentcontent){
        return byAssessmentcontentMapper.selectCountElementByChild(byAssessmentcontent);
    }

}
