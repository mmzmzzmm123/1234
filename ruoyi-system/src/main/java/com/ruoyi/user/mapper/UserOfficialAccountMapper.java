package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserOfficialAccount;
import org.apache.ibatis.annotations.Param;

/**
 * 微信公众号用户Mapper接口
 *
 * @author Lam
 * @date 2023-10-14
 */
public interface UserOfficialAccountMapper {

    /**
     * 查询微信公众号用户
     *
     * @param id 微信公众号用户主键
     * @return 微信公众号用户
     */
    public UserOfficialAccount selectUserOfficialAccountById(Long id);

    /**
     * 查询id最大的记录
     *
     * @return 结果
     * */
    public UserOfficialAccount selectMaxRecord();

    /**
     * 查询微信公众号用户列表
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 微信公众号用户集合
     */
    public List<UserOfficialAccount> selectUserOfficialAccountList(UserOfficialAccount userOfficialAccount);

    /**
     * 查询unionId为空的记录
     *
     * @return 结果
     * */
    public List<UserOfficialAccount> selectOfUnionIdIsNull();

    /**
     * 根据UnionId查询openId
     *
     * @param unionId
     * @return 结果
     * */
    public String getOpenIdByUnionId(String unionId);

    /**
     * 根据openId集合批量插入数据
     *
     * @param openIdList 数据
     * @return 结果
     * */
    public int insertOfOpenIdList(List<String> openIdList);

    /**
     * 新增微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    public int insertUserOfficialAccount(UserOfficialAccount userOfficialAccount);

    /**
     * 修改微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    public int updateUserOfficialAccount(UserOfficialAccount userOfficialAccount);

    /**
     * 批量更新
     *
     * @param userOfficialAccountList 数据 */
    public void updateOfList(List<UserOfficialAccount> userOfficialAccountList);

    /**
     * 根据openId修改订阅状态
     *
     * @param ifSubscribe 是否订阅
     * @param openId openid
     * */
    public void updateIfSubscribeByOpenId(@Param("ifSubscribe") String ifSubscribe,@Param("openId") String openId);

    /**
     * 删除微信公众号用户
     *
     * @param id 微信公众号用户主键
     * @return 结果
     */
    public int deleteUserOfficialAccountById(Long id);

    /**
     * 批量删除微信公众号用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserOfficialAccountByIds(Long[] ids);
}
