package com.onethinker.bk.service.impl;

import java.util.List;

import com.onethinker.im.websocket.ImConfigConst;
import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.ImChatUserFriendMapper;
import com.onethinker.bk.domain.ImChatUserFriend;
import com.onethinker.bk.service.IImChatUserFriendService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 好友Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatUserFriendServiceImpl extends ServiceImpl<ImChatUserFriendMapper,ImChatUserFriend> implements IImChatUserFriendService {
    @Resource
    private ImChatUserFriendMapper imChatUserFriendMapper;

    @Autowired
    private ISysConfigService sysConfigService;
    /**
     * 查询好友
     *
     * @param id 好友主键
     * @return 好友
     */
    @Override
    public ImChatUserFriend selectImChatUserFriendById(Long id) {
        return imChatUserFriendMapper.selectImChatUserFriendById(id);
    }

    /**
     * 查询好友列表
     *
     * @param imChatUserFriend 好友
     * @return 好友
     */
    @Override
    public List<ImChatUserFriend> selectImChatUserFriendList(ImChatUserFriend imChatUserFriend) {
        return imChatUserFriendMapper.selectImChatUserFriendList(imChatUserFriend);
    }

    /**
     * 新增好友
     *
     * @param imChatUserFriend 好友
     * @return 结果
     */
    @Override
    public int insertImChatUserFriend(ImChatUserFriend imChatUserFriend) {
                imChatUserFriend.setCreateTime(DateUtils.getNowDate());
            return imChatUserFriendMapper.insertImChatUserFriend(imChatUserFriend);
    }

    /**
     * 修改好友
     *
     * @param imChatUserFriend 好友
     * @return 结果
     */
    @Override
    public int updateImChatUserFriend(ImChatUserFriend imChatUserFriend) {
        return imChatUserFriendMapper.updateImChatUserFriend(imChatUserFriend);
    }

    /**
     * 批量删除好友
     *
     * @param ids 需要删除的好友主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserFriendByIds(Long[] ids) {
        return imChatUserFriendMapper.deleteImChatUserFriendByIds(ids);
    }

    /**
     * 删除好友信息
     *
     * @param id 好友主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserFriendById(Long id) {
        return imChatUserFriendMapper.deleteImChatUserFriendById(id);
    }

    @Override
    public void insertImChatUserFriendByPlUserId(Long plUserId) {
        ImChatUserFriend imChatUser = new ImChatUserFriend();
        imChatUser.setUserId(plUserId);
        imChatUser.setFriendId(Long.parseLong(sysConfigService.selectConfigByKey(SysConfigKeyEnum.ADMIN_USER_ID)));
        imChatUser.setRemark("站长");
        imChatUser.setFriendStatus(ImConfigConst.FRIEND_STATUS_PASS);
        imChatUserFriendMapper.insertImChatUserFriend(imChatUser);

        ImChatUserFriend imChatFriend = new ImChatUserFriend();
        imChatFriend.setUserId(Long.parseLong(sysConfigService.selectConfigByKey(SysConfigKeyEnum.ADMIN_USER_ID)));
        imChatFriend.setFriendId(plUserId);
        imChatFriend.setFriendStatus(ImConfigConst.FRIEND_STATUS_PASS);
        imChatUserFriendMapper.insertImChatUserFriend(imChatFriend);
    }
}
