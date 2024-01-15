package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.mapper.GroupMemberMapper;
import com.ruoyi.system.domain.GroupMember;
import com.ruoyi.system.service.GroupMemberService;
import org.springframework.stereotype.Service;

/**
 * 群成员信息(GroupMember)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:25
 */
@Service("groupMemberService")
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements GroupMemberService {

}
