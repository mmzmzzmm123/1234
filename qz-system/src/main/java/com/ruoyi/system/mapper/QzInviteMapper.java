package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.QzInvite;

/**
 * 邀请Mapper接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface QzInviteMapper 
{
    /**
     * 查询邀请
     * 
     * @param id 邀请主键
     * @return 邀请
     */
    public QzInvite selectQzInviteById(Long id);

    /**
     * 查询邀请列表
     * 
     * @param qzInvite 邀请
     * @return 邀请集合
     */
    public List<QzInvite> selectQzInviteList(QzInvite qzInvite);

    /**
     * 新增邀请
     * 
     * @param qzInvite 邀请
     * @return 结果
     */
    public int insertQzInvite(QzInvite qzInvite);

    /**
     * 修改邀请
     * 
     * @param qzInvite 邀请
     * @return 结果
     */
    public int updateQzInvite(QzInvite qzInvite);

    /**
     * 删除邀请
     * 
     * @param id 邀请主键
     * @return 结果
     */
    public int deleteQzInviteById(Long id);

    /**
     * 批量删除邀请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQzInviteByIds(Long[] ids);
}
