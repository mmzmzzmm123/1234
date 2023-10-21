package com.ruoyi.mail.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mail.mapper.MailContentMapper;
import com.ruoyi.mail.domain.MailContent;
import com.ruoyi.mail.service.IMailContentService;

/**
 * 晚安语列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
@Service
public class MailContentServiceImpl implements IMailContentService 
{
    @Autowired
    private MailContentMapper mailContentMapper;

    /**
     * 查询晚安语列表
     * 
     * @param id 晚安语列表主键
     * @return 晚安语列表
     */
    @Override
    public MailContent selectMailContentById(Long id)
    {
        return mailContentMapper.selectMailContentById(id);
    }

    /**
     * 查询晚安语列表列表
     * 
     * @param mailContent 晚安语列表
     * @return 晚安语列表
     */
    @Override
    public List<MailContent> selectMailContentList(MailContent mailContent)
    {
        return mailContentMapper.selectMailContentList(mailContent);
    }

    /**
     * 新增晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    @Override
    public int insertMailContent(MailContent mailContent)
    {
        mailContent.setCreateTime(DateUtils.getNowDate());
        return mailContentMapper.insertMailContent(mailContent);
    }

    /**
     * 修改晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    @Override
    public int updateMailContent(MailContent mailContent)
    {
        mailContent.setUpdateTime(DateUtils.getNowDate());
        return mailContentMapper.updateMailContent(mailContent);
    }

    /**
     * 批量删除晚安语列表
     * 
     * @param ids 需要删除的晚安语列表主键
     * @return 结果
     */
    @Override
    public int deleteMailContentByIds(Long[] ids)
    {
        return mailContentMapper.deleteMailContentByIds(ids);
    }

    /**
     * 删除晚安语列表信息
     * 
     * @param id 晚安语列表主键
     * @return 结果
     */
    @Override
    public int deleteMailContentById(Long id)
    {
        return mailContentMapper.deleteMailContentById(id);
    }
}
