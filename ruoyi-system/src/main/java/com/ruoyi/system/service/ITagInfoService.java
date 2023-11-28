package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TagInfo;

/**
 * tag信息Service接口
 * 
 * @author derek
 * @date 2023-11-14
 */
public interface ITagInfoService 
{
    /**
     * 查询tag信息
     * 
     * @param tagInfoId tag信息主键
     * @return tag信息
     */
    public TagInfo selectTagInfoByTagInfoId(Long tagInfoId);

    /**
     * 查询tag信息列表
     * 
     * @param tagInfo tag信息
     * @return tag信息集合
     */
    public List<TagInfo> selectTagInfoList(TagInfo tagInfo);

    /**
     * 新增tag信息
     * 
     * @param tagInfo tag信息
     * @return 结果
     */
    public int insertTagInfo(TagInfo tagInfo);

    /**
     * 修改tag信息
     * 
     * @param tagInfo tag信息
     * @return 结果
     */
    public int updateTagInfo(TagInfo tagInfo);

    /**
     * 批量删除tag信息
     * 
     * @param tagInfoIds 需要删除的tag信息主键集合
     * @return 结果
     */
    public int deleteTagInfoByTagInfoIds(Long[] tagInfoIds);

    /**
     * 删除tag信息信息
     * 
     * @param tagInfoId tag信息主键
     * @return 结果
     */
    public int deleteTagInfoByTagInfoId(Long tagInfoId);
}
