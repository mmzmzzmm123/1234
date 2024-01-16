package com.ruoyi.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.ImChatGroupUserMapper;
import com.ruoyi.bk.domain.ImChatGroupUser;
import com.ruoyi.bk.service.IImChatGroupUserService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 聊天群成员Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatGroupUserServiceImpl extends ServiceImpl<ImChatGroupUserMapper,ImChatGroupUser> implements IImChatGroupUserService {
    @Resource
    private ImChatGroupUserMapper imChatGroupUserMapper;

    /**
     * 查询聊天群成员
     *
     * @param id 聊天群成员主键
     * @return 聊天群成员
     */
    @Override
    public ImChatGroupUser selectImChatGroupUserById(Long id) {
        return imChatGroupUserMapper.selectImChatGroupUserById(id);
    }

    /**
     * 查询聊天群成员列表
     *
     * @param imChatGroupUser 聊天群成员
     * @return 聊天群成员
     */
    @Override
    public List<ImChatGroupUser> selectImChatGroupUserList(ImChatGroupUser imChatGroupUser) {
        return imChatGroupUserMapper.selectImChatGroupUserList(imChatGroupUser);
    }

    /**
     * 新增聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    @Override
    public int insertImChatGroupUser(ImChatGroupUser imChatGroupUser) {
                imChatGroupUser.setCreateTime(DateUtils.getNowDate());
            return imChatGroupUserMapper.insertImChatGroupUser(imChatGroupUser);
    }

    /**
     * 修改聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    @Override
    public int updateImChatGroupUser(ImChatGroupUser imChatGroupUser) {
        return imChatGroupUserMapper.updateImChatGroupUser(imChatGroupUser);
    }

    /**
     * 批量删除聊天群成员
     *
     * @param ids 需要删除的聊天群成员主键
     * @return 结果
     */
    @Override
    public int deleteImChatGroupUserByIds(Long[] ids) {
        return imChatGroupUserMapper.deleteImChatGroupUserByIds(ids);
    }

    /**
     * 删除聊天群成员信息
     *
     * @param id 聊天群成员主键
     * @return 结果
     */
    @Override
    public int deleteImChatGroupUserById(Long id) {
        return imChatGroupUserMapper.deleteImChatGroupUserById(id);
    }
}
