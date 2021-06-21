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
import com.ruoyi.project.benyi.mapper.ByFamilyeduMapper;
import com.ruoyi.project.benyi.domain.ByFamilyedu;
import com.ruoyi.project.benyi.service.IByFamilyeduService;

/**
 * 家庭教育Service业务层处理
 *
 * @author tsbz
 * @date 2021-06-20
 */
@Service
public class ByFamilyeduServiceImpl implements IByFamilyeduService {
    @Autowired
    private ByFamilyeduMapper byFamilyeduMapper;

    /**
     * 查询家庭教育
     *
     * @param id 家庭教育ID
     * @return 家庭教育
     */
    @Override
    public ByFamilyedu selectByFamilyeduById(Long id) {
        return byFamilyeduMapper.selectByFamilyeduById(id);
    }

    /**
     * 查询家庭教育列表
     *
     * @param byFamilyedu 家庭教育
     * @return 家庭教育
     */
    @Override
    public List<ByFamilyedu> selectByFamilyeduList(ByFamilyedu byFamilyedu) {
        return byFamilyeduMapper.selectByFamilyeduList(byFamilyedu);
    }

    /**
     * 查询家庭教育列表
     *
     * @param byFamilyedu 家庭教育
     * @return 家庭教育集合
     */
    @Override
    public List<ByFamilyedu> selectByFamilyeduListTree(ByFamilyedu byFamilyedu){
        return byFamilyeduMapper.selectByFamilyeduList(byFamilyedu);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param byFamilyedus 家庭教育列表
     * @return 树结构列表
     */
    @Override
    public List<ByFamilyedu> buildFamilyeduDetailTree(List<ByFamilyedu> byFamilyedus) {
        //System.out.println("start---");
        List<ByFamilyedu> returnList = new ArrayList<ByFamilyedu>();
        List<Long> tempList = new ArrayList<Long>();
        for (ByFamilyedu item : byFamilyedus) {
            tempList.add(item.getId());
        }
        for (Iterator<ByFamilyedu> iterator = byFamilyedus.iterator(); iterator.hasNext(); ) {
            ByFamilyedu item = (ByFamilyedu) iterator.next();
            //System.out.println("test==="+!tempList.contains(item.getParentId()));
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(item.getParentid())) {
                recursionFn(byFamilyedus, item);
                returnList.add(item);
            }
        }
        if (returnList.isEmpty()) {
            returnList = byFamilyedus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byFamilyedus 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildFamilyeduTreeSelect(List<ByFamilyedu> byFamilyedus) {
        List<ByFamilyedu> byFamilyeduTrees = buildFamilyeduDetailTree(byFamilyedus);
        return byFamilyeduTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ByFamilyedu> list, ByFamilyedu t) {
        // 得到子节点列表
        List<ByFamilyedu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ByFamilyedu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<ByFamilyedu> it = childList.iterator();
                while (it.hasNext()) {
                    ByFamilyedu n = (ByFamilyedu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ByFamilyedu> getChildList(List<ByFamilyedu> list, ByFamilyedu t) {
        List<ByFamilyedu> tlist = new ArrayList<ByFamilyedu>();
        Iterator<ByFamilyedu> it = list.iterator();
        while (it.hasNext()) {
            ByFamilyedu n = (ByFamilyedu) it.next();
            if (StringUtils.isNotNull(n.getParentid()) && n.getParentid().longValue() == t.getId().longValue()) {
                //System.out.println("parentid="+n.getParentId().longValue()+"---"+t.getId().longValue());
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ByFamilyedu> list, ByFamilyedu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    /**
     * 新增家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    @Override
    public int insertByFamilyedu(ByFamilyedu byFamilyedu) {
        byFamilyedu.setCreateTime(DateUtils.getNowDate());
        return byFamilyeduMapper.insertByFamilyedu(byFamilyedu);
    }

    /**
     * 修改家庭教育
     *
     * @param byFamilyedu 家庭教育
     * @return 结果
     */
    @Override
    public int updateByFamilyedu(ByFamilyedu byFamilyedu) {
        return byFamilyeduMapper.updateByFamilyedu(byFamilyedu);
    }

    /**
     * 批量删除家庭教育
     *
     * @param ids 需要删除的家庭教育ID
     * @return 结果
     */
    @Override
    public int deleteByFamilyeduByIds(Long[] ids) {
        return byFamilyeduMapper.deleteByFamilyeduByIds(ids);
    }

    /**
     * 删除家庭教育信息
     *
     * @param id 家庭教育ID
     * @return 结果
     */
    @Override
    public int deleteByFamilyeduById(Long id) {
        return byFamilyeduMapper.deleteByFamilyeduById(id);
    }
}
