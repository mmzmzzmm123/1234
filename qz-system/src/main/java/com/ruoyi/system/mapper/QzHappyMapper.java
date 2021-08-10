package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.QzHappy;

/**
 * 快乐+1Mapper接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface QzHappyMapper 
{
    /**
     * 查询快乐+1
     * 
     * @param happyId 快乐+1主键
     * @return 快乐+1
     */
    public QzHappy selectQzHappyByHappyId(Long happyId);

    /**
     * 查询快乐+1列表
     * 
     * @param qzHappy 快乐+1
     * @return 快乐+1集合
     */
    public List<QzHappy> selectQzHappyList(QzHappy qzHappy);

    /**
     * 新增快乐+1
     * 
     * @param qzHappy 快乐+1
     * @return 结果
     */
    public int insertQzHappy(QzHappy qzHappy);

    /**
     * 修改快乐+1
     * 
     * @param qzHappy 快乐+1
     * @return 结果
     */
    public int updateQzHappy(QzHappy qzHappy);

    /**
     * 删除快乐+1
     * 
     * @param happyId 快乐+1主键
     * @return 结果
     */
    public int deleteQzHappyByHappyId(Long happyId);

    /**
     * 批量删除快乐+1
     * 
     * @param happyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQzHappyByHappyIds(Long[] happyIds);
}
