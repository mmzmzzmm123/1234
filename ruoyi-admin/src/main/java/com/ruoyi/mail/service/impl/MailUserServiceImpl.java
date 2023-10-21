package com.ruoyi.mail.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mail.mapper.MailUserMapper;
import com.ruoyi.mail.domain.MailUser;
import com.ruoyi.mail.service.IMailUserService;

/**
 * 邮件用户列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
@Service
public class MailUserServiceImpl implements IMailUserService 
{
    @Autowired
    private MailUserMapper mailUserMapper;

    /**
     * 查询邮件用户列表
     * 
     * @param id 邮件用户列表主键
     * @return 邮件用户列表
     */
    @Override
    public MailUser selectMailUserById(Long id)
    {
        return mailUserMapper.selectMailUserById(id);
    }

    /**
     * 查询邮件用户列表列表
     * 
     * @param mailUser 邮件用户列表
     * @return 邮件用户列表
     */
    @Override
    public List<MailUser> selectMailUserList(MailUser mailUser)
    {
        return mailUserMapper.selectMailUserList(mailUser);
    }

    /**
     * 新增邮件用户列表
     * 
     * @param mailUser 邮件用户列表
     * @return 结果
     */
    @Override
    public int insertMailUser(MailUser mailUser)
    {
        mailUser.setCreateTime(DateUtils.getNowDate());
        return mailUserMapper.insertMailUser(mailUser);
    }

    /**
     * 修改邮件用户列表
     * 
     * @param mailUser 邮件用户列表
     * @return 结果
     */
    @Override
    public int updateMailUser(MailUser mailUser)
    {
        mailUser.setUpdateTime(DateUtils.getNowDate());
        return mailUserMapper.updateMailUser(mailUser);
    }

    /**
     * 批量删除邮件用户列表
     * 
     * @param ids 需要删除的邮件用户列表主键
     * @return 结果
     */
    @Override
    public int deleteMailUserByIds(Long[] ids)
    {
        return mailUserMapper.deleteMailUserByIds(ids);
    }

    /**
     * 删除邮件用户列表信息
     * 
     * @param id 邮件用户列表主键
     * @return 结果
     */
    @Override
    public int deleteMailUserById(Long id)
    {
        return mailUserMapper.deleteMailUserById(id);
    }
}
