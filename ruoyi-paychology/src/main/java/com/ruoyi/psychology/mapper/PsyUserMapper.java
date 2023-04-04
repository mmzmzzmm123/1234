package com.ruoyi.psychology.mapper;

import java.util.List;
import com.ruoyi.psychology.domain.PsyUser;

/**
 * 用户Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
public interface PsyUserMapper 
{
    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    public PsyUser selectPsyUserById(Integer id);

    /**
     * 查询用户列表
     * 
     * @param psyUser 用户
     * @return 用户集合
     */
    public List<PsyUser> selectPsyUserList(PsyUser psyUser);

    /**
     * 新增用户
     * 
     * @param psyUser 用户
     * @return 结果
     */
    public int insertPsyUser(PsyUser psyUser);

    /**
     * 修改用户
     * 
     * @param psyUser 用户
     * @return 结果
     */
    public int updatePsyUser(PsyUser psyUser);

    /**
     * 删除用户
     * 
     * @param id 用户主键
     * @return 结果
     */
    public int deletePsyUserById(Integer id);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyUserByIds(Integer[] ids);

    int insertOrUpdate(PsyUser psyUser);

    PsyUser queryUserByAccount(String account);
}
