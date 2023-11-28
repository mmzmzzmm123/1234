package com.ruoyi.system.service.impl;

import com.ruoyi.system.utils.RFIDUtils;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TagInfoMapper;
import com.ruoyi.system.domain.TagInfo;
import com.ruoyi.system.service.ITagInfoService;

/**
 * tag信息Service业务层处理
 *
 * @author derek
 * @date 2023-11-14
 */
@Service
public class TagInfoServiceImpl implements ITagInfoService
{
    @Autowired
    private TagInfoMapper tagInfoMapper;

    /**
     * 查询tag信息
     *
     * @param tagInfoId tag信息主键
     * @return tag信息
     */
    @Override
    public TagInfo selectTagInfoByTagInfoId(Long tagInfoId)
    {
        return tagInfoMapper.selectTagInfoByTagInfoId(tagInfoId);
    }

    /**
     * 查询tag信息列表
     *
     * @param tagInfo tag信息
     * @return tag信息
     */
    @Override
    public List<TagInfo> selectTagInfoList(TagInfo tagInfo)
    {
        return tagInfoMapper.selectTagInfoList(tagInfo);
    }

    /**
     * 新增tag信息
     *
     * @param tagInfo tag信息
     * @return 结果
     */
    @Override
    public int insertTagInfo(TagInfo tagInfo) {
        tagInfo.setRfidId(RFIDUtils.generateRfidId(1));
        tagInfo.setCreateTime(DateUtils.getNowDate());

        tagInfoMapper.insertTagInfo(tagInfo);
        // 自增id作为order
        tagInfo.setRfidId(RFIDUtils.generateRfidId(tagInfo.getTagInfoId()));
        return updateTagInfo(tagInfo);
    }

    /**
     * 修改tag信息
     *
     * @param tagInfo tag信息
     * @return 结果
     */
    @Override
    public int updateTagInfo(TagInfo tagInfo)
    {
        tagInfo.setUpdateTime(DateUtils.getNowDate());
        return tagInfoMapper.updateTagInfo(tagInfo);
    }

    /**
     * 批量删除tag信息
     *
     * @param tagInfoIds 需要删除的tag信息主键
     * @return 结果
     */
    @Override
    public int deleteTagInfoByTagInfoIds(Long[] tagInfoIds)
    {
        return tagInfoMapper.deleteTagInfoByTagInfoIds(tagInfoIds);
    }

    /**
     * 删除tag信息信息
     *
     * @param tagInfoId tag信息主键
     * @return 结果
     */
    @Override
    public int deleteTagInfoByTagInfoId(Long tagInfoId)
    {
        return tagInfoMapper.deleteTagInfoByTagInfoId(tagInfoId);
    }
}
