package com.ruoyi.mail.service;

import java.util.List;
import com.ruoyi.mail.domain.MailUser;

/**
 * 邮件用户列表Service接口
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
public interface IMailUserService 
{
    /**
     * 查询邮件用户列表
     * 
     * @param id 邮件用户列表主键
     * @return 邮件用户列表
     */
    public MailUser selectMailUserById(Long id);

    /**
     * 查询邮件用户列表列表
     * 
     * @param mailUser 邮件用户列表
     * @return 邮件用户列表集合
     */
    public List<MailUser> selectMailUserList(MailUser mailUser);

    /**
     * 新增邮件用户列表
     * 
     * @param mailUser 邮件用户列表
     * @return 结果
     */
    public int insertMailUser(MailUser mailUser);

    /**
     * 修改邮件用户列表
     * 
     * @param mailUser 邮件用户列表
     * @return 结果
     */
    public int updateMailUser(MailUser mailUser);

    /**
     * 批量删除邮件用户列表
     * 
     * @param ids 需要删除的邮件用户列表主键集合
     * @return 结果
     */
    public int deleteMailUserByIds(Long[] ids);

    /**
     * 删除邮件用户列表信息
     * 
     * @param id 邮件用户列表主键
     * @return 结果
     */
    public int deleteMailUserById(Long id);
}
