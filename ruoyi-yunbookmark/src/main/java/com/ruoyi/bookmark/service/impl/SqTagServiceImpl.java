package com.ruoyi.bookmark.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqTagMapper;
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.service.ISqTagService;

import javax.annotation.Resource;

/**
 * 书签_标签Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-04
 */
@Service
public class SqTagServiceImpl implements ISqTagService
{
    public static Logger logger =  LoggerFactory.getLogger(SqTagServiceImpl.class);
    @Autowired
    private SqTagMapper sqTagMapper;


    /**
     * 查询书签_标签
     *
     * @param id 书签_标签ID
     * @return 书签_标签
     */
    @Override
    public SqTag selectSqTagById(Long id)
    {
        return sqTagMapper.selectSqTagById(id);
    }

    /**
     * 查询书签_标签列表
     *
     * @param sqTag 书签_标签
     * @return 书签_标签
     */
    @Override
    public List<SqTag> selectSqTagList(SqTag sqTag)
    {
        return sqTagMapper.selectSqTagList(sqTag);
    }

    /**
     * 新增书签_标签
     *
     * @param sqTag 书签_标签
     * @return 结果
     */
    @Override
    public int insertSqTag(SqTag sqTag)
    {
        sqTag.setCreateTime(DateUtils.getNowDate());
        return sqTagMapper.insertSqTag(sqTag);
    }

    /**
     * 修改书签_标签
     *
     * @param sqTag 书签_标签
     * @return 结果
     */
    @Override
    public int updateSqTag(SqTag sqTag)
    {
        return sqTagMapper.updateSqTag(sqTag);
    }

    /**
     * 批量删除书签_标签
     *
     * @param ids 需要删除的书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqTagByIds(Long[] ids)
    {
        return sqTagMapper.deleteSqTagByIds(ids);
    }

    /**
     * 删除书签_标签信息
     *
     * @param id 书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqTagById(Long id)
    {
        return sqTagMapper.deleteSqTagById(id);
    }


    /**
     * @auther: Wang
     * @date: 2020/09/06 15:06
     * 功能描述:修改书签 标签 检测传入的标签是否 需要添加
     */

    @Override
    public Map<String,Object> addtag(String tagName,Long userId) {
        Map<String,Object> map=new HashMap<>();
        //创建新的标签 返回id给map
       //1.1查询书签是否存在
        SqTag sqTag=new SqTag();
        sqTag.setName(tagName);
        sqTag.setUserId(userId);
       List<SqTag> taglist=sqTagMapper.selectSqTagList(sqTag);
        //存在返回ID
        if (taglist!=null&&!taglist.isEmpty()){
            map.put("tagId",taglist.get(0).getId());
        }else {
           //不存在 >>创建 返回ID
           sqTagMapper.insertSqTag(sqTag);
//           logger.debug("传入的新标签 tagid="+sqTag.getId());
          map.put("tagId",sqTag.getId());
        }
        return map;
    }

    @Override
    public int countByuserId(Long userId) {
        SqTag sqTag = new SqTag();
        sqTag.setUserId(userId);
        return sqTagMapper.selectCount(sqTag);
    }

    @Override
    public int delteTagByid(String id, Long userId) {
        return sqTagMapper.delteTagByidAndUserId(Long.valueOf(id),userId);
    }


}
