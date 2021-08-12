package com.ruoyi.system.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.domain.QzHappy;

/**
 * 快乐+1Service接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface IQzHappyService 
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
     * 批量删除快乐+1
     * 
     * @param happyIds 需要删除的快乐+1主键集合
     * @return 结果
     */
    public int deleteQzHappyByHappyIds(Long[] happyIds);

    /**
     * 删除快乐+1信息
     * 
     * @param happyId 快乐+1主键
     * @return 结果
     */
    public int deleteQzHappyByHappyId(Long happyId);
    /**
     * 修改快乐+1
     *
     * @param checkdate 计划发布时间
     * @return 结果
     */
    public int sendQzHappy(Date checkdate);
}
