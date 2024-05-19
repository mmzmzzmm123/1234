package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.ImChatGroupUser;
import com.onethinker.bk.mapper.ImChatGroupUserMapper;
import com.onethinker.bk.service.IImChatGroupUserService;
import com.onethinker.im.websocket.ImConfigConst;
import com.onethinker.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天群成员Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatGroupUserServiceImpl extends ServiceImpl<ImChatGroupUserMapper, ImChatGroupUser> implements IImChatGroupUserService {
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

    @Override
    public void insertImChatGroupByPlUserId(Long plUserId) {
        ImChatGroupUser imChatGroupUser = new ImChatGroupUser();
        imChatGroupUser.setGroupId(ImConfigConst.DEFAULT_GROUP_ID);
        imChatGroupUser.setUserId(plUserId);
        imChatGroupUser.setUserStatus(ImConfigConst.GROUP_USER_STATUS_PASS);
        imChatGroupUserMapper.insertImChatGroupUser(imChatGroupUser);
    }
}
