package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzHappyMapper;
import com.ruoyi.system.domain.QzHappy;
import com.ruoyi.system.service.IQzHappyService;

/**
 * 快乐+1Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzHappyServiceImpl implements IQzHappyService 
{
    @Autowired
    private QzHappyMapper qzHappyMapper;

    /**
     * 查询快乐+1
     * 
     * @param happyId 快乐+1主键
     * @return 快乐+1
     */
    @Override
    public QzHappy selectQzHappyByHappyId(Long happyId)
    {
        return qzHappyMapper.selectQzHappyByHappyId(happyId);
    }

    /**
     * 查询快乐+1列表
     * 
     * @param qzHappy 快乐+1
     * @return 快乐+1
     */
    @Override
    public List<QzHappy> selectQzHappyList(QzHappy qzHappy)
    {
        return qzHappyMapper.selectQzHappyList(qzHappy);
    }

    /**
     * 新增快乐+1
     * 
     * @param qzHappy 快乐+1
     * @return 结果
     */
    @Override
    public int insertQzHappy(QzHappy qzHappy)
    {
        qzHappy.setCreateTime(DateUtils.getNowDate());
        return qzHappyMapper.insertQzHappy(qzHappy);
    }

    /**
     * 修改快乐+1
     * 
     * @param qzHappy 快乐+1
     * @return 结果
     */
    @Override
    public int updateQzHappy(QzHappy qzHappy)
    {
        qzHappy.setUpdateTime(DateUtils.getNowDate());
        return qzHappyMapper.updateQzHappy(qzHappy);
    }

    /**
     * 批量删除快乐+1
     * 
     * @param happyIds 需要删除的快乐+1主键
     * @return 结果
     */
    @Override
    public int deleteQzHappyByHappyIds(Long[] happyIds)
    {
        return qzHappyMapper.deleteQzHappyByHappyIds(happyIds);
    }

    /**
     * 删除快乐+1信息
     * 
     * @param happyId 快乐+1主键
     * @return 结果
     */
    @Override
    public int deleteQzHappyByHappyId(Long happyId)
    {
        return qzHappyMapper.deleteQzHappyByHappyId(happyId);
    }

    @Override
    public int sendQzHappy(Date checkdate) {
        QzHappy qzHappy = new QzHappy();
        qzHappy.setReleaseTime(checkdate);
        List<QzHappy> sendQzHappyList =  qzHappyMapper.selectQzHappyList(qzHappy);
        for(QzHappy  happy :sendQzHappyList){
            happy.setReleaseTime(checkdate);
            happy.setStatus("1");
            qzHappyMapper.updateQzHappy(happy);

        }
        return 0;
    }
}
