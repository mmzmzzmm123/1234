package com.ruoyi.bookmark.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import com.github.wujun234.uid.UidGenerator;
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.mapper.SqTagMapper;
import com.ruoyi.common.utils.YunConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqUserTagMapper;
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.bookmark.service.ISqUserTagService;

import javax.annotation.Resource;

/**
 * 标签管理Service业务层处理
 *
 * @author wang
 * @date 2020-09-04
 */
@Service
public class SqUserTagServiceImpl implements ISqUserTagService
{
     public final static Logger logger =  LoggerFactory.getLogger(SqUserTagServiceImpl.class);

    @Autowired
    private SqUserTagMapper sqUserTagMapper;

    @Autowired
    private SqTagMapper sqTagMapper;






    /**
     * 批量减少用戶 标签引用的 数量 -1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int updateCountReduce(Long[] tags,Long userId){
        return sqUserTagMapper.updateCountReduce(tags,userId);
    }

    /**
     * 批量添加 用戶 标签引用的 数量 +1
     *
     * @param tags 标签串
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int updateCountAdd(Long[] tags,Long userId){
        return sqUserTagMapper.updateCountAdd(tags,userId);
    }






    /**
     *通过用户userID查用户的所有标签
     *
     *
     */
    @Override
    public List<Map<String,Object>>selectSqUserTagByUserId(Long userId){
        return sqUserTagMapper.selectSqUserTagByUserId(userId);
    }



    /**
     * 查询标签管理
     *
     * @param id 标签管理ID
     * @return 标签管理
     */
    @Override
    public SqUserTag selectSqUserTagById(Long id)
    {
        return sqUserTagMapper.selectSqUserTagById(id);
    }

    /**
     * 查询标签管理列表
     *
     * @param sqUserTag 标签管理
     * @return 标签管理
     */
    @Override
    public List<SqUserTag> selectSqUserTagList(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.selectSqUserTagList(sqUserTag);
    }

    /**
     * 新增标签管理
     *
     * @param sqUserTag 标签管理
     * @return 结果
     */
    @Override
    public int insertSqUserTag(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.insertSqUserTag(sqUserTag);
    }

    /**
     * 修改标签管理
     *
     * @param sqUserTag 标签管理
     * @return 结果
     */
    @Override
    public int updateSqUserTag(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.updateSqUserTag(sqUserTag);
    }

    /**
     * 批量删除标签管理
     *
     * @param ids 需要删除的标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqUserTagByIds(Long[] ids)
    {
        return sqUserTagMapper.deleteSqUserTagByIds(ids);
    }

    /**
     * 删除标签管理信息
     *
     * @param id 标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqUserTagById(Long id)
    {
        return sqUserTagMapper.deleteSqUserTagById(id);
    }

    /**
     * 删除标签管理信息
     *
     * @param ids 书签标签ID串
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int userRemoveByid(Long[] ids, Long userId) {
        return sqUserTagMapper.userRemoveByid(ids,userId);
    }


    /**
     * @Description 用户添加标签
     * @Author  wanghao
     * @Date   2020/09/16 20:00
     * @Param  [sqUserTag]
     * @Return      int
     * @Exception
     *
     */
    @Override
    public int insertSqUserTagAdd(SqUserTag sqUserTag) {

      List<SqTag> sqtag =  sqTagMapper.selectCountByName(sqUserTag.getTagName());
      if (sqtag!=null&&!sqtag.isEmpty()){
          sqUserTag.setTagId(sqtag.get(0).getId());
          sqUserTag.setTagName(sqtag.get(0).getName());
          sqUserTag.setIcount(1);
      }else {
          SqTag sqTag=new SqTag();
          sqTag.setName(sqUserTag.getTagName());
          sqTag.setIcount(1);
          sqTag.setUserId(sqUserTag.getUserId());
          sqTag.setTagType(YunConstant.KEY_TAGS_PERSON);
          sqTag.setStatus(0);
          sqTag.setCreateTime(DateUtil.date(System.currentTimeMillis()));
          sqTagMapper.insertSqTag(sqTag);
          //创建新的标签后
          logger.debug("创建新标签ID:"+sqTag.getId()+"name:"+sqUserTag.getTagName());
          sqUserTag.setTagId(sqTag.getId());
          sqUserTag.setTagName(sqUserTag.getTagName());
          sqUserTag.setIcount(1);
      }
        return sqUserTagMapper.insertSqUserTag(sqUserTag);
    }

    /**
     *用户修改书签
     *
     * @param  sqUserTag
     * @return int
     */
    @Override
    public int updateSqUserTagEdit(SqUserTag sqUserTag) {
        //修改前的tagid
        Long tagId =sqUserTag.getTagId();
        logger.debug("修改前的tagid："+tagId);

        List<SqTag> sqtag =  sqTagMapper.selectCountByName(sqUserTag.getTagName());
        if (sqtag!=null&&!sqtag.isEmpty()){
            sqUserTag.setTagId(sqtag.get(0).getId());
            logger.debug("修改后的tagid："+sqtag.get(0).getId());
        }else {
            SqTag sqTag=new SqTag();
            sqTag.setName(sqUserTag.getTagName());
            sqTag.setIcount(1);
            sqTag.setUserId(sqUserTag.getUserId());
            sqTag.setTagType(YunConstant.KEY_TAGS_PERSON);
            sqTag.setStatus(0);
            sqTag.setCreateTime(DateUtil.date(System.currentTimeMillis()));
            sqTagMapper.insertSqTag(sqTag);
            logger.debug("修改后的tagid："+sqTag.getId());
            sqUserTag.setTagId(sqTag.getId());
        }
        //修改在正在使用该标签的 对应引用书签
        sqTagMapper.updateBookmarkTagIdByTagId(tagId,sqUserTag.getTagId(),sqUserTag.getUserId());

        return sqUserTagMapper.updateSqUserTag(sqUserTag);
    }

    /**
     * 通过标签名字查看是否存在
     *
     * @param name String
     * @param userId Long
     * @return 数量
     */
    @Override
    public int selectCountByName(String name, Long userId) {
        return sqUserTagMapper.selectCountByName(name,userId);
    }
}
