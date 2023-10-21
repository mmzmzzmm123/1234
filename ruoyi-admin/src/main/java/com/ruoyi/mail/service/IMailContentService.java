package com.ruoyi.mail.service;

import java.util.List;
import com.ruoyi.mail.domain.MailContent;

/**
 * 晚安语列表Service接口
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
public interface IMailContentService 
{
    /**
     * 查询晚安语列表
     * 
     * @param id 晚安语列表主键
     * @return 晚安语列表
     */
    public MailContent selectMailContentById(Long id);

    /**
     * 查询晚安语列表列表
     * 
     * @param mailContent 晚安语列表
     * @return 晚安语列表集合
     */
    public List<MailContent> selectMailContentList(MailContent mailContent);

    /**
     * 新增晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    public int insertMailContent(MailContent mailContent);

    /**
     * 修改晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    public int updateMailContent(MailContent mailContent);

    /**
     * 批量删除晚安语列表
     * 
     * @param ids 需要删除的晚安语列表主键集合
     * @return 结果
     */
    public int deleteMailContentByIds(Long[] ids);

    /**
     * 删除晚安语列表信息
     * 
     * @param id 晚安语列表主键
     * @return 结果
     */
    public int deleteMailContentById(Long id);
}
